/**

 *

 *
 * 
 */

package net.geedge.common.validator;

import net.geedge.common.exception.CNException;
import org.apache.commons.lang.StringUtils;

/**
 * 数据校验
 *
 * @author Mark sunlightcs@gmail.com
 */
public abstract class Assert {

    public static void isBlank(String str, String message) {
        if (StringUtils.isBlank(str)) {
            throw new CNException(message);
        }
    }

    public static void isNull(Object object, String message) {
        if (object == null) {
            throw new CNException(message);
        }
    }
}
