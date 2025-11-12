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
public class UserInfoEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;


    /**
     * 微信用户的唯一标识
     */
    private String openid;

    /**
     * 用户的唯一邮箱
     */
    private String email;

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
     * 性别(0：女 1：男)
     */
    private int sex;


    /**
     * 个人简介
     */
    private String introduction;



}
