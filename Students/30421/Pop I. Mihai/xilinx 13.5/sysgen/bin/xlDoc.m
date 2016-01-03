% xlDoc Launches the System Generator Help System
% 
%   xlDoc
%     Launches the doc system, or brings it into focus if already opened.
%
%   xlDoc -browser 
%     Launches the doc system in the system browser.
%
%   xlDoc(2) 
%     Used Internally. Clear the xldoc_jsobj global var when the doc
%     system is closed down.
%
%   xlDoc(1,obj)
%     Used Internally. Sets the xldoc_jsobj global var. Expects obj to
%     be a netscape.javascript.JSObject that is live in the web browser.
% 
%   xlDoc -book book -topic topic
%     Opens the sysgen doc associated with a particular book to a location.
%     Book is a string, typically 'sysgen'. Topic is a string, and is the
%     topic alias setup in WebWorks. Topic Alias is case sensitive.
%     e.g. xlDoc -book sysgen -topic Accumulator
%     e.g. xlDoc -book sysgen -topic 'Hardware Design Using System
%     Generator/Notes to the Hardware Engineer'
%  
%  xlDoc -book book -title title
%    Opens the doc to the page in the "boo" with the given "title"
%    e.g. xlDoc('-book','sysgen','-title','Introduction');
%
%  xlDoc -getbook
%     Reports the book name of the page currently showing in the help
%     browser.
% 
%  If the global var dbgsysgen is set to 1, debug info will be printed to
%  the console.
%
% Copyright 2007. Xilinx Inc.

% ** NOTE **
% PCode for this m-code file will be compiled in the absence of System
% Generator. Do not use sysgen related functions in this body, unless you
% change the compilation scheme.
function [varargout]=xlDoc(varargin)

book = '';
title = '';
html = '';
topic = '';
subtopic = '';

% Special Case (1)
% xlDoc() Just open the doc
if (nargin==0)
  launchDoc;
  return;
end


for l=1:nargin
  switch varargin{l}
    case '-browser'
      web(docloc,'-browser');
      return;
    case '-getbook'
      varargout{1}=getCurrentContext();
      return;
    case {'-book','-title','-html','-topic','-subtopic'}
      if (l==nargin)
        error(['The ' varargin{l} ' option requires a further parameter to specify the book name.']);
        return;
      end
      eval([varargin{l}(2:end) '= varargin{l+1};']);
      l=l+1;
  end
end

if isempty(book)
  %error('Please specify a book name.');
  % No book specified, defaulting to sysgen
  book = 'sysgen';
end

if ~isempty(title)
  openDocToTitle(book,title);
  return;
end

if ~isempty(topic)
  if findstr(topic,'/')
    findSubTopic(book,topic);
  else
    openDocTo(book,topic);
  end
  return;
end

if ~isempty(html)
  openDocTo(book,html);
  return;
end


%% ------------------------------------------------------------------------
% 2006b supports two kinds of internal renderer, the ICE Browser and
% WebRenderer. JAVA-JavaScript communication with ICE Browser will be done
% via Java-Applet and Live Connect, for WebRenderer we will use their
% internal API to execute a Javascript.
% w is a com.mathworks.mde.webbrowser.WebBrowser object
function b=detectBrowserType
try
  isWebRenderer=com.mathworks.mlwidgets.html.HTMLRenderer.getUseWebRenderer;
catch
  isWebRenderer = 0;
end

if isWebRenderer
  b='webrenderer';
else
  b='other';
end

%% ------------------------------------------------------------------------
% This xlDoc.m file is expected to be installed in sysgen/bin. And the help
% files are expected to be in sysgen/help/html_help.
function d=docloc
docpath = fileparts(mfilename('fullpath'));
d = fullfile(docpath,'..','help','html_help','index.html');
writeDebug(['Doc location: ' d]);

%% ------------------------------------------------------------------------
function launchDoc

% Check to see if an activeBrowser is up.
writeDebug('Checking to see if active Browser is up');
activeBrowser = com.mathworks.mde.webbrowser.WebBrowser.getActiveBrowser;
browser = detectBrowserType;

writeDebug(['Detected ' browser]);

if strcmp(browser,'other')
  % Just open in external browser.
  openDocInExternalBrowser([],[]);
  return;
end

if ~isempty(activeBrowser)
  % A Browser is already open.
  % Check to see if browser currenly has a loaded doc set if so just
  % bring to focus and return;
  writeDebug('ActiveBrowser found checking to see if sysgen help is loaded.');
  switch browser
    case 'webrenderer'
      try
        webrenderer=activeBrowser.getComponent(0).getComponent(0).getComponent(0);
        browserCanvas=webrenderer.getComponent(0);
        ret=browserCanvas.executeScriptWithReturn('WWHFrame.WWHHandler.fIsReady()');
        writeDebug(['WWHFrame,WWHHandler.fIsReady returned: ' char(ret)]);
        if (ret.startsWith('true'))
          activeBrowser.grabFocus;
          return;
        end
      catch
      end
  end
end

% if we got here, either there isn't an active browser, or the
% activebrowser is not currenly displaying the help docs.
[stat,w]=web(docloc,'-noaddressbox');
if (stat == 1)
  error(['Matlab web browser was not found. Please contact Mathworks for ' ...
    'more information.']);
end
if (stat==2)
  error(['Matlab web browser was found but could not be launched. ' ...
    'Please contact Mathworks for more information.']);
end

% Wait until the doc system has loaded. Times out after 60*0.5=30seconds
timeout = 60;
XILINXEnv = [strrep(getenv('XILINX'),'\','\\') '\\'];
while (timeout>0)
  switch browser
    case 'webrenderer'
      try
        webrenderer=w.getComponent(0).getComponent(0).getComponent(0);
        browserCanvas=webrenderer.getComponent(0);
        ret=browserCanvas.executeScriptWithReturn('WWHFrame.WWHBrowser.mPanelViewLoaded');
        if (ret.startsWith('true'))
          writeDebug('Sysgen Doc loaded');
          writeDebug(['Setting WWHFrame.WWHBrowser.mbXilinx=' XILINXEnv]);
          % If mPanelViewLoaded, we assume that the Sysgen Doc is fully
          % loaded. Save the Xilinx env into the havascript object to allow
          % links to local PDF files to work.
          browserCanvas.executeScript('WWHFrame.WWHBrowser.mbIsMatlab=true;');
          browserCanvas.executeScript(['WWHFrame.WWHBrowser.mbXilinx="' XILINXEnv '";']);
          break;
        end
      catch
      end
  end % switch
  
  timeout = timeout-1;
  writeDebug(['Sysgen doc not yet loaded, timeout counter: ' num2str(timeout)]);
  pause(0.5);
end %while

if (timeout<=0) 
  error('Loading of the System Generator help system timed out.');
else
  writeDebug('Docs launched');
end


%% ------------------------------------------------------------------------
function openDocTo(context,link)

h = java.lang.String(link);

if (h.endsWith('.htm') || h.endsWith('.html') || h.matches('.*\.htm.*'))
  javascript=['WWHFrame.WWHHelp.fClickedPopup(''' context ''',''' link ''','''')'];
else
  javascript=['WWHFrame.WWHHelp.fShowTopic(''' context ''',''' link ''')'];
end

activeBrowser = com.mathworks.mde.webbrowser.WebBrowser.getActiveBrowser;

switch detectBrowserType
  case 'webrenderer'
    if isempty(activeBrowser)
      launchDoc;
      activeBrowser = com.mathworks.mde.webbrowser.WebBrowser.getActiveBrowser;
    end
    webrenderer=activeBrowser.getComponent(0).getComponent(0).getComponent(0);
    if (webrenderer.getComponentCount==0)
      % browser is not rendering anything so load doc.
      launchDoc;
    end
    browserCanvas=webrenderer.getComponent(0);
    ret=browserCanvas.executeScriptWithReturn('WWHBrowser.mBrowser');
    if ret.equalsIgnoreCase('undefined')
      launchDoc;
    end
    browserCanvas.executeScript(javascript);
    try
      activeBrowser.grabFocus;
    catch
    end
  case 'other'
    openDocInExternalBrowser(context,link);
end


% ------------------------------------------------------------------------ 
function browserCanvas= getWebRendererCanvas()
browser = detectBrowserType;
if (~strcmp(browser,'webrenderer'))
  error(['The Matlab web browser must be set to use the Webrenderer component ' ...
    'in order for this option to function correctly. '...
    ' Please use the webrenderer component. Contact your Mathworks '...
    'associate for details.']);
end
activeBrowser = com.mathworks.mde.webbrowser.WebBrowser.getActiveBrowser;
webrenderer=activeBrowser.getComponent(0).getComponent(0).getComponent(0);
browserCanvas=webrenderer.getComponent(0);

% -------------------------------------------------------------------------
function book=getCurrentContext()
b = getWebRendererCanvas;
docFrame=b.executeScriptWithReturn('WWHFrame.WWHHelp.fGetFrameReference("WWHDocumentFrame")');
book=b.executeScriptWithReturn([char(docFrame) '.WWHBookData_Context()']);
book=char(book);

% -------------------------------------------------------------------------
function openDocToTitle(book,title)
  switch detectBrowserType
    case 'webrenderer'
      xlDoc;
      b = getWebRendererCanvas;
      href=char(b.executeScriptWithReturn(['WWHFrame.WWHBrowser.fBookAndTitleToHREF("' ...
        book '","' title '")']));
      if ~strcmp(href,'undefined')
        xlDoc('-book',book,'-html',href);
      end
      
    case 'other'
      try 
        sgpath = xilinx.environment.getpath('sysgen');
      catch           
        sgpath = fullfile(fileparts(mfilename('fullpath')),'..');
        lasterr('');
      end     
      filesxml = fullfile(sgpath,'help','html_help','wwhdata','xml','files.xml');
      dom = xmlread(filesxml);
      root_elem = dom.getDocumentElement;
      %xp=org.apache.xpath.XPathAPI;
      %n=xp.selectSingleNode(root_elem,['DocumentInfo/Document[@title="'  title '"]']);
      xpath=getXPath('pre',root_elem);
      n = xpath.evaluate(['//pre:DocumentInfo/pre:Document[@title="'  title ...
                          '"]'],root_elem,javax.xml.xpath.XPathConstants.NODE);
      
      if isempty(n)
        % Title not found So just default to index page
        xlDoc;
      else
        href=char(n.getAttribute('href'));
        xlDoc('-book',book,'-html',href);
      end
      
  end

%% ------------------------------------------------------------------------
function writeDebug(msg)
global dbgsysgen;
if (dbgsysgen==1)
  disp(msg);
end

% -------------------------------------------------------------------------
% eg.  
% findSubTopic('Release Information/Release Notes 10.1.1','Known Issues');
function findSubTopic(book,topic)
try
  oriTopic = topic;
  toc = fullfile(xilinx.environment.getpath('sysgen'),'help','html_help','wwhdata','xml','toc.xml');
  files = fullfile(xilinx.environment.getpath('sysgen'),'help','html_help','wwhdata','xml','files.xml');
  x=xmlread(toc);
  %xpath = org.apache.xpath.XPathAPI();
  xpath = getXPath('pre',x);
  [q,dontcare]=regexp(topic,'(.*)/(.*)','tokens');
  topic=q{1}{1};
  subTopic=q{1}{2};
  
  findLastSlash = regexp(topic,'/');
  if isempty(findLastSlash)
    oneLevelup = topic;
  else
    oneLevelup = topic(findLastSlash(end)+1:end);
  end
  %p=xpath.selectSingleNode(x,['//i[@t="' oneLevelup '"]/i[@t="' subTopic '"]']);
  p=xpath.evaluate(['//pre:i[@t="' oneLevelup '"]/pre:i[@t="' subTopic '"]'],x,javax.xml.xpath.XPathConstants.NODE);
  link = p.getAttribute('l'); % should be of form 1#12345
  indexofHash = link.indexOf('#');
  if (indexofHash > 0)
    htmIndex=str2double(char(link.substring(0,indexofHash)));
    link=char(link);
    htmHash=link(indexofHash+1:end);
  else
    htmIndex=str2double(char(link));
    htmHash=[];
  end
  
  % Find the html book  of htmIndex
  x=xmlread(files);
  %a=xpath.selectNodeList(x,'//DocumentInfo/Document');
  xpath = getXPath('pre',x); % Need to recall this so Namespace is correct.
  a=xpath.evaluate('//pre:DocumentInfo/pre:Document',x,javax.xml.xpath.XPathConstants.NODESET);
  htm = char(a.item(htmIndex).getAttribute('href'));
  if isempty(htmHash)
    openDocTo(book,htm);
  else
    openDocTo(book,[htm htmHash]);
  end
catch
  disp(['Could not find the document: ' oriTopic]);
end

 
function [browser,options]=checkBrowserConfigured
  % Only used for ML2008b and below
  [browser,options,docpath] = docopt;
    if isempty(browser)        
        warning(['Matlab has not been configured to work with an external Web Browser.' char(10)...
          'An external web browser is required for The System Generator Help System to work.' char(10)...
          'To learn how to configure Matlab to use your Web browser type ''help docopt''']);
         options = [];
         browser=[];
         %browser = 'C:\Progra~1\Mozill~1\firefox';
    else
        if ~isempty(docpath)
          browser = fullfile(docpath,browser);
        end
    end
    
  function  url = formURLForExternalBrowser(context,link)
    if (isempty(context) || isempty(link))
      url  = ['file://' docloc];
      return;
    else
      % Is link plain text? If so assume it is a topic
      h = java.lang.String(link);
      if (h.endsWith('.htm') || h.endsWith('.html') || h.matches('.*\.htm.*'))
        url = ['file://'  docloc '?context=' context '&href=' link];
      else
        url = ['file://'  docloc '?context=' context '&topic=' link];
      end
    end
    

function openDocInExternalBrowser(context,link)
  url = formURLForExternalBrowser(context,link);
  
  % if matlab is pree 2009a,use doc opt, 
  if verLessThan('matlab','7.8')
    % Check to see if browser is configured
    [browser,option] = checkBrowserConfigured;
    if isempty(browser)
      % Browser not configured, a warning text is printed in
      % the checkBrowserConfigured function.
      return;
    end

    if ~isempty(option)
      option = [option ' '];
    end
  
    switch browser
      case {'firefox','mozilla'}
        [a,b] = system([browser ' -remote "ping()"']);
        if (a > 0)
          % Browser not running
          cmd = [browser ' ' option '"' url '" &'];
        else
          % Browser already running
          cmd = [browser ' ' option '-remote "openURL(' url ',new-tab)" &'];
        end
      otherwise
        cmd = [browser ' ' option '"' url '" &'];
    end

    system(cmd);
  else
    % Matlab 2009a and above browser preference is in file->preference
    %Strip out file:// from front
    if ispc
      defaultBrowser = getDefaultBrowserInWindows;
      if isempty(defaultBrowser)
        web(url,'-browser');
      else
        system([defaultBrowser ' ' url ' &']);
      end
    else
      web(url,'-browser');
    end
  end

  
  function b=getDefaultBrowserInWindows
    try
      q = winqueryreg('HKEY_LOCAL_MACHINE','SOFTWARE\Clients\StartMenuInternet');
      b = winqueryreg('HKEY_LOCAL_MACHINE',['SOFTWARE\Clients\StartMenuInternet\' q '\shell\open\command']);
      if (b(1)~='"')
        % if the command is not quoted, always quote it
        b = ['"' b '"'];
      end
    catch
      b = [];
    end
  
  function xp = getXPath(prefix, doc)
    xp=javax.xml.xpath.XPathFactory.newInstance.newXPath;
    % Get first tag in the doc
    tag = xp.evaluate('//*',doc,javax.xml.xpath.XPathConstants.NODE);
    if ~isempty(tag) 
      att = tag.getAttribute('xmlns');
      if ~att.isEmpty()
        ns=com.xilinx.sysgen.util.xlNamespaceContext(prefix,att);
        xp.setNamespaceContext(ns);
      end
    end
    