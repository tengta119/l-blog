package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminBlogSettingsService;
import top.lbwxxc.api.dto.settings.AddExternalUrlRequestDTO;
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

        return getStringResponse(i, "博客设置失败");
    }

    @PostMapping("external-url/add")
    @Override
    public Response<String> addExternalUrl(@RequestBody AddExternalUrlRequestDTO addExternalUrlRequestDTO) {
        String url = addExternalUrlRequestDTO.getUrl();
        String name = addExternalUrlRequestDTO.getName();
        String logo = addExternalUrlRequestDTO.getLogo();
        int i = blogSettingsService.addExternalUrl(name, logo, url);


        return getStringResponse(i, "第三方平台添加失败");
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
