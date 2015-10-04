package org.braidner.utils.diff.test;

import org.braidner.utils.diff.DiffObject;
import org.braidner.utils.diff.Difference;
import org.braidner.utils.diff.Merge;

/**
 * @author Braidner
 */
public class TestEntity implements Difference {


    @Merge private String firstName;
    @Merge private String lastName;
    @Merge private String login;
    @Merge private String password;
    @Merge private TestAnotherEntity anotherEntity;
    private String ignoredField;

    public TestEntity(String firstName, String lastName, String ignoredField) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.ignoredField = ignoredField;
    }

    public TestEntity(String firstName, String lastName, String login, String password, String ignoredField) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
        this.ignoredField = ignoredField;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIgnoredField() {
        return ignoredField;
    }

    public void setIgnoredField(String ignoredField) {
        this.ignoredField = ignoredField;
    }

    @Override
    public String buildDifference(DiffObject object) {
        Object oldValue = object.getOldValue();
        Object newValue = object.getNewValue();
        return oldValue + " => " + newValue;
    }

    public TestAnotherEntity getAnotherEntity() {
        return anotherEntity;
    }

    public void setAnotherEntity(TestAnotherEntity anotherEntity) {
        this.anotherEntity = anotherEntity;
    }
}
