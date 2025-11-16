package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.login.adapter.repository.IUserAccountRepository;
import top.lbwxxc.domain.login.model.entity.LoginUserDetailEntity;
import top.lbwxxc.domain.login.model.entity.UserRoleEntity;
import top.lbwxxc.infrastructure.dao.UserDao;
import top.lbwxxc.infrastructure.dao.UserRoleRelDao;
import top.lbwxxc.infrastructure.dao.po.User;
import top.lbwxxc.infrastructure.dao.po.UserRoleRel;
import top.lbwxxc.types.enums.CreateUserType;
import top.lbwxxc.types.enums.SelectUserType;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserAccountRepository implements IUserAccountRepository {

    @Resource
    private UserDao userDao;
    @Resource
    private UserRoleRelDao userRoleRelDao;

    @Override
    public LoginUserDetailEntity getUser(String str, SelectUserType selectUserType) {

        User user = null;
        if (selectUserType.equals(SelectUserType.SELECT_USER_EMAIL)) {
            user = userDao.getUserByEmail(str);
        } else if (selectUserType.equals(SelectUserType.SELECT_USER_PHONE)) {
            user = userDao.getUserByPhone(str);
        } else if (selectUserType.equals(SelectUserType.SELECT_USER_OPENID)) {
            user = userDao.getUserByOpenId(str);
        } else if (selectUserType.equals(SelectUserType.SELECT_USER_ID)) {
            user = userDao.selectByPrimaryKey(Long.parseLong(str));
        }
        if (user != null) {
            return getUserDetailEntity(user);
        } else {
            return null;
        }
    }

    @Override
    public LoginUserDetailEntity createUser(String str, String password, CreateUserType createUserType) {
        User user = new User();
        user.setNickname("默认名称");
        if (createUserType.equals(CreateUserType.CREATE_USER_OPENID)) {
            user.setOpenid(str);
            userDao.insertSelective(user);
            return getUserDetailEntity(userDao.getUserByOpenId(str));
        }

        user.setPassword(password);
        if (createUserType.equals(CreateUserType.CREATE_USER_PHONE)) {
            user.setPhone(str);
            userDao.insertSelective(user);
            return getUserDetailEntity(userDao.getUserByPhone(str));
        }

        if (createUserType.equals(CreateUserType.CREATE_USER_EMAIL)) {
            user.setEmail(str);
            userDao.insertSelective(user);
            return getUserDetailEntity(userDao.getUserByEmail(str));
        }
        return null;
    }

    @Override
    public int updateUserPasswordById(String str, SelectUserType selectUserType, String password) {
        if (selectUserType.equals(SelectUserType.SELECT_USER_EMAIL)) {
            return userDao.updateUserPasswordByEmail(str, password);
        } else if (selectUserType.equals(SelectUserType.SELECT_USER_PHONE)) {
            return userDao.updateUserPasswordByPhone(str, password);
        }
        return 0;
    }

    @Override
    public UserRoleEntity getUserRoleById(long userId) {

        List<UserRoleRel> userRoleRels = userRoleRelDao.selectByUserId(userId);
        if (userRoleRels == null || userRoleRels.isEmpty()) {
            return null;
        }

        List<Long> roleIds = new ArrayList<>();
        for (UserRoleRel userRoleRel : userRoleRels) {
            roleIds.add(userRoleRel.getRoleId());
        }

        return UserRoleEntity.builder()
                .userId(userId)
                .roleIds(roleIds)
                .build();
    }

    private LoginUserDetailEntity getUserDetailEntity(User user) {
        return LoginUserDetailEntity.builder()
                .id(user.getId())
                .password(user.getPassword())
                .build();
    }
}
