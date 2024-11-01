package io.enderdev.patchoulibooks.util;

import io.enderdev.patchoulibooks.PatchouliBooks;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class AnnotationUtil {
    private static final Reflections REFLECTIONS;

    private AnnotationUtil() {
    }

    static {
        REFLECTIONS = new Reflections("io.enderdev.patchoulibooks");
    }

    public static Set<Class<?>> validateAnnotations(Class<? extends Annotation> decoratorClass, Class<?> validateClass) {
        Set<Class<?>> annotatedClasses = REFLECTIONS.getTypesAnnotatedWith(decoratorClass);
        boolean valid = true;
        for (Class<?> clazz : annotatedClasses) {
            if (!validateClass.isAssignableFrom(clazz)) {
                PatchouliBooks.LOGGER.error("Class {} is annotated with {} but does not extend {}.", clazz.getName(), decoratorClass.getName(), validateClass.getName());
                valid = false;
            }
        }
        return valid ? annotatedClasses : null;
    }
}
