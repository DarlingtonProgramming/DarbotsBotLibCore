package org.darbots.corebotlib.hardware.typedef.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EncoderType {
    String name();
    String manufacturer() default "Default Manufacturer";
    double operatingVoltage();
    double ticksPerRev();
}
