package net.skeyurt.lit.test.other;

/**
 * User : liulu
 * Date : 2017-1-7 12:23
 * version $Id: Child.java, v 0.1 Exp $
 */
public class Child extends Parent {

    private String cPrivate;

    String cDefault;

    protected String cProtected;

    public String cPublic;

    static {
        System.out.println(" child static block initializing ... ");
    }

    {
        System.out.println(" child non-static block initializing ... ");
    }

    public Child() {
        System.out.println(" child constructor initializing ... ");
    }

    public String getcPrivate() {
        return cPrivate;
    }

    public void setcPrivate(String cPrivate) {
        this.cPrivate = cPrivate;
    }

    public String getcDefault() {
        return cDefault;
    }

    public void setcDefault(String cDefault) {
        this.cDefault = cDefault;
    }

    public String getcProtected() {
        return cProtected;
    }

    public void setcProtected(String cProtected) {
        this.cProtected = cProtected;
    }

    public String getcPublic() {
        return cPublic;
    }

    public void setcPublic(String cPublic) {
        this.cPublic = cPublic;
    }
}
