function sysgenMatrixInversSGRGeneration( n, latency )
% sysgenMatrixInversSGRGeneration generates Hardware to perform
% triangularization of an input nxn matrix. This method was proposed by R.
% Dohler in his paper : “Squared Givens Rotation,” IMA Journal of Numerical
% Analysis, no. 11, pp. 1–5, 1991.An implementation was also proposed in 
% Marjan Karkooti et al, "FPGA Implementation of Matrix Inversion Using
% QRD-RLS Algorithm",Signals, Systems and Computers, 2005. Conference.
% Usage :
%   sysgenMatrixInversSGRGeneration(n, latency)
%   n : dimension of the square matrix
%   latency : Latency each Operator
v = cell(n,2*n);
for i = 1:n,
    for j=1:2*n,
        if j <= n,
            v{i,j} = xInport(['a' num2str(i) num2str(j)]);
            continue;
        end
        %Construct the identity Matrix
        if j == n+i,
            v{i,j} = constantInstance(1, v{i,j-n});
            continue;
        end;
        if j > n,
            v{i,j} = constantInstance(0, v{i,j-n});
        end;
    end
end

% Initialize W for now - We will revisit this to improve 
w = cell(n,1);
for i = 1:n,
    w{i} = constantInstance(1, v{i,i});
    %w{i} = xSignal;
    %xBlock('Constant',{'arith_type','Floating-point','const', 1},{},{w{i}});
end

%Initialize everything to 0
u = cell(n,2*n);
for i = 1:n,
    for j=1:2*n,
        u{i,j} = constantInstance(0, v{i,j});
    end
end

%Row Updates have been initialized
ubar = cell(2*n,1);
vbar = cell(2*n,1);

%Start the Squared Givens Iteration
for i = 1:n,
    entering_j = true;
    for j = n-i+1:-1:1,
        if entering_j == true,
            entering_j = false;
            for rm = 1:2*n,
                ubar{rm} = xSignal;            
                xBlock([supportLibrary '/ubar_update'],{'Latency', latency},{u{i,rm},w{j},v{j,i},v{j,rm}},{ubar{rm}});
                u{i,rm} = ubar{rm};
            end   
            for r=1:n,
                for c=1:2*n,
                    if r ~= i,                                            
                        u_delayed_signal = xSignal;
                        xBlock('Delay',{'Latency', 3*latency},{u{r,c}},{u_delayed_signal});        
                        u{r,c} = u_delayed_signal;                    
                    end;
                    v_delayed_signal = xSignal;
                    xBlock('Delay',{'Latency', 3*latency},{v{r,c}},{v_delayed_signal});        
                    v{r,c} = v_delayed_signal;                    
                end;
            end;
        else
            %Generate ubar
            for rm = 1:2*n,
                ubar{rm} = xSignal;            
                xBlock([supportLibrary '/ubar_update'],{'Latency', latency},{u{i,rm},w{j},v{j,i},v{j,rm}},{ubar{rm}});
            end
            %Generate vbar
            for rm = 1:2*n,
                vbar{rm} = xSignal;            
                xBlock([supportLibrary '/vbar_update'],{'Latency', latency},{v{j,rm},v{j,i},u{i,rm}, u{i,i}},{vbar{rm}});
            end
            %Compute Wj
            w_update_signal = xSignal;
            %Delay all Signals to coincide with Ubar
            w_delayed_signal = xSignal;
            xBlock('Delay',{'Latency', 3*latency},{w{j}},{w_delayed_signal});        
            uii_delayed_signal = xSignal;
            xBlock('Delay',{'Latency', 3*latency},{u{i,i}},{uii_delayed_signal});        
            xBlock([supportLibrary '/w_update'],{'Latency', latency},{w_delayed_signal, uii_delayed_signal, ubar{i}},{w_update_signal});
            %Now we update all the signals
            for rm = 1:2*n,
                v{j,rm} = vbar{rm};
                u{i,rm} = ubar{rm};            
            end
            w{j} = w_update_signal;    
            %Now We bring the remaining wavefront ahead to where w is 
            for r = 1:n,
                for c=1:2*n,
                    if (r ~= j), %Bring the v Wavefront forward
                        v_delayed_signal = xSignal;
                        xBlock('Delay',{'Latency', 3*latency*2},{v{r,c}},{v_delayed_signal});        
                        v{r,c} = v_delayed_signal;
                    else
                        v_delayed_signal = xSignal;
                        xBlock('Delay',{'Latency', 3*latency},{v{r,c}},{v_delayed_signal});        
                        v{r,c} = v_delayed_signal;
                    end
                    if (r ~= i)
                        u_delayed_signal = xSignal;
                        xBlock('Delay',{'Latency', 3*latency*2},{u{r,c}},{u_delayed_signal});        
                        u{r,c} = u_delayed_signal;
                    else
                        u_delayed_signal = xSignal;
                        xBlock('Delay',{'Latency', 3*latency},{u{r,c}},{u_delayed_signal});        
                        u{r,c} = u_delayed_signal;   
                    end
                end
            end        
            for r=1:n,
                if r ~= j,
                    w_delayed_signal = xSignal;
                    xBlock('Delay',{'Latency', 3*latency*2},{w{r}},{w_delayed_signal});        
                    w{r} = w_delayed_signal;
                end;
            end
        end    
    end
end

for i = 1:n,
    for j=1:n,
        uij = xOutport(['u' num2str(i) num2str(j)]);
        uij_signal = u{i,j};
        uij.assign(uij_signal);
    end;
end;
for i = 1:n,
    for j=n+1:2*n,
        pij = xOutport(['p' num2str(i) num2str(j-n)]);
        pij_signal = u{i,j};
        pij.assign(pij_signal);
    end;
end;

end

function outSignal = constantInstance(init, inSignal)
    outSignal = xSignal;
    enSignal = xSignal;
    xBlock('Constant',{'arith_type','Boolean', 'const', 0},{},{enSignal});
    xBlock('Register',{'en','on','init', init},{inSignal, enSignal},{outSignal});
end

function a = supportLibrary
    a = 'sysgenMatrixInversionLib';
end