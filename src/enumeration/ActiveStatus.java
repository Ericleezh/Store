package enumeration;

/**
 * 用户激活状态
 * @author PearRealGood
 *
 */
public enum ActiveStatus {

    ACTIVATED(1), //已激活
    INACTIVATED(0); //未激活

    private int value;

    private ActiveStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}


