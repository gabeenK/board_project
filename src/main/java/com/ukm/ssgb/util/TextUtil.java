package com.ukm.ssgb.util;

import org.apache.commons.lang3.StringUtils;

public class TextUtil {

    public static String cutByMaxLength(String message, int maxLength) {
        return StringUtils.substring(message, 0, maxLength);
    }
}
