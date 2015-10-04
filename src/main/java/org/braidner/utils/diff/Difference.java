package org.braidner.utils.diff;

/**
 * @author Braidner
 *
 * Support interface for changing difference output
 */
public interface Difference {
    String buildDifference(DiffObject oldValue);
}
