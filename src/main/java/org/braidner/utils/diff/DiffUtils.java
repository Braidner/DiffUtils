package org.braidner.utils.diff;

import org.braidner.utils.exception.NotSameObjectsException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Braidner
 */
public class DiffUtils {

    /**
     * Get the differences of two objects.
     * Searching only field with annotation {@link Merge}
     * @return Set of diffs, contains old and new values
     * @throws IllegalAccessException, NotSameObjectsException
     */
    public static Set<DiffObject> difference(Object mainObject, Object mergeObject) throws IllegalAccessException, NotSameObjectsException {
        Set<DiffObject> differences = new HashSet<>();

        Class<?> mainClass = mainObject.getClass();
        Class<?> mergeClass = mergeObject.getClass();

        if (!equals(mainClass, mergeClass)) {  //TODO add check for instances
            throw new NotSameObjectsException();
        }

        Set<Field> annotatedFields = findFields(mainClass, Merge.class);
        for (Field annotatedField : annotatedFields) {
            annotatedField.setAccessible(true);
            Object oldValue = annotatedField.get(mainObject);
            Object newValue = annotatedField.get(mergeObject);

            if (!equals(oldValue, newValue)) {
                DiffObject difference = new DiffObject(oldValue, newValue, annotatedField);
                differences.add(difference);
            }
        }
        return differences;
    }


    /**
     * Merge two objects and returns string of changes.
     * Searching only field with annotation {@link Merge}.
     * If you want to change format of returning string you must implement interface {@link Difference}.
     * @param mainObject Main object to merge
     * @param mergeObject secondary object for merge
     * @param recursive should merge inner objects
     * @return String of changes
     * @throws IllegalAccessException
     */
    public static String merge(Object mainObject, Object mergeObject, boolean recursive) throws IllegalAccessException {
        Set<DiffObject> differences = difference(mainObject, mergeObject);

        StringBuilder builder = new StringBuilder();

        for (DiffObject difference : differences) {
            if (recursive) {
                String mergeString = merge(difference, true);
                if (mergeString.length() > 0) {
                    builder.append(mergeString);
                } else {
                    builder.append(difference.mergeValues(mainObject)).append("\n");
                }
            } else {
                builder.append(difference.mergeValues(mainObject)).append("\n");

            }
        }
        return builder.toString();
    }

    private static String merge(DiffObject difference, boolean recursive) throws IllegalAccessException {
        Object mainObject = difference.getOldValue();
        Object mergeObject = difference.getNewValue();
        return merge(mainObject, mergeObject, recursive);
    }

    private static Set<Field> findFields(Class<?> clazz, Class<? extends Annotation> ann) {
        Set<Field> set = new HashSet<>();
        Class<?> c = clazz;
        while (c != null) {
            for (Field field : c.getDeclaredFields()) {
                if (field.isAnnotationPresent(ann)) {
                    set.add(field);
                }
            }
            c = c.getSuperclass();
        }
        return set;
    }

    private static boolean equals(final Object object1, final Object object2) {
        return object1 == object2 || !(object1 == null || object2 == null) && object1.equals(object2);
    }
}
