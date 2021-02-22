package com.github.stephenenright.fileuploader.common.utils;

public abstract class ClassUtils {

    public static String getClassNameWords(Class<?> cls) {
        final String simpleName = cls.getSimpleName();

        if (simpleName.length() <= 1) {
            return simpleName;
        }

        StringBuilder builder = new StringBuilder();
        builder.append(simpleName.charAt(0));

        for (int i = 1; i < simpleName.length(); ++i) {
            char c = simpleName.charAt(i);

            if (Character.isUpperCase(c)) {
                builder.append(" ");
            }

            builder.append(c);
        }

        return builder.toString();
    }
}
