package top.lbwxxc.api;


import top.lbwxxc.api.dto.settings.AddExternalUrlRequestDTO;
import top.lbwxxc.api.dto.settings.UpdateBlogSettingsRequestVO;
import top.lbwxxc.api.dto.settings.UpdateExternalUrlRequestDTO;
import top.lbwxxc.api.response.Response;

public interface IAdminBlogSettingsService {

    Response<String> updateBlogSettings(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO);

    Response<String> addExternalUrl(AddExternalUrlRequestDTO addExternalUrlRequestDTO);

    Response<String> updateExternalUrl(UpdateExternalUrlRequestDTO updateExternalUrlRequestDTO);
}
