package top.lbwxxc.api;


import top.lbwxxc.api.dto.settings.*;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

public interface IAdminBlogSettingsService {

    Response<String> updateBlogSettings(UpdateBlogSettingsRequestVO updateBlogSettingsRequestVO);

    Response<FindBlogSettingsResponseDTO> findBlogSettings();

    Response<String> addExternalUrl(AddExternalUrlRequestDTO addExternalUrlRequestDTO);

    Response<String> updateExternalUrl(UpdateExternalUrlRequestDTO updateExternalUrlRequestDTO);

    PageResponse<FindExternalUrlListResponseDTO> findExternalUrlList(FindExternalUrlListRequestDTO findExternalUrlListRequestDTO);
}
