package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.UserService;
import top.lbwxxc.api.dto.UserInfoResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.user.model.entity.UserInfoEntity;
import top.lbwxxc.domain.user.service.IUserInfoService;
import top.lbwxxc.types.enums.ResponseCode;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/user/")
public class UserController implements UserService {

    @Resource
    private IUserInfoService userInfoService;


    @PostMapping("info")
    @Override
    public Response<UserInfoResponse> getUserInfo() {

        UserInfoEntity userInfo = userInfoService.getUserInfo();
        try {
            if(userInfo == null){
                throw new RuntimeException("用户不存在");
            }
            UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                    .phone(userInfo.getPhone())
                    .openid(userInfo.getOpenid())
                    .email(userInfo.getEmail())
                    .nickname(userInfo.getNickname())
                    .avatar(userInfo.getAvatar())
                    .birthday(userInfo.getBirthday())
                    .backgroundImg(userInfo.getBackgroundImg())
                    .sex(userInfo.getSex())
                    .introduction(userInfo.getIntroduction())
                    .build();
            return Response.<UserInfoResponse>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(userInfoResponse)
                    .build();
        } catch (Exception e) {
            return Response.<UserInfoResponse>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }

    }
}
