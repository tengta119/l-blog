package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminBlogSettingsService;
import top.lbwxxc.api.dto.admin.settings.*;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.BlogSettingsEntity;
import top.lbwxxc.domain.blog.model.entity.ExternalUrlEntity;
import top.lbwxxc.domain.blog.service.IBlogSettingsService;
import top.lbwxxc.types.enums.ResponseCode;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/settings/")
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

    @PostMapping("find")
    @Override
    public Response<FindBlogSettingsResponseDTO> findBlogSettings() {
        BlogSettingsEntity blogSettings = blogSettingsService.getBlogSettings();

        return Response.<FindBlogSettingsResponseDTO>builder()
                .code(ResponseCode.SUCCESS.getCode())
                .info(ResponseCode.SUCCESS.getInfo())
                .data(FindBlogSettingsResponseDTO.builder()
                        .name(blogSettings.getName())
                        .logo(blogSettings.getLogo())
                        .build()
                )
                .build();
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

    @PostMapping("external-url/update")
    @Override
    public Response<String> updateExternalUrl(@RequestBody UpdateExternalUrlRequestDTO updateExternalUrlRequestDTO) {
        Long id = updateExternalUrlRequestDTO.getId();
        String url = updateExternalUrlRequestDTO.getUrl();
        String name = updateExternalUrlRequestDTO.getName();
        String logo = updateExternalUrlRequestDTO.getLogo();

        int i = blogSettingsService.updateExternalUrl(id, name, logo, url);

        return getStringResponse(i, "修改第三方平台信息失败");
    }

    @PostMapping("external-url/find")
    @Override
    public PageResponse<FindExternalUrlListResponseDTO> findExternalUrlList(@RequestBody FindExternalUrlListRequestDTO findExternalUrlListRequestDTO) {

        PageResponse<FindExternalUrlListResponseDTO> pageResponse = new PageResponse<>();

        int externalUrlSize = blogSettingsService.findExternalUrlSize();
        if (externalUrlSize == 0) {
            pageResponse.setCode(ResponseCode.UN_ERROR.getCode());
            pageResponse.setInfo("第三方平台为空");
            return pageResponse;
        }

        int current = findExternalUrlListRequestDTO.getCurrent();
        int size = findExternalUrlListRequestDTO.getSize();
        List<ExternalUrlEntity> externalUrlList = blogSettingsService.findExternalUrlList(current, size);

        List<FindExternalUrlListResponseDTO> responseList = new ArrayList<>();
        for (ExternalUrlEntity externalUrlEntity : externalUrlList) {
            FindExternalUrlListResponseDTO responseDTO = new FindExternalUrlListResponseDTO();
            responseDTO.setId(externalUrlEntity.getId());
            responseDTO.setName(externalUrlEntity.getName());
            responseDTO.setUrl(externalUrlEntity.getUrl());
            responseDTO.setLogo(externalUrlEntity.getLogo());
            responseList.add(responseDTO);
        }
        pageResponse.setData(responseList);
        pageResponse.setTotal(externalUrlSize);
        pageResponse.setCurrent(current);
        pageResponse.setSize(size);
        pageResponse.setCode(ResponseCode.SUCCESS.getCode());
        pageResponse.setInfo(ResponseCode.SUCCESS.getInfo());
        return pageResponse;
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
