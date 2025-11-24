package top.lbwxxc.api;


import top.lbwxxc.api.dto.front.article.*;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

public interface IFrontArticleService {

    PageResponse<FindIndexArticlePageListResponseDTO> findArticlePageList(FindIndexArticlePageListRequestDTO findIndexArticlePageListRequestDTO);

    PageResponse<FindArchiveArticlePageListResponseDTO>  findArchivePageList(FindArchiveArticlePageListRequestDTO findArchiveArticlePageListRequestDTO);

    Response<FindArticleDetailResponseDTO> findArticleDetail(FindArticleDetailRequestDTO findArticleDetailRequestDTO);
}
