package top.lbwxxc.api;


import top.lbwxxc.api.dto.article.*;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

public interface IAdminArticleService {

    Response<String> publishArticle(PublishArticleRequestDTO publishArticleRequestDTO);

    Response<String> deleteArticle(DeleteArticleRequestDTO deleteArticleRequestDTO);

    PageResponse<FindArticlePageListResponseDTO> findArticlePageList(FindArticlePageListRequestDTO findArticlePageListRequestDTO);

    Response<FindArticleDetailResponseDTO>  findArticleDetail(FindArticleDetailRequestDTO findArticleDetailRequestDTO);
}
