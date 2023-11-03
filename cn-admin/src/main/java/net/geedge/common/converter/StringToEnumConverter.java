package net.geedge.common.converter;

import net.geedge.common.exception.CNException;
import net.geedge.modules.knowledge.enums.IntelligenceLearningStatus;
import org.springframework.core.convert.converter.Converter;

/**
 * @Auther: lijinyang
 * @Date: 2023/09/25/18:06
 */

public class StringToEnumConverter implements Converter<String, IntelligenceLearningStatus> {
    @Override
    public IntelligenceLearningStatus convert(String source) {
        try {
            return IntelligenceLearningStatus.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CNException("enum param conversion failed");
        }
    }
}
