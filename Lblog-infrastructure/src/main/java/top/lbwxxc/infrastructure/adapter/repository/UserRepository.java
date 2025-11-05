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

        if (userByPhone != null) {
            return UserDetailEntity.builder()
                    .id(userByPhone.getId())
                    .password(userByPhone.getPassword())
                    .nickname(userByPhone.getNickname())
                    .avatar(userByPhone.getAvatar())
                    .birthday(userByPhone.getBirthday())
                    .backgroundImg(userByPhone.getBackgroundImg())
                    .phone(userByPhone.getPhone())
                    .status(userByPhone.getStatus())
                    .status(userByPhone.getStatus())
                    .introduction(userByPhone.getIntroduction())
                    .createTime(userByPhone.getCreateTime())
                    .updateTime(userByPhone.getUpdateTime())
                    .isDeleted(userByPhone.getIsDeleted())
                    .openid(userByPhone.getOpenid())
                    .build();
        } else {
            return null;
        }

    }

    @Override
    public UserDetailEntity createUser(UserRegisterEntity userVO) {
        User user = User.builder()
                .phone(userVO.getPhone())
                .password(userVO.getPassword())
                .nickname("默认名称")
                .build();

        int insert = userDao.insertSelective(user);

        User userByPhone = userDao.getUserByPhone(userVO.getPhone());

        return UserDetailEntity.builder()
                .id(userByPhone.getId())
                .password(userByPhone.getPassword())
                .nickname(userByPhone.getNickname())
                .avatar(userByPhone.getAvatar())
                .birthday(userByPhone.getBirthday())
                .backgroundImg(userByPhone.getBackgroundImg())
                .phone(userByPhone.getPhone())
                .status(userByPhone.getStatus())
                .status(userByPhone.getStatus())
                .introduction(userByPhone.getIntroduction())
                .createTime(userByPhone.getCreateTime())
                .updateTime(userByPhone.getUpdateTime())
                .isDeleted(userByPhone.getIsDeleted())
                .openid(userByPhone.getOpenid())
                .build();
    }
}
