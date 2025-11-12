package top.lbwxxc.api;


import top.lbwxxc.api.dto.UserInfoResponse;
import top.lbwxxc.api.response.Response;

public interface UserService {

    Response<UserInfoResponse> getUserInfo();
}
