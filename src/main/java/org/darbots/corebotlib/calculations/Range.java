package org.darbots.corebotlib.calculations;

public final class Range {
    public static int map(int number, int originalMin, int originalMax, int newMin, int newMax){
        int oldDelta = originalMax - originalMin;
        int newDelta = newMax - newMin;
        float startDelta = (number - originalMin);
        int scaled = newMin + Math.round(startDelta / oldDelta * newDelta);
        return scaled;
    }
    public static float map(float number, float originalMin, float originalMax, float newMin, float newMax){
        return newMin + ((number-originalMin) / (originalMax - originalMin) * (newMax - newMin));
    }
    public static double map(double number, double originalMin, double originalMax, double newMin, double newMax){
        return newMin + ((number-originalMin) / (originalMax - originalMin) * (newMax - newMin));
    }
    public static int clip(int number, int min, int max){
        if(number < min){
            number = min;
        }else if(number > max){
            number = max;
        }
        return number;
    }
    public static double clip(double number, double min, double max){
        if(number < min){
            number = min;
        }else if(number > max){
            number = max;
        }
        return number;
    }
    public static float clip(float number, float min, float max){
        if(number < min){
            number = min;
        }else if(number > max){
            number = max;
        }
        return number;
    }
}
