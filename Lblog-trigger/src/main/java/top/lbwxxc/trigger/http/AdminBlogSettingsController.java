package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminBlogSettingsService;
import top.lbwxxc.api.dto.settings.UpdateBlogSettingsRequestVO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.service.IBlogSettingsService;
import top.lbwxxc.types.enums.ResponseCode;

@Slf4j
@RestController
@RequestMapping("/settings/")
public class AdminBlogSettingsController implements IAdminBlogSettingsService {

    @Resource
    private IBlogSettingsService blogSettingsService;

    @PostMapping("settings")
    @Override
    public Response<String> updateBlogSettings(@RequestBody UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO) {
        String logo = updateBlogSettingsRequestVO.getLogo();
        String name = updateBlogSettingsRequestVO.getName();

        int i = blogSettingsService.updateBlogSettings(logo, name);

        Response<String> response = new Response<>();
        if (i > 0) {
            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());
            return response;
        }

        response.setCode(ResponseCode.UN_ERROR.getCode());
        response.setInfo("博客设置失败");

        return response;
    }
}
