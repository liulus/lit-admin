package com.lit.test.other;

/**
 * User : liulu
 * Date : 2017-1-7 12:23
 * version $Id: Parent.java, v 0.1 Exp $
 */
public class Parent {

    private String pPrivate;

    String pDefault;

    protected String pProtected;

    public String pPublic;

    static {
        System.out.println(" parent static block initializing ... ");
    }

    {
        System.out.println(" parent non-static block initializing ... ");
    }

    public Parent() {
        System.out.println(" parent constructor initializing ... ");
    }

    public String getpPrivate() {
        return pPrivate;
    }

    public void setpPrivate(String pPrivate) {
        this.pPrivate = pPrivate;
    }

    public String getpDefault() {
        return pDefault;
    }

    public void setpDefault(String pDefault) {
        this.pDefault = pDefault;
    }

    public String getpProtected() {
        return pProtected;
    }

    public void setpProtected(String pProtected) {
        this.pProtected = pProtected;
    }

    public String getpPublic() {
        return pPublic;
    }

    public void setpPublic(String pPublic) {
        this.pPublic = pPublic;
    }
}
