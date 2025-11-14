package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminCategoryService;
import top.lbwxxc.api.dto.AddCategoryRequestDTO;
import top.lbwxxc.api.dto.FindCategoryPageListRequestDTO;
import top.lbwxxc.api.dto.FindCategoryPageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category/")
public class AdminCategoryController implements IAdminCategoryService {

    @Resource
    private ICategoryService categoryService;


    @PostMapping("add")
    @Override
    public Response<String> addCategory(@RequestBody AddCategoryRequestDTO addCategoryRequestDTO) {

        int addCategory = categoryService.addCategory(addCategoryRequestDTO.getName());
        if (addCategory > 0) {
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        }

        return Response.<String>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("添加文章分类失败")
                .build();
    }

    @PostMapping("select")
    @Override
    public PageResponse<FindCategoryPageListResponseDTO> findCategoryList(@RequestBody FindCategoryPageListRequestDTO findCategoryPageListRequestDTO) {

        int current = findCategoryPageListRequestDTO.getCurrent();
        int size = findCategoryPageListRequestDTO.getSize();
        LocalDate startDate = findCategoryPageListRequestDTO.getStartDate();
        LocalDate endDate = findCategoryPageListRequestDTO.getEndDate();
        List<CategoryEntity> categoryList = categoryService.findCategoryList(current, size, startDate, endDate);

        PageResponse<FindCategoryPageListResponseDTO> pageResponse = new PageResponse<>();
        if (categoryList.isEmpty()) {
            pageResponse.setCode(ResponseCode.UN_ERROR.getCode());
            pageResponse.setInfo("文章分类不存在");
            return pageResponse;
        }

        ArrayList<FindCategoryPageListResponseDTO> categoryPageListResponseDTOS = new ArrayList<>();
        for (CategoryEntity categoryEntity : categoryList) {
            categoryPageListResponseDTOS.add(FindCategoryPageListResponseDTO.builder()
                    .id(categoryEntity.getId())
                    .name(categoryEntity.getName())
                    .createTime(categoryEntity.getCreateTime())
                    .build());
        }
        pageResponse.setData(categoryPageListResponseDTOS);
        return pageResponse;
    }
}
