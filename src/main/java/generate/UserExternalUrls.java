package generate;

import java.io.Serializable;
import lombok.Data;

/**
 * user_external_urls
 */
@Data
public class UserExternalUrls implements Serializable {
    /**
     * 主键（自增），唯一标识一条记录。
     */
    private Long id;

    /**
     * 	外键，关联用户表的主键（如 users.id），明确该链接属于哪个用户
     */
    private Long userId;

    /**
     * 第三方平台标识（如 github、csdn、zhihu），用英文小写统一规范。
     */
    private String platform;

    /**
     * 具体的第三方网址（如 https://github.com/username），长度预留充足。
     */
    private String url;

    /**
     * 第三方网址的 icon 地址
     */
    private String icon;

    private static final long serialVersionUID = 1L;
}