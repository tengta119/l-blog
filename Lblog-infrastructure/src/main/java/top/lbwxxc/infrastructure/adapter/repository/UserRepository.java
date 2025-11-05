package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.user.adapter.repository.IUserRepository;
import top.lbwxxc.domain.user.model.entity.UserRegisterEntity;
import top.lbwxxc.domain.user.model.entity.UserDetailEntity;
import top.lbwxxc.infrastructure.dao.UserDao;
import top.lbwxxc.infrastructure.dao.po.User;

@Repository
public class UserRepository implements IUserRepository {

    @Resource
    private UserDao userDao;

    @Override
    public UserDetailEntity getUserByPhone(String phone) {

        User userByPhone = userDao.getUserByPhone(phone);

        return getUser(userByPhone);
    }

    @Override
    public UserDetailEntity getUserByEmail(String email) {

        User userByEmail = userDao.getUserByEmail(email);

        return getUser(userByEmail);
    }

    private UserDetailEntity getUser(User user) {
        if (user != null) {
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
        } else {
            return null;
        }
    }

    @Override
    public UserDetailEntity createUserByPhone(UserRegisterEntity userVO) {

        User user = User.builder()
                .phone(userVO.getPhone())
                .password(userVO.getPassword())
                .nickname("默认名称")
                .build();
        userDao.insertSelective(user);

        User userByPhone = userDao.getUserByPhone(userVO.getPhone());

        return createUser(userByPhone);
    }

    @Override
    public UserDetailEntity createUserByEmail(UserRegisterEntity userVO) {

        User user = User.builder()
                .email(userVO.getEmail())
                .password(userVO.getPassword())
                .nickname("默认名称")
                .build();
        userDao.insertSelective(user);

        User userByEmail = userDao.getUserByEmail(userVO.getEmail());
        return createUser(userByEmail);
    }

    private UserDetailEntity createUser(User user) {
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
