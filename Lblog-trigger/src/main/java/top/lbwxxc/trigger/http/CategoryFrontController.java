package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.ICategoryFrontService;
import top.lbwxxc.api.dto.front.category.FindCategoryListResponseDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.types.enums.ResponseCode;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category/")
public class CategoryFrontController implements ICategoryFrontService {


    @Resource
    private ICategoryService categoryService;

    @PostMapping("list")
    @Override
    public Response<List<FindCategoryListResponseDTO>> findCategoryList() {


        List<CategoryEntity> allCategory = categoryService.findAllCategory();

        Response<List<FindCategoryListResponseDTO>> response = new Response<>();

        if (allCategory.isEmpty()) {
            log.warn("文章分类不存在");
            response.setCode(ResponseCode.UN_ERROR.getCode());
            response.setInfo("分类不存在");
        } else {
            List<FindCategoryListResponseDTO> findCategoryListResponseDTOS = allCategory.stream()
                    .map(categoryEntity -> FindCategoryListResponseDTO.builder()
                            .id(categoryEntity.getId()).name(categoryEntity.getName()).build())
                    .toList();

            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());
            response.setData(findCategoryListResponseDTOS);
        }

        return response;
    }
}
