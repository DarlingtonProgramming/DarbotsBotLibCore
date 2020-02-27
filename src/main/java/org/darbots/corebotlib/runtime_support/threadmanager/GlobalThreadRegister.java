package org.darbots.corebotlib.runtime_support.threadmanager;

import org.darbots.corebotlib.runtime_support.RobotLifeCyclePhase;
import org.darbots.corebotlib.hardware.typedef.Destroyable;
import org.darbots.corebotlib.runtime_support.GlobalSettings;

public class GlobalThreadRegister {
    public static void registerDestroyable(Destroyable destroyable){
        if(GlobalSettings.separateThreadSensorDestroyPhase == RobotLifeCyclePhase.PROGRAM_LIFE_CYCLE){
            ProgramCycleThreadRegister.registerDestroyable(destroyable);
        }else{
            RobotCycleThreadRegister.registerDestroyable(destroyable);
        }
    }
}
