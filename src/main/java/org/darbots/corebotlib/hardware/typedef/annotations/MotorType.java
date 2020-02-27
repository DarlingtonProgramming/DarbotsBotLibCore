package org.darbots.corebotlib.hardware.typedef.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MotorType {
    String name();
    String manufacturer() default "Default Manufacturer";
    double minimumVoltage();
    double maximumVoltage();
    double maxRPM();
}
