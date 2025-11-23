package top.lbwxxc.api;


import top.lbwxxc.api.dto.front.tag.FindTagArticlePageListRequestDTO;
import top.lbwxxc.api.dto.front.tag.FindTagArticlePageListResponseDTO;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

import java.util.List;

public interface IFrontTagService {

    Response<List<FindTagListResponseDTO>> findTagList();

    PageResponse<FindTagArticlePageListResponseDTO> findTagPageList(FindTagArticlePageListRequestDTO findTagArticlePageListRequestDTO);
}
