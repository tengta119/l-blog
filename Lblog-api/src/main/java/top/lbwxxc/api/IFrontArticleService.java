package top.lbwxxc.api;


import top.lbwxxc.api.dto.front.article.FindArchiveArticlePageListRequestDTO;
import top.lbwxxc.api.dto.front.article.FindArchiveArticlePageListResponseDTO;
import top.lbwxxc.api.dto.front.article.FindIndexArticlePageListRequestDTO;
import top.lbwxxc.api.dto.front.article.FindIndexArticlePageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;

public interface IFrontArticleService {

    PageResponse<FindIndexArticlePageListResponseDTO> findArticlePageList(FindIndexArticlePageListRequestDTO findIndexArticlePageListRequestDTO);

    PageResponse<FindArchiveArticlePageListResponseDTO>  findArchivePageList(FindArchiveArticlePageListRequestDTO findArchiveArticlePageListRequestDTO);
}
