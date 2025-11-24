package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IFrontArticleService;
import top.lbwxxc.api.dto.front.article.*;
import top.lbwxxc.api.dto.front.category.FindCategoryListResponseDTO;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.marketdown.MarkdownHelper;
import top.lbwxxc.domain.blog.model.entity.ArticleDetailEntity;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.CategoryEntity;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.domain.blog.service.ITagService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public PageResponse<FindIndexArticlePageListResponseDTO> findArticlePageList(@RequestBody FindIndexArticlePageListRequestDTO request) {
        int size = request.getSize();
        int current = request.getCurrent();

        List<ArticleEntity> articleEntities = articleService.findArticlePageList(current, size);


        PageResponse<FindIndexArticlePageListResponseDTO> response = new PageResponse<>();
        if (articleEntities.isEmpty()) {
            response.setCode(ResponseCode.UN_ERROR.getCode());
            response.setInfo("文章不存在");
        }

        int articleSize = articleService.findArticleSize();
        response.setTotal(articleSize);
        response.setCurrent(current);
        response.setSize(size);
        response.setPages(articleSize / size);
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

    @PostMapping("/archive/list")
    @Override
    public PageResponse<FindArchiveArticlePageListResponseDTO> findArchivePageList(@RequestBody FindArchiveArticlePageListRequestDTO findArchiveArticlePageListRequestDTO) {

        int current = findArchiveArticlePageListRequestDTO.getCurrent();
        int size = findArchiveArticlePageListRequestDTO.getSize();

        List<ArticleEntity> articlePageList = articleService.findArticlePageList(current, size);
        Map<YearMonth, List<ArticleEntity>> yearMonthToArticle = articlePageList.stream()
                .collect(Collectors.groupingBy(articleEntity -> YearMonth.from(articleEntity.getCreateTime())));

        List<FindArchiveArticlePageListResponseDTO> archiveArticlePageListResponseDTOS = new ArrayList<>();
        for (Map.Entry<YearMonth, List<ArticleEntity>> entry : yearMonthToArticle.entrySet()) {
            FindArchiveArticlePageListResponseDTO findArchiveArticlePageListResponseDTO = FindArchiveArticlePageListResponseDTO.builder()
                    .month(entry.getKey()).build();
            List<FindArchiveArticleResponseDTO> findArchiveArticleResponseDTOS = entry.getValue().stream().map(articleEntity -> FindArchiveArticleResponseDTO.builder()
                    .id(articleEntity.getId())
                    .cover(articleEntity.getCover())
                    .title(articleEntity.getTitle())
                    .createDate(LocalDate.from(articleEntity.getCreateTime()))
                    .createMonth(YearMonth.from(articleEntity.getCreateTime()))
                    .build()

            ).toList();
            findArchiveArticlePageListResponseDTO.setArticles(findArchiveArticleResponseDTOS);

            archiveArticlePageListResponseDTOS.add(findArchiveArticlePageListResponseDTO);
        }

        PageResponse<FindArchiveArticlePageListResponseDTO> response = new PageResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setInfo(ResponseCode.SUCCESS.getInfo());
        response.setData(archiveArticlePageListResponseDTOS);

        int articleSize = articleService.findArticleSize();
        response.setTotal(articleSize);
        response.setCurrent(current);
        response.setSize(size);
        response.setPages(articleSize / size);

        return response;
    }


    @PostMapping("/detail")
    @Override
    public Response<FindArticleDetailResponseDTO> findArticleDetail(@RequestBody FindArticleDetailRequestDTO findArticleDetailRequestDTO) {
        Long articleId = findArticleDetailRequestDTO.getArticleId();
        try {
            ArticleDetailEntity articleDetail = articleService.findArticleDetailByArticleId(articleId);
            CategoryEntity categoryEntity = categoryService.findCategoryByCategoryId(articleDetail.getCategoryId());

            FindArticleDetailResponseDTO findArticleDetailResponseDTO = FindArticleDetailResponseDTO.builder()
                    .title(articleDetail.getTitle())
                    .content(MarkdownHelper.convertMarkdown2Html(articleDetail.getContent()))
                    .createTime(articleDetail.getCreateTime())
                    .categoryId(categoryEntity.getId())
                    .categoryName(categoryEntity.getName())
                    .readNum(Long.valueOf(articleDetail.getReadNum()))
                    .build();

            List<Long> tagIds = articleDetail.getTagIds();
            List<FindTagListResponseDTO> findTagListResponseDTOS = tagService.findTagsByTagIds(tagIds).stream()
                    .map(tagEntity -> FindTagListResponseDTO
                    .builder()
                    .id(tagEntity.getId())
                    .name(tagEntity.getName())
                    .build()
            ).toList();

            findArticleDetailResponseDTO.setTags(findTagListResponseDTOS);

            ArticleEntity preArticle = articleService.findPreArticleByArticleId(articleId);
            if (preArticle != null) {
                findArticleDetailResponseDTO.setPreArticle(FindPreNextArticleResponseDTO
                        .builder()
                        .articleId(preArticle.getId())
                        .articleTitle(preArticle.getTitle())
                        .build()
                );
            }

            ArticleEntity nextArticle = articleService.findNextArticleByArticleId(articleId);
            if (nextArticle != null) {
                findArticleDetailResponseDTO.setNextArticle(FindPreNextArticleResponseDTO
                        .builder()
                        .articleId(nextArticle.getId())
                        .articleTitle(nextArticle.getTitle())
                        .build()
                );
            }

            return Response.<FindArticleDetailResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(findArticleDetailResponseDTO)
                    .build();

        } catch (ClassCastException e) {

            log.error("获取文章详细信息失败 {}", e.getMessage());
            return Response.<FindArticleDetailResponseDTO>builder()
                    .code(ResponseCode.UN_ERROR.getCode())
                    .info(e.getMessage())
                    .build();
        }

    }
}
