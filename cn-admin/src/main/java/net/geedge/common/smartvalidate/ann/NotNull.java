package net.geedge.common.smartvalidate.ann;

import net.geedge.common.utils.RCode;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
@Documented
public @interface NotNull {
	RCode code() default RCode.ERROR;
}
