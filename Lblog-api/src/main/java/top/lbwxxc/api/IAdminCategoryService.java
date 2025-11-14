package top.lbwxxc.api;


import top.lbwxxc.api.dto.AddCategoryRequestDTO;
import top.lbwxxc.api.dto.DeleteCategoryRequestDTO;
import top.lbwxxc.api.dto.FindCategoryPageListRequestDTO;
import top.lbwxxc.api.dto.FindCategoryPageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;

public interface IAdminCategoryService {


    Response<String> addCategory(AddCategoryRequestDTO addCategoryRequestDTO);

    PageResponse<FindCategoryPageListResponseDTO> findCategoryList(FindCategoryPageListRequestDTO findCategoryPageListRequestDTO);

    Response<String> deleteCategory(DeleteCategoryRequestDTO deleteCategoryRequestDTO);
}
