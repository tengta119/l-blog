package top.lbwxxc.api;


import top.lbwxxc.api.dto.AddCategoryRequestDTO;
import top.lbwxxc.api.response.Response;

public interface IAdminCategoryService {


    Response<String> addCategory(AddCategoryRequestDTO addCategoryRequestDTO);
}
