package top.lbwxxc.api;


import top.lbwxxc.api.dto.settings.UpdateBlogSettingsRequestVO;
import top.lbwxxc.api.response.Response;

public interface IAdminBlogSettingsService {

    Response<String> updateBlogSettings(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO);
}
