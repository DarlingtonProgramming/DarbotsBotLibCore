package org.darbots.corebotlib.runtime_support;

import org.darbots.corebotlib.runtime_support.threadmanager.ProgramCycleThreadRegister;
import org.darbots.corebotlib.runtime_support.threadmanager.RobotCycleThreadRegister;

public class GlobalEntry {
    /**
     * This function should be embedded into your native robot controller software, as long as a hardware is detected this should be called.
     */
    public static void startRobot(){

    }

    /**
     * This function should be called when a specific program for the robot starts.
     */
    public static void startLifeCycle(){

    }

    /**
     * This function should be called when a specific program for the robot stops
     */
    public static void stopLifeCycle(){
        ProgramCycleThreadRegister.terminateAllDestroyable();
    }

    /**
     * This function should be embedded into your native robot controller software, as long as the entire hardware being controlled in detached, or the app is trying to quit, this should be called.
     */
    public static void terminateRobot(){
        ControlLoopUtil.clearRegisteredCallables();
        RobotCycleThreadRegister.terminateAllDestroyable();
    }
}
