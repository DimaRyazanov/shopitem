package ru.shopitem.util;

import ru.shopitem.model.AbstractBaseEntity;

public class ValidationUtil {
    private ValidationUtil(){}

    public static <T extends AbstractBaseEntity> void checkIsNew(T object) {
        if (!object.isNew()) {
            throw new IllegalArgumentException(object + " must be new (id=null)");
        }
    }

    public static <T extends AbstractBaseEntity> void checkIsNotNew(T object) {
        if (object.isNew()) {
            throw new IllegalArgumentException(object + " must be new (id=null)");
        }
    }

    public static <T> void checkNotFound(T object, String msg) {
        if (object == null) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getLocalizedMessage() != null ? e.getLocalizedMessage() : e.getClass().getName();
    }
}
