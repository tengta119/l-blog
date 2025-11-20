package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminCategoryService;
import top.lbwxxc.api.dto.SelectResponse;
import top.lbwxxc.api.dto.category.AddCategoryRequestDTO;
import top.lbwxxc.api.dto.category.DeleteCategoryRequestDTO;
import top.lbwxxc.api.dto.category.FindCategoryPageListRequestDTO;
import top.lbwxxc.api.dto.category.FindCategoryPageListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @PostMapping("list")
    @Override
    public PageResponse<FindCategoryPageListResponseDTO> findCategoryList(@RequestBody FindCategoryPageListRequestDTO findCategoryPageListRequestDTO) {
        int current = findCategoryPageListRequestDTO.getCurrent();
        int size = findCategoryPageListRequestDTO.getSize();
        LocalDateTime startDate = findCategoryPageListRequestDTO.getStartDate();
        LocalDateTime endDate = findCategoryPageListRequestDTO.getEndDate();
        String name = findCategoryPageListRequestDTO.getName();
        log.info("请求参数 current: {}, size: {}, startDate: {}, endDate: {}, name: {}", current, size, startDate, endDate, name);
        List<CategoryEntity> categoryList = categoryService.findCategoryList(current, size, name, startDate, endDate);
        int categorySize = categoryService.findCategorySize();
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
        pageResponse.setCode(ResponseCode.SUCCESS.getCode());
        pageResponse.setInfo(ResponseCode.SUCCESS.getInfo());
        pageResponse.setPages(current);
        pageResponse.setSize(size);
        pageResponse.setTotal(categorySize);
        return pageResponse;
    }

    @PostMapping("delete")
    @Override
    public Response<String> deleteCategory(@RequestBody DeleteCategoryRequestDTO deleteCategoryRequestDTO) {

        int i = categoryService.deleteCategory(deleteCategoryRequestDTO.getId());
        if (i > 0) {
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        }

        return Response.<String>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("删除文章分类失败")
                .build();
    }

    @PostMapping("select/list")
    @Override
    public Response<List<SelectResponse>> findCategorySelectList() {

        List<CategoryEntity> allCategory = categoryService.findAllCategory();
        List<SelectResponse> selectRspVOS = null;
        if (!allCategory.isEmpty()) {
            selectRspVOS = allCategory.stream().map(categoryEntity ->  SelectResponse.builder()
                    .label(categoryEntity.getName())
                    .value(categoryEntity.getId())
                    .build())
                    .toList();
        }

        if (selectRspVOS != null) {
            return Response.<List<SelectResponse>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(selectRspVOS)
                    .build();
        }

        return Response.<List<SelectResponse>>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("获取文章分类失败")
                .build();
    }
}
