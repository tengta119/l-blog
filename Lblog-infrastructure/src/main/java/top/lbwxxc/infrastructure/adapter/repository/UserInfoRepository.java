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

import java.time.LocalDate;

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

    @Override
    public int updateUserInfo(UserInfoEntity userInfo) {

        UserDetail userDetailUpdate = new UserDetail();
        userDetailUpdate.setId(userInfo.getId());

        String author = userInfo.getAuthor();
        if (author != null && !author.isEmpty()) {
            userDetailUpdate.setAuthor(author);
        }
        String introduction = userInfo.getIntroduction();
        if (introduction != null && !introduction.isEmpty()) {
            userDetailUpdate.setIntroduction(introduction);
        }
        String avatar = userInfo.getAvatar();
        if (avatar != null && !avatar.isEmpty()) {
            userDetailUpdate.setAvatar(avatar);
        }
        LocalDate birthday = userInfo.getBirthday();
        if (birthday != null) {
            userDetailUpdate.setBirthday(birthday);
        }
        String backgroundImg = userInfo.getBackgroundImg();
        if (backgroundImg != null && !backgroundImg.isEmpty()) {
            userDetailUpdate.setBackgroundImg(backgroundImg);
        }
        int sex = userInfo.getSex();
        userDetailUpdate.setSex(sex);

        return userDetailDao.updateByPrimaryKeySelective(userDetailUpdate);
    }
}
