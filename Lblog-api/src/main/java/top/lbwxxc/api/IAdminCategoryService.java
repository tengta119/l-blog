package top.lbwxxc.api;


import top.lbwxxc.api.dto.category.AddCategoryRequestDTO;
import top.lbwxxc.api.dto.category.DeleteCategoryRequestDTO;
import top.lbwxxc.api.dto.category.FindCategoryPageListRequestDTO;
import top.lbwxxc.api.dto.category.FindCategoryPageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

public interface IAdminCategoryService {


    Response<String> addCategory(AddCategoryRequestDTO addCategoryRequestDTO);

    PageResponse<FindCategoryPageListResponseDTO> findCategoryList(FindCategoryPageListRequestDTO findCategoryPageListRequestDTO);

    Response<String> deleteCategory(DeleteCategoryRequestDTO deleteCategoryRequestDTO);
}
