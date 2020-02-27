package org.darbots.corebotlib;

import org.darbots.corebotlib.hardware.typedef.Destroyable;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * This class is for all separate thread codes to register.
 * This helps us to clean up threads at the end of a robot program life cycle, even if some threads have not been terminated properly.
 */
public class LifeCycleThreadRegister {
    public static LinkedList<Destroyable> allDestroyable = new LinkedList<>();
    public static void registerDestroyable(Destroyable destroyable){
        if(destroyable != null)
            allDestroyable.add(destroyable);
    }
    public static void terminateAllDestroyable(){
        Iterator<Destroyable> mIterator = allDestroyable.iterator();
        while(mIterator.hasNext()){
            Destroyable currentDestroyable = mIterator.next();
            currentDestroyable.terminate();
        }
        allDestroyable.clear();
    }
}
