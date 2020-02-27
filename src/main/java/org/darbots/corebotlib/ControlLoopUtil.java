package org.darbots.corebotlib;

import java.util.LinkedList;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class ControlLoopUtil {
    public static LinkedList<Callable> callablesToRefreshData = new LinkedList<>();
    public static void registerRefreshDataCallable(Callable callable){
        callablesToRefreshData.add(callable);
    }
    public static void clearRegisteredCallables(){
        callablesToRefreshData.clear();
    }
    public static void clearData() throws Exception{
        for(Callable i : callablesToRefreshData){
            i.call();
        }
    }
}
