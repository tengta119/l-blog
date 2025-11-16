package top.lbwxxc.infrastructure.dao.po;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * user_detail
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetail implements Serializable {
    /**
     * 用户id
     */
    private Long id;

    /**
     * 作者名
     */
    private String author;

    /**
     * 自由介绍
     */
    private String introduction;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private LocalDate birthday;

    /**
     * 背景图
     */
    private String backgroundImg;

    /**
     * 性别
     */
    private Integer sex;

    private static final long serialVersionUID = 1L;
}