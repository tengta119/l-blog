package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.UserService;
import top.lbwxxc.api.dto.user.UpdateUserInfoRequestDTO;
import top.lbwxxc.api.dto.user.UserInfoResponseDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.user.model.entity.UserInfoEntity;
import top.lbwxxc.domain.user.service.IUserInfoService;
import top.lbwxxc.types.enums.ResponseCode;

@Slf4j
@RestController
@RequestMapping("/user/")
public class UserController implements UserService {

    @Resource
    private IUserInfoService userInfoService;


    @PostMapping("info")
    @Override
    public Response<UserInfoResponseDTO> getUserInfo() {

        UserInfoEntity userInfo = userInfoService.getUserInfo();
        try {
            if(userInfo == null){
                throw new RuntimeException("用户不存在");
            }
            UserInfoResponseDTO userInfoResponseDTO = UserInfoResponseDTO.builder()
                    .phone(userInfo.getPhone())
                    .openid(userInfo.getOpenid())
                    .email(userInfo.getEmail())
                    .author(userInfo.getAuthor())
                    .avatar(userInfo.getAvatar())
                    .birthday(userInfo.getBirthday())
                    .backgroundImg(userInfo.getBackgroundImg())
                    .sex(userInfo.getSex())
                    .introduction(userInfo.getIntroduction())
                    .build();
            return Response.<UserInfoResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(userInfoResponseDTO)
                    .build();
        } catch (Exception e) {
            return Response.<UserInfoResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }

    }

    @PostMapping("update")
    @Override
    public Response<String> updateUserInfo(@RequestBody UpdateUserInfoRequestDTO updateUserInfoRequestDTO) {

        UserInfoEntity userInfoEntity = new UserInfoEntity();
        userInfoEntity.setPhone(updateUserInfoRequestDTO.getPhone());
        userInfoEntity.setEmail(updateUserInfoRequestDTO.getEmail());
        userInfoEntity.setAuthor(updateUserInfoRequestDTO.getAuthor());
        userInfoEntity.setAvatar(updateUserInfoRequestDTO.getAvatar());
        userInfoEntity.setBirthday(updateUserInfoRequestDTO.getBirthday());
        userInfoEntity.setBackgroundImg(updateUserInfoRequestDTO.getBackgroundImg());
        userInfoEntity.setSex(updateUserInfoRequestDTO.getSex());
        userInfoEntity.setIntroduction(updateUserInfoRequestDTO.getIntroduction());

        int i = userInfoService.updateUserInfo(userInfoEntity);


        return getStringResponse(i, "更新用户信息失败");
    }

    private Response<String> getStringResponse(int i, String info) {
        Response<String> response = new Response<>();
        if (i > 0) {
            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());
            return response;
        }

        response.setCode(ResponseCode.UN_ERROR.getCode());
        response.setInfo(info);

        return response;
    }
}
