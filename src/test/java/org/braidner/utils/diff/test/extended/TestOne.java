package org.braidner.utils.diff.test.extended;

import org.braidner.utils.diff.Merge;

/**
 * @author Braidner
 */
public class TestOne {

    @Merge("login") private String login;
    @Merge("password") private String password;
    @Merge private String anotherField;

    public TestOne(String login, String password, String anotherField) {
        this.login = login;
        this.password = password;
        this.anotherField = anotherField;
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

    public String getAnotherField() {
        return anotherField;
    }

    public void setAnotherField(String anotherField) {
        this.anotherField = anotherField;
    }
}
