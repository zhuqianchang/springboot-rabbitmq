package indi.zqc.rabbitmq.base.constants;

/**
 * Title : ExchangeType.java
 * Package : indi.zqc.rabbitmq.base.constants
 * Description : 交换器类型
 * Create on : 2018/1/8 10:57
 *
 * @author Zhu.Qianchang
 * @version v1.0.0
 */
public enum ExchangeType {

    FANOUT("fanout"), DIRECT("direct"), TOPIC("topic");

    private String key;

    ExchangeType(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
