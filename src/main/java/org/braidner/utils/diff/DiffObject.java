package org.braidner.utils.diff;

import java.lang.reflect.Field;

/**
 * @author Braidner
 */
public class DiffObject {

    private Object oldValue;
    private Object newValue;
    private Field field;

    public DiffObject(Object oldValue, Object newValue, Field field) {
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.field = field;
    }

    public String mergeValues(Object toMerge) throws IllegalAccessException {
        field.set(toMerge, newValue);
        if (toMerge instanceof Difference) {
            return ((Difference) toMerge).buildDifference(this);
        }

        return field.getName() + " : " + oldValue + " => " + newValue;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String toString() {
        return "DiffObject{" +
                "oldValue=" + oldValue +
                ", newValue=" + newValue +
                ", field=" + field +
                '}';
    }
}
