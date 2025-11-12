package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.login.adapter.repository.IUserAccountRepository;
import top.lbwxxc.domain.login.model.entity.UserRegisterEntity;
import top.lbwxxc.domain.login.model.entity.UserDetailEntity;
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
    public UserDetailEntity getUser(String str, SelectUserType selectUserType) {

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
    public UserDetailEntity createUser(UserRegisterEntity userRegisterEntity) {
        User user = new User();
        user.setNickname("默认名称");
        CreateUserType createUserType = userRegisterEntity.getCreateUserType();
        if (createUserType.equals(CreateUserType.CREATE_USER_OPENID)) {
            user.setOpenid(userRegisterEntity.getOpenid());
            userDao.insertSelective(user);
            return getUserDetailEntity(userDao.getUserByOpenId(userRegisterEntity.getOpenid()));
        }

        user.setPassword(userRegisterEntity.getPassword());
        if (createUserType.equals(CreateUserType.CREATE_USER_PHONE)) {
            user.setPhone(userRegisterEntity.getPhone());
            userDao.insertSelective(user);
            return getUserDetailEntity(userDao.getUserByPhone(userRegisterEntity.getPhone()));
        }

        if (createUserType.equals(CreateUserType.CREATE_USER_EMAIL)) {
            user.setEmail(userRegisterEntity.getEmail());
            userDao.insertSelective(user);
            return getUserDetailEntity(userDao.getUserByEmail(userRegisterEntity.getEmail()));
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

    private UserDetailEntity getUserDetailEntity(User user) {
        return UserDetailEntity.builder()
                .id(user.getId())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .birthday(user.getBirthday())
                .backgroundImg(user.getBackgroundImg())
                .phone(user.getPhone())
                .status(user.getStatus())
                .status(user.getStatus())
                .introduction(user.getIntroduction())
                .createTime(user.getCreateTime())
                .updateTime(user.getUpdateTime())
                .isDeleted(user.getIsDeleted())
                .openid(user.getOpenid())
                .build();
    }
}
