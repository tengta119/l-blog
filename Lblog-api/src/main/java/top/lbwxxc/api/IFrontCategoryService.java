package top.lbwxxc.api;


import top.lbwxxc.api.dto.front.category.FindCategoryListResponseDTO;
import top.lbwxxc.api.response.Response;

import java.util.List;

public interface IFrontCategoryService {

    Response<List<FindCategoryListResponseDTO>> findCategoryList();
}
