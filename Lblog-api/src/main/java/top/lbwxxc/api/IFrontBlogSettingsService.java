package top.lbwxxc.api;


import top.lbwxxc.api.dto.front.blogsettings.FindBlogSettingsDetailResponse;
import top.lbwxxc.api.response.Response;

public interface IFrontBlogSettingsService {

    Response<FindBlogSettingsDetailResponse> findBlogSettingsDetail();
}
