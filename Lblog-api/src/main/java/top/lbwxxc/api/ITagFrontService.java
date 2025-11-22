package top.lbwxxc.api;


import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;
import top.lbwxxc.api.response.Response;

import java.util.List;

public interface ITagFrontService {

    Response<List<FindTagListResponseDTO>> findTagList();
}
