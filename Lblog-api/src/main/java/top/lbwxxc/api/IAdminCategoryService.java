package top.lbwxxc.api;


import top.lbwxxc.api.dto.SelectResponse;
import top.lbwxxc.api.dto.admin.category.AddCategoryRequestDTO;
import top.lbwxxc.api.dto.admin.category.DeleteCategoryRequestDTO;
import top.lbwxxc.api.dto.admin.category.FindCategoryPageListRequestDTO;
import top.lbwxxc.api.dto.admin.category.FindCategoryPageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

import java.util.List;

public interface IAdminCategoryService {


    Response<String> addCategory(AddCategoryRequestDTO addCategoryRequestDTO);

    PageResponse<FindCategoryPageListResponseDTO> findCategoryList(FindCategoryPageListRequestDTO findCategoryPageListRequestDTO);

    Response<String> deleteCategory(DeleteCategoryRequestDTO deleteCategoryRequestDTO);

    Response<List<SelectResponse>> findCategorySelectList();
}
