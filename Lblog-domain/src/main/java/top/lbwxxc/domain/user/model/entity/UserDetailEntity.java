package top.lbwxxc.domain.user.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDetailEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 生日
     */
    private Date birthday;

    /**
     * 背景图
     */
    private String backgroundImg;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 性别(0：女 1：男)
     */
    private int sex;

    /**
     * 状态(0：启用 1：禁用)
     */
    private int status;

    /**
     * 个人简介
     */
    private String introduction;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 逻辑删除(0：未删除 1：已删除)
     */
    private Boolean isDeleted;

    /**
     * 微信用户的唯一标识
     */
    private String openid;


}
