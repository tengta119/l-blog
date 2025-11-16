package top.lbwxxc.domain.login.adapter.repository;


import top.lbwxxc.domain.login.model.entity.LoginUserDetailEntity;
import top.lbwxxc.domain.login.model.entity.UserRoleEntity;
import top.lbwxxc.types.enums.CreateUserType;
import top.lbwxxc.types.enums.SelectUserType;

public interface IUserAccountRepository {


    LoginUserDetailEntity getUser(String str, SelectUserType selectUserType);

    LoginUserDetailEntity createUser(String str, String password, CreateUserType createUserType);

    int updateUserPasswordById(String str, SelectUserType selectUserType, String password);

    UserRoleEntity getUserRoleById(long userId);
}
