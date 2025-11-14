package top.lbwxxc.api;


import top.lbwxxc.api.dto.user.UserInfoResponse;
import top.lbwxxc.api.response.Response;

public interface UserService {

    Response<UserInfoResponse> getUserInfo();
}
