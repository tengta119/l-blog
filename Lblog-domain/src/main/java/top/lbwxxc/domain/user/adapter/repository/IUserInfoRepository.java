package top.lbwxxc.domain.user.adapter.repository;


import top.lbwxxc.domain.user.model.entity.UserInfoEntity;

public interface IUserInfoRepository {

    UserInfoEntity getUserInfoById(Long id);
}
