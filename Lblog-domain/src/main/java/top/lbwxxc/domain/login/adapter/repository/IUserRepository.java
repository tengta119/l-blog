package top.lbwxxc.domain.login.adapter.repository;


import top.lbwxxc.domain.login.model.entity.UserRegisterEntity;
import top.lbwxxc.domain.login.model.entity.UserDetailEntity;
import top.lbwxxc.types.enums.SelectUserType;

public interface IUserRepository {


    UserDetailEntity getUser(String str, SelectUserType selectUserType);

    UserDetailEntity createUser(UserRegisterEntity userRegisterEntity);

    int updateUserPasswordById(String str, SelectUserType selectUserType, String password);
}
