#ifndef XFIXCIRPIPE_H
#define XFIXCIRPIPE_H

#include "sysgen/sg_config.h"
#include "sysgen/XFix.h"

/**
 * XFixCirPipe is a fixed size circular buffer of xfix objects. Head is
 *  where an element is popped, tail is where an element is pushed.
 */
class SG_API XFixCirPipe {
public:
    XFixCirPipe();
    XFixCirPipe(int latency, const Sysgen::XFix& proto=Sysgen::XFix(0));
    virtual ~XFixCirPipe();

    virtual void setLatency(int lat);
    virtual void setPrototype(const Sysgen::XFix& proto);
    virtual void config(int lat, const Sysgen::XFix& proto);
    virtual void resize(int lat, const Sysgen::XFix& proto);
    virtual void setAll(const Sysgen::XFix& v) {
        int i;
        for (i=0; i<length; i++)
            cirpipe[i] = v;
    }
    inline virtual Sysgen::XFix& tail() {
        return cirpipe[tailIdx()];
    }
    inline virtual Sysgen::XFix& head() { return cirpipe[headIdx()]; }
    inline virtual void advance(Sysgen::XFix& q) {
        pos = (pos + length - 1) % length;
        Sysgen::XFix& t = tail();
        t = q;
    }
    inline virtual void advance() {
        pos = (pos +length - 1) % length;
    }
    /**
     * the [idx] operator is meaningful if 0<=idx<=pipeLength - 1
     */
    virtual Sysgen::XFix& operator[](int idx) {
        if (idx<0)
            idx = 0;
        idx = idx%(length - extra());
        return cirpipe[(tailIdx()+idx)%length];
    }
    inline virtual int size() { return length - extra(); }

    inline virtual void setPipelineValid(bool flag) {
        int i;
        if (!cirpipe)
            return;

        for (i=0; i<length; i++)
            cirpipe[i].setPipelineValidFlag(flag);
    }
private:
    Sysgen::XFix *cirpipe;
    int pos;
    int length;
    /**
     * inorder to make it safer, one or more "unused" elements could be placed
     * between tail and head.
     */
    static const int _extra;
    static int extra();

    inline virtual int tailIdx() {
        return pos;
    }

    inline virtual int headIdx() {
        return (pos + length - extra() - 1) % length;
    }

};

#endif
