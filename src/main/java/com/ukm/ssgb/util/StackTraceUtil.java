package com.ukm.ssgb.util;

import io.jsonwebtoken.lang.Strings;

public class StackTraceUtil {

    public static String getPrintStackTrace(Throwable e) {
        if (e == null) {
            return Strings.EMPTY;
        }

        StringBuilder sb = new StringBuilder();

        // 루트 메시지 추가
        sb.append(e);

        // 스택 트레이스 추가
        StackTraceElement[] elements = e.getStackTrace();
        for (StackTraceElement element : elements) {
            // 내 소스와 관련된 트레이스만 출력
            if (!isUkmSsgbClass(element)) {
                continue;
            }

            sb.append("\n\tat ");
            sb.append(element);
        }

        return TextUtil.cutByMaxLength(sb.toString(), 3000);
    }

    static boolean isUkmSsgbClass(StackTraceElement StackTraceElement) {
        String className = StackTraceElement.getClassName();
        return className.startsWith("com.ukm.ssgb");
    }
}