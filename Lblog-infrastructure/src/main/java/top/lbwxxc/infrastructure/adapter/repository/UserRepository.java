package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.user.adapter.repository.IUserRepository;
import top.lbwxxc.domain.user.model.entity.UserRegisterEntity;
import top.lbwxxc.domain.user.model.entity.UserDetailEntity;
import top.lbwxxc.infrastructure.dao.UserDao;
import top.lbwxxc.infrastructure.dao.po.User;
import top.lbwxxc.types.enums.CreateUserType;
import top.lbwxxc.types.enums.SelectUserType;

@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetailEntity getUser(String str, SelectUserType selectUserType) {

        User user = null;
        if (selectUserType.equals(SelectUserType.SELECT_USER_EMAIL)) {
            user = userDao.getUserByEmail(str);
        } else if (selectUserType.equals(SelectUserType.SELECT_USER_PHONE)) {
            user = userDao.getUserByPhone(str);
        } else if (selectUserType.equals(SelectUserType.SELECT_USER_OPENID)) {
            user = userDao.getUserByOpenId(str);
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
