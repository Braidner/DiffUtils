package org.braidner.utils.diff.test;

import org.braidner.utils.diff.Merge;

/**
 * @author Braidner
 */
public class TestAnotherEntity {

    @Merge private String testString;
    @Merge private String anotherString;

    public TestAnotherEntity(String testString, String anotherString) {
        this.testString = testString;
        this.anotherString = anotherString;
    }

    public String getTestString() {
        return testString;
    }

    public void setTestString(String testString) {
        this.testString = testString;
    }

    public String getAnotherString() {
        return anotherString;
    }

    public void setAnotherString(String anotherString) {
        this.anotherString = anotherString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestAnotherEntity)) return false;

        TestAnotherEntity that = (TestAnotherEntity) o;

        if (testString != null ? !testString.equals(that.testString) : that.testString != null) return false;
        return !(anotherString != null ? !anotherString.equals(that.anotherString) : that.anotherString != null);

    }

    @Override
    public int hashCode() {
        int result = testString != null ? testString.hashCode() : 0;
        result = 31 * result + (anotherString != null ? anotherString.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TestAnotherEntity{" +
                "anotherString='" + anotherString + '\'' +
                ", testString='" + testString + '\'' +
                '}';
    }
}
