/*
 *  WaitBoxManager.h
 *
 *  Copyright (c) 2005, Xilinx, Inc. All rights reserved.
 *
 *  Description: Static class interface for the model scoped waitboxes
 */

#ifndef _SYSGEN_WAIT_BOX_MANAGER_H_
#define _SYSGEN_WAIT_BOX_MANAGER_H_

// Xilinx inclusions:
#include "sysgen/sg_config.h"

// Standard Library inclusions:
#include <string>

class WboxProxy;

namespace Sysgen {
    /// \ingroup public_utility
    /// \brief Manages the model-scoped waitbox
    class WaitBoxManager
    {
    public:
        //! @name Static class interface
        //@{

        SG_API static const unsigned char SHOW_CLOSE;
        SG_API static const unsigned char SHOW_CANCEL;
        SG_API static const unsigned char SHOW_DETAIL;
        SG_API static const unsigned char ENABLE_CLOSE;
        SG_API static const unsigned char ENABLE_CANCEL;
        SG_API static const unsigned char ENABLE_DETAIL;
        SG_API static const char* SYSGEN_STATUS;

        /* \brief Create a new waitbox if one does not exist,
         * otherwise create a new waitbox context.
         */
        SG_API static void start(const std::string &title, const std::string &text="",
                                 const unsigned char flags = 0);
        /* \brief Destory the current waitbox context and restore the
         * previous one.  If no other contexts exist, close the
         * waitbox GUI.
         */
        SG_API static void done();

        /* \brief Terminate the waitbox GUI
         */
        SG_API static void stop();

        SG_API static void hide();
        SG_API static void unhide();

        

        /* \brief Freeze the animation in the waitbox GUI
         */
        SG_API static void freezeAnimation();

        /* \brief Resume the animation in the waitbox GUI
         */
        SG_API static void resumeAnimation();

        SG_API static void setTitle(const std::string &title);
        SG_API static void setText(const std::string &text);
        SG_API static void setDetail(const std::string &detail);
        SG_API static void appendDetail(const std::string &detail);

        SG_API static std::string getTitle(void);
        SG_API static std::string getText(void);
        SG_API static std::string getDetail(void);

        // Sets whether the button should be displayed or not
        SG_API static void showClose(bool); 
        SG_API static void showCancel(bool);
        SG_API static void showDetail(bool);
        // Sets whether the button should be in the enabled or disable state
        SG_API static void enableClose(bool);
        SG_API static void enableCancel(bool);
        SG_API static void enableDetail(bool);

        // Indicates whether or not the cancel button has been pressed
        SG_API static bool cancelPressed();

        // Indicates whether the status box window is currently in the
        // running state
        SG_API static bool isRunning();

        //@}
    protected:
        virtual ~WaitBoxManager() {}
        static WboxProxy* getWBox(void);
    };

    /*! \ingroup public_utility
     *  \brief An interface to the waitbox manager that creates a new
     *  waitbox context that is destroyed when the class instance is destroyed.
     */
    class ScopedWaitBox : public WaitBoxManager 
    {
    public:
        SG_API ScopedWaitBox(const std::string &msg="", const unsigned char flags = 0);
        SG_API virtual ~ScopedWaitBox();
    };

}

#endif // _SYSGEN_WAIT_BOX_MANAGER_H_
