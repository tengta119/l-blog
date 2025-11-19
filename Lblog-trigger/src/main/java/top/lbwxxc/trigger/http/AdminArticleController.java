package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminArticleService;
import top.lbwxxc.api.dto.article.PublishArticleRequestDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.PublishArticleEntity;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.types.enums.ResponseCode;

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
