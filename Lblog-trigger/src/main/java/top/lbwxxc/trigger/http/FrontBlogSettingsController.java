package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IFrontBlogSettingsService;
import top.lbwxxc.api.dto.front.blogsettings.FindBlogSettingsDetailResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.BlogSettingsEntity;
import top.lbwxxc.domain.blog.service.IBlogSettingsService;
import top.lbwxxc.domain.user.model.entity.UserInfoEntity;
import top.lbwxxc.domain.user.service.IUserInfoService;
import top.lbwxxc.types.enums.ResponseCode;

@Slf4j
@RestController
@RequestMapping("/blog/settings/")
public class FrontBlogSettingsController implements IFrontBlogSettingsService {

    @Resource
    private IBlogSettingsService blogSettingsService;
    @Resource
    private IUserInfoService  userInfoService;

    @PostMapping("detail")
    @Override
    public Response<FindBlogSettingsDetailResponse> findBlogSettingsDetail() {

        BlogSettingsEntity blogSettings = blogSettingsService.findBlogSettings();
        UserInfoEntity userInfo = userInfoService.getUserInfo();

        FindBlogSettingsDetailResponse findBlogSettingsDetailResponse = FindBlogSettingsDetailResponse.builder()
                .logo(blogSettings.getLogo())
                .name(blogSettings.getName())
                .author(userInfo.getAuthor())
                .introduction(userInfo.getIntroduction())
                .avatar(userInfo.getAvatar())
                .build();

        return Response.<FindBlogSettingsDetailResponse>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(findBlogSettingsDetailResponse)
                .build();
    }
}
