package net.geedge.common.annotation;

import net.geedge.common.utils.OperationEnum;
import net.geedge.common.utils.TypeEnum;

import java.lang.annotation.*;

/**
 * 系统日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

    OperationEnum operation() default OperationEnum.UNKNOWN;

    TypeEnum type() default TypeEnum.UNKNOWN;
}
