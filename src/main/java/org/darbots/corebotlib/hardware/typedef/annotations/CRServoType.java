package org.darbots.corebotlib.hardware.typedef.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CRServoType {
    String name();
    String manufacturer() default "Default Manufacturer";
    double pwmMicroSecMin() default 1500;
    double pwmMicroSecMax() default 2500;
    double secondsToTurn60Deg();
    double minimumVoltage();
    double maximumVoltage();
}
