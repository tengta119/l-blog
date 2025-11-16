package top.lbwxxc.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import top.lbwxxc.infrastructure.dao.po.User;

@Mapper
public interface UserDao {


    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    // 根据手机号查询用户
    User getUserByPhone(String phone);

    // 根据邮箱查询用户
    User getUserByEmail(String email);

    User getUserByOpenId(String openid);

    // 更新用户密码
    int updateUserPasswordByPhone(String phone, String password);

    int updateUserPasswordByEmail(String email, String password);
}