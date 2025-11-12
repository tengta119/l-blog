package top.lbwxxc.infrastructure.adapter.repository;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import top.lbwxxc.domain.user.adapter.repository.IUserInfoRepository;
import top.lbwxxc.domain.user.model.entity.UserInfoEntity;
import top.lbwxxc.infrastructure.dao.UserDao;
import top.lbwxxc.infrastructure.dao.po.User;

@Repository
@Slf4j
public class UserInfoRepository implements IUserInfoRepository {

    @Resource
    private UserDao userDao;

    @Override
    public UserInfoEntity getUserInfoById(Long id) {
        User user = userDao.selectByPrimaryKey(id);
        return UserInfoEntity.builder()
                .id(user.getId())
                .phone(user.getPhone())
                .openid(user.getOpenid())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .birthday(user.getBirthday())
                .backgroundImg(user.getBackgroundImg())
                .introduction(user.getIntroduction())
                .sex(user.getSex())
                .build();
    }
}
