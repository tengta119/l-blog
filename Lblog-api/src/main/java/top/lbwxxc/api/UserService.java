package top.lbwxxc.api;


import top.lbwxxc.api.dto.user.UpdateUserInfoRequestDTO;
import top.lbwxxc.api.dto.user.UserInfoResponseDTO;
import top.lbwxxc.api.response.Response;

public interface UserService {

    Response<UserInfoResponseDTO> getUserInfo();

    Response<String> updateUserInfo(UpdateUserInfoRequestDTO updateUserInfoRequestDTO);
}
