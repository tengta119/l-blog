package top.lbwxxc.domain.user.adapter.repository;


import top.lbwxxc.domain.user.model.entity.UserRegisterEntity;
import top.lbwxxc.domain.user.model.entity.UserDetailEntity;

public interface IUserRepository {

    UserDetailEntity getUserByPhone(String phone);

    UserDetailEntity createUser(UserRegisterEntity userVO);


}
