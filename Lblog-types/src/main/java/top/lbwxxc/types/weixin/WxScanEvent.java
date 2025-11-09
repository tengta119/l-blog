package top.lbwxxc.types.weixin;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;

// @XmlRootElement(name = "xml") 告诉 JAXB 根元素是 <xml>
@XmlRootElement(name = "xml")
// @XmlAccessorType(XmlAccessType.FIELD) 告诉 JAXB 直接访问字段
@XmlAccessorType(XmlAccessType.FIELD)
@Data
public class WxScanEvent {

    // @XmlElement(name = "...") 将 XML 标签映射到 Java 字段
    // 即使 XML 标签是大写，Java 字段也可以用驼峰命名
    @XmlElement(name = "ToUserName")
    private String toUserName;

    @XmlElement(name = "FromUserName")
    private String fromUserName;

    @XmlElement(name = "CreateTime")
    private Long createTime;

    @XmlElement(name = "MsgType")
    private String msgType;

    @XmlElement(name = "Event")
    private String event;

    @XmlElement(name = "EventKey")
    private String eventKey;

    @XmlElement(name = "Ticket")
    private String ticket;

    // 必须有一个 public 的无参构造函数
    public WxScanEvent() {}

    // (为方便使用，可以添加 Getters 和 Setters，以及 toString 方法)

    @Override
    public String toString() {
        return "WxScanEvent{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType='" + msgType + '\'' +
                ", event='" + event + '\'' +
                ", eventKey='" + eventKey + '\'' +
                ", ticket='" + ticket + '\'' +
                '}';
    }
}