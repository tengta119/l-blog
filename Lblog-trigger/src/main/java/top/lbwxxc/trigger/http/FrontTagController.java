package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IFrontTagService;
import top.lbwxxc.api.dto.front.tag.FindTagArticlePageListRequestDTO;
import top.lbwxxc.api.dto.front.tag.FindTagArticlePageListResponseDTO;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.TagEntity;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.domain.blog.service.ITagService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/tag/")
public class FrontTagController implements IFrontTagService {

    @Resource
    private ITagService tagService;
    @Resource
    private IArticleService articleService;

    @PostMapping("list")
    @Override
    public Response<List<FindTagListResponseDTO>> findTagList() {

        List<TagEntity> allTag = tagService.findAllTag();

        Response<List<FindTagListResponseDTO>> response = new Response<>();
        if (allTag == null || allTag.isEmpty()) {
            log.warn("文章标签不存在");
            response.setCode(ResponseCode.UN_ERROR.getCode());
            response.setInfo("文章标签不存在");
        } else {
            List<FindTagListResponseDTO> findTagListResponseDTOS = allTag.stream().map(tagEntity -> FindTagListResponseDTO.builder()
                            .id(tagEntity.getId())
                            .name(tagEntity.getName())
                            .build())
                    .toList();
            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());
            response.setData(findTagListResponseDTOS);
        }

        return response;
    }

    @PostMapping("/article/list")
    @Override
    public PageResponse<FindTagArticlePageListResponseDTO> findTagPageList(@RequestBody FindTagArticlePageListRequestDTO findTagArticlePageListRequestDTO) {

        long tagId = findTagArticlePageListRequestDTO.getTagId();
        int current = findTagArticlePageListRequestDTO.getCurrent();
        int size = findTagArticlePageListRequestDTO.getSize();

        int articleSizeByTagId = articleService.findArticleSizeByTagId(tagId);

        PageResponse<FindTagArticlePageListResponseDTO> response = new PageResponse<>();
        response.setCode(ResponseCode.SUCCESS.getCode());
        response.setInfo(ResponseCode.SUCCESS.getInfo());
        response.setCurrent(current);
        response.setSize(size);
        response.setTotal(articleSizeByTagId);
        response.setPages((int)Math.ceil((double) articleSizeByTagId / size));
        if (articleSizeByTagId == 0) {
            return response;
        }

        List<ArticleEntity> articlePageListByTagId = articleService.findArticlePageListByTagId(tagId, current, size);

        List<FindTagArticlePageListResponseDTO> list = articlePageListByTagId
                .stream()
                .filter(Objects::nonNull)
                .map(articleEntity -> FindTagArticlePageListResponseDTO
                        .builder()
                        .id(articleEntity.getId())
                        .title(articleEntity.getTitle())
                        .cover(articleEntity.getCover())
                        .createDate(LocalDate.from(articleEntity.getCreateTime()))
                        .build()
                ).toList();

        response.setData(list);
        return response;
    }
}
