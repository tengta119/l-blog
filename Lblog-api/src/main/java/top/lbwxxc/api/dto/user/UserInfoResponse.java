package top.lbwxxc.api.dto.user;


import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserInfoResponse {

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
    private String author;

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
     * 性别(0：女 1：男)
     */
    private int sex;


    /**
     * 个人简介
     */
    private String introduction;

}
