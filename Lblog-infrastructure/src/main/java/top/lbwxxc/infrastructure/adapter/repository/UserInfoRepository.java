package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.user.adapter.repository.IUserInfoRepository;
import top.lbwxxc.domain.user.model.entity.UserInfoEntity;
import top.lbwxxc.infrastructure.dao.UserDao;
import top.lbwxxc.infrastructure.dao.UserDetailDao;
import top.lbwxxc.infrastructure.dao.po.User;
import top.lbwxxc.infrastructure.dao.po.UserDetail;

@Repository
@Slf4j
public class UserInfoRepository implements IUserInfoRepository {

    @Resource
    private UserDao userDao;
    @Resource
    private UserDetailDao userDetailDao;

    @Override
    public UserInfoEntity getUserInfoById(Long id) {
        User user = userDao.selectByPrimaryKey(id);
        UserDetail userDetail = userDetailDao.selectByPrimaryKey(id);

        return UserInfoEntity.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .openid(user.getOpenid())
                .email(user.getEmail())
                .author(userDetail.getAuthor())
                .avatar(userDetail.getAvatar())
                .birthday(userDetail.getBirthday())
                .backgroundImg(userDetail.getBackgroundImg())
                .introduction(userDetail.getIntroduction())
                .sex(userDetail.getSex())
                .build();
    }
}
