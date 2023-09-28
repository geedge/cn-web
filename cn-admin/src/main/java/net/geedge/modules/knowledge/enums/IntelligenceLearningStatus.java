package net.geedge.modules.knowledge.enums;

import org.springframework.core.convert.converter.Converter;

/**
 * @Auther: lijinyang
 * @Date: 2023/09/25/17:22
 */
public enum IntelligenceLearningStatus {
    STOP("stop"),START("start");

    private String value;

    public String getValue() {
        return value;
    }

    private IntelligenceLearningStatus(String value) {
        this.value = value;
    }
}