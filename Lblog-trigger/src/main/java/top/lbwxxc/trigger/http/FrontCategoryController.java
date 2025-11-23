package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IFrontCategoryService;
import top.lbwxxc.api.dto.front.category.FindCategoryArticlePageListRequestDTO;
import top.lbwxxc.api.dto.front.category.FindCategoryArticlePageListResponseDTO;
import top.lbwxxc.api.dto.front.category.FindCategoryListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/category/")
public class FrontCategoryController implements IFrontCategoryService {


    @Resource
    private ICategoryService categoryService;
    @Resource
    private IArticleService articleService;

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

    @PostMapping("/article/list")
    @Override
    public PageResponse<FindCategoryArticlePageListResponseDTO> findCategoryArticlePageList(@RequestBody FindCategoryArticlePageListRequestDTO findCategoryArticlePageListRequestDTO) {
        Long categoryId = findCategoryArticlePageListRequestDTO.getId();
        int current = findCategoryArticlePageListRequestDTO.getCurrent();
        int size = findCategoryArticlePageListRequestDTO.getSize();

        int articleSizeByCategoryId = articleService.findArticleSizeByCategoryId(categoryId);

        PageResponse<FindCategoryArticlePageListResponseDTO> response = new PageResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setInfo(ResponseCode.SUCCESS.getInfo());
        response.setCurrent(current);
        response.setSize(size);
        if (articleSizeByCategoryId == 0) {
            response.setPages(0);
            response.setTotal(0);
            return response;
        }

        List<ArticleEntity> articlePageListByCategoryId = articleService.findArticlePageListByCategoryId(categoryId, current, size);
        List<FindCategoryArticlePageListResponseDTO> findCategoryArticlePageListResponseDTOS = articlePageListByCategoryId.stream()
                .map(articleEntity -> FindCategoryArticlePageListResponseDTO.builder()
                .id(articleEntity.getId())
                .title(articleEntity.getTitle())
                .cover(articleEntity.getCover())
                .createDate(LocalDate.from(articleEntity.getCreateTime()))
                .build()
        ).toList();
        response.setTotal(articleSizeByCategoryId);
        response.setPages(articleSizeByCategoryId / size);
        response.setData(findCategoryArticlePageListResponseDTOS);
        return response;
    }
}
