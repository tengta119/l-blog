package top.lbwxxc.api;


import top.lbwxxc.api.dto.SelectResponse;
import top.lbwxxc.api.dto.tag.AddTagRequestDTO;
import top.lbwxxc.api.dto.tag.DeleteTagRequestDTO;
import top.lbwxxc.api.dto.tag.FindTagPageListRequestDTO;
import top.lbwxxc.api.dto.tag.FindTagPageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

import java.util.List;

public interface IAdminTagService {

    Response<String> addTag(AddTagRequestDTO addTagRequestDTO);

    PageResponse<FindTagPageListResponseDTO> findTagList(FindTagPageListRequestDTO findTagPageListRequestDTO);

    Response<String> deleteTag(DeleteTagRequestDTO deleteTagRequestDTO);

    Response<List<SelectResponse>> findTagSelectList();
}
