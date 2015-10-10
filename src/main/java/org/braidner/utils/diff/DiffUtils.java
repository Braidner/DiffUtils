package org.braidner.utils.diff;

import org.braidner.utils.exception.NotSameObjectsException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Braidner
 */
public class DiffUtils {

    private final static Class<Merge> MERGE_CLASS = Merge.class;

    /**
     * Get the differences of two objects.
     * Searching only field with annotation {@link Merge}
     *
     * @return Set of diffs, contains old and new values
     * @throws IllegalAccessException, NotSameObjectsException
     */
    public static <T> Set<DiffObject> difference(T mainObject, T mergeObject) throws IllegalAccessException, NotSameObjectsException {
        return difference(mainObject, mergeObject, MERGE_CLASS);
    }

    /**
     * Get the differences of two objects.
     * Searching only field with selected annotation
     *
     * @param annotation any annotation to fiend fields for merge. Default annotation {@link Merge}
     * @return Set of diffs, contains old and new values
     * @throws IllegalAccessException, NotSameObjectsException
     */
    public static <T> Set<DiffObject> difference(T mainObject, T mergeObject, Class<? extends Annotation> annotation) throws IllegalAccessException, NotSameObjectsException {
        annotation = annotation != null ? annotation : MERGE_CLASS;

        Set<DiffObject> differences = new HashSet<DiffObject>();

        Class<?> mainClass = mainObject.getClass();
        Class<?> mergeClass = mergeObject.getClass();

        if (!equals(mainClass, mergeClass)) {  //TODO add check for instances
            throw new NotSameObjectsException();
        }

        Set<Field> annotatedFields = findFields(mainClass, annotation);
        for (Field annotatedField : annotatedFields) {
            annotatedField.setAccessible(true);
            Object oldValue = annotatedField.get(mainObject);
            Object newValue = annotatedField.get(mergeObject);

            if (notEquals(oldValue, newValue)) {
                DiffObject difference = new DiffObject(oldValue, newValue, annotatedField);
                differences.add(difference);
            }
        }
        return differences;
    }


    /**
     * Simple merge two objects and returns string of changes.
     * Searching only field with annotation {@link Merge}.
     * If you want to change format of returning string you must implement interface {@link Difference}.
     *
     * @param mainObject  Main object to merge
     * @param mergeObject secondary object for merge
     * @param recursive   should merge inner objects
     * @return String of changes
     * @throws IllegalAccessException
     */
    public static String merge(Object mainObject, Object mergeObject, boolean recursive) throws IllegalAccessException {
        return merge(mainObject, mergeObject, recursive, false);
    }

    /**
     * Extended merge two objects and return string of changes.
     * Use this for mapping different classes. You should annotated your fields with {@link Merge} and specified annotation field {@link Merge#value()}
     *
     * @param mainObject  Main object to merge
     * @param mergeObject secondary object for merge
     * @param recursive   should merge inner objects
     * @return String of changes
     * @throws IllegalAccessException
     */
    public static String extendedMerge(Object mainObject, Object mergeObject, boolean recursive) throws IllegalAccessException {
        return merge(mainObject, mergeObject, recursive, true);
    }

    public static Set<DiffObject> extendedDifference(Object mainObject, Object mergeObject) throws IllegalAccessException {
        Map<Field, Field> fields = new HashMap<Field, Field>();
        Set<DiffObject> differences = new HashSet<DiffObject>();

        Class<?> mainClass = mainObject.getClass();
        Class<?> mergeClass = mergeObject.getClass();

        Set<Field> mainFields = findFields(mainClass, MERGE_CLASS);
        Set<Field> mergeFields = findFields(mergeClass, MERGE_CLASS);

        for (Field mainFiled : mainFields) {
            String mainName = mainFiled.getDeclaredAnnotation(MERGE_CLASS).value();
            if (mainName.length() > 0) {
                for (Field mergeField : mergeFields) {
                    if (mainName.equals(mergeField.getAnnotation(MERGE_CLASS).value())) {
                        fields.put(mainFiled, mergeField);
                    }
                }
            }
        }

        for (Field mainField : fields.keySet()) {
            Field mergeField = fields.get(mainField);

            mainField.setAccessible(true);
            mergeField.setAccessible(true);

            Object oldValue = mainField.get(mainObject);
            Object newValue = mergeField.get(mergeObject);

            if (notEquals(oldValue, newValue)) {
                differences.add(new DiffObject(oldValue, newValue, mainField));
            }
        }

        return differences;
    }

    private static String merge(Object mainObject, Object mergeObject, boolean recursive, boolean extended) throws IllegalAccessException {
        Set<DiffObject> differences = extended ? extendedDifference(mainObject, mergeObject) : difference(mainObject, mergeObject);

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
        Set<Field> set = new HashSet<Field>();
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

    public static boolean notEquals(final Object object1, final Object object2) {
        return !equals(object1, object2);
    }

    private static boolean equals(final Object object1, final Object object2) {
        return object1 == object2 || !(object1 == null || object2 == null) && object1.equals(object2);
    }
}
