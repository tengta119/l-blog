package top.lbwxxc.domain.user.service;


import top.lbwxxc.domain.user.model.entity.UserInfoEntity;

public interface IUserInfoService {

    void pushRolePermission2Redis();

    UserInfoEntity getUserInfo();

    void logout();
}
