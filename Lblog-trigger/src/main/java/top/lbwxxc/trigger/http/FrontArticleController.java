package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IFrontArticleService;
import top.lbwxxc.api.dto.front.article.FindIndexArticlePageListRequestDTO;
import top.lbwxxc.api.dto.front.article.FindIndexArticlePageListResponseDTO;
import top.lbwxxc.api.dto.front.category.FindCategoryListResponseDTO;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.domain.blog.service.ITagService;
import top.lbwxxc.types.enums.ResponseCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/article/")
public class FrontArticleController implements IFrontArticleService {

    @Resource
    private IArticleService articleService;
    @Resource
    private ITagService tagService;
    @Resource
    private ICategoryService categoryService;

    @PostMapping("list")
    @Override
    public Response<List<FindIndexArticlePageListResponseDTO>> findArticlePageList(@RequestBody FindIndexArticlePageListRequestDTO request) {
        int size = request.getSize();
        int current = request.getCurrent();

        List<ArticleEntity> articleEntities = articleService.findArticlePageList(current, size);

        Response<List<FindIndexArticlePageListResponseDTO>> response = new Response<>();
        if (articleEntities.isEmpty()) {
            response.setCode(ResponseCode.UN_ERROR.getCode());
            response.setInfo("文章不存在");
        }

        List<FindIndexArticlePageListResponseDTO>  responseList = new ArrayList<>();

        for (ArticleEntity articleEntity : articleEntities) {
            Long articleId = articleEntity.getId();
            FindIndexArticlePageListResponseDTO dto = new FindIndexArticlePageListResponseDTO();

            List<FindTagListResponseDTO> list = tagService.findTagsByArticleId(articleId).stream()
                    .filter(Objects::nonNull)
                    .map(tagEntity -> FindTagListResponseDTO.builder()
                            .id(tagEntity.getId())
                            .name(tagEntity.getName())
                            .build())
                    .toList();
            dto.setTags(list);

            CategoryEntity categoryByArticleId = categoryService.findCategoryByArticleId(articleId);
            FindCategoryListResponseDTO findCategoryListResponseDTO = FindCategoryListResponseDTO.builder()
                    .id(categoryByArticleId.getId()).name(categoryByArticleId.getName())
                    .build();
            dto.setCategory(findCategoryListResponseDTO);

            dto.setId(articleId);
            dto.setCover(articleEntity.getCover());
            dto.setTitle(articleEntity.getTitle());
            dto.setSummary(articleEntity.getSummary());
            dto.setCreateTime(articleEntity.getCreateTime());

            responseList.add(dto);
        }
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setInfo(ResponseCode.SUCCESS.getInfo());
        response.setData(responseList);
        return response;
    }
}
