package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminArticleService;
import top.lbwxxc.api.dto.article.DeleteArticleRequestDTO;
import top.lbwxxc.api.dto.article.FindArticlePageListRequestDTO;
import top.lbwxxc.api.dto.article.FindArticlePageListResponseDTO;
import top.lbwxxc.api.dto.article.PublishArticleRequestDTO;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.ArticleEntity;
import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/article/")
public class AdminArticleController implements IAdminArticleService {

    @Resource
    private IArticleService articleService;

    @PostMapping("publish")
    @Override
    public Response<String> publishArticle(@RequestBody PublishArticleRequestDTO publishArticleRequestDTO) {

        PublishArticleEntity publishArticleEntity = PublishArticleEntity.builder()
                .title(publishArticleRequestDTO.getTitle())
                .content(publishArticleRequestDTO.getContent())
                .cover(publishArticleRequestDTO.getCover())
                .tags(publishArticleRequestDTO.getTags())
                .summary(publishArticleRequestDTO.getSummary())
                .categoryId(publishArticleRequestDTO.getCategoryId())
                .build();

        int published = articleService.publishArticle(publishArticleEntity);

        return getStringResponse(published, "文章发布失败");
    }

    @PostMapping("delete")
    @Override
    public Response<String> deleteArticle(@RequestBody DeleteArticleRequestDTO deleteArticleRequestDTO) {

        int i = articleService.deleteArticle(deleteArticleRequestDTO.getId());

        return getStringResponse(i, "删除文章失败");
    }

    @PostMapping("list")
    @Override
    public PageResponse<FindArticlePageListResponseDTO> findArticlePageList(@RequestBody FindArticlePageListRequestDTO findArticlePageListRequestDTO) {
        String title = findArticlePageListRequestDTO.getTitle();
        LocalDate startDate = findArticlePageListRequestDTO.getStartDate();
        LocalDate endDate = findArticlePageListRequestDTO.getEndDate();
        int current = findArticlePageListRequestDTO.getCurrent();
        int size = findArticlePageListRequestDTO.getSize();

        int articleSize = articleService.findArticleSize();

        PageResponse<FindArticlePageListResponseDTO> pageResponse = new PageResponse<>();
        if (articleSize == 0) {
            pageResponse.setCode(ResponseCode.UN_ERROR.getCode());
            pageResponse.setInfo("文章不存在");
            return pageResponse;
        }

        List<ArticleEntity> allArticlePageList = articleService.findAllArticlePageList(current, size, title, startDate, endDate);



        if (allArticlePageList.isEmpty()) {
            pageResponse.setCode(ResponseCode.UN_ERROR.getCode());
            pageResponse.setInfo("文章不存在");
            return pageResponse;
        }

        List<FindArticlePageListResponseDTO> findArticlePageListResponseDTOS = new ArrayList<>();
        for (ArticleEntity articleEntity : allArticlePageList) {
            findArticlePageListResponseDTOS.add(
                    FindArticlePageListResponseDTO.builder()
                            .id(articleEntity.getId())
                            .title(articleEntity.getTitle())
                            .cover(articleEntity.getCover())
                            .createTime(articleEntity.getCreateTime())
                            .build()
            );
        }
        pageResponse.setData(findArticlePageListResponseDTOS);
        pageResponse.setCode(ResponseCode.SUCCESS.getCode());
        pageResponse.setInfo(ResponseCode.SUCCESS.getInfo());
        return pageResponse;
    }


    private Response<String> getStringResponse(int i, String info) {
        Response<String> response = new Response<>();
        if (i > 0) {
            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());
            return response;
        }

        response.setCode(ResponseCode.UN_ERROR.getCode());
        response.setInfo(info);

        return response;
    }
}
