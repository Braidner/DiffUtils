package org.braidner.utils.diff.test.extended;

import org.braidner.utils.diff.Merge;

/**
 * @author Braidner
 */
public class TestTwo {

    @Merge("login") private String wowow;
    @Merge("password") private String lwoe;
    @Merge private String anotherasd;

    public TestTwo(String wowow, String lwoe, String anotherasd) {
        this.wowow = wowow;
        this.lwoe = lwoe;
        this.anotherasd = anotherasd;
    }

    public String getWowow() {
        return wowow;
    }

    public void setWowow(String wowow) {
        this.wowow = wowow;
    }

    public String getLwoe() {
        return lwoe;
    }

    public void setLwoe(String lwoe) {
        this.lwoe = lwoe;
    }

    public String getAnotherasd() {
        return anotherasd;
    }

    public void setAnotherasd(String anotherasd) {
        this.anotherasd = anotherasd;
    }
}
