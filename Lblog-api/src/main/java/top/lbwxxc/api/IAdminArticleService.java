package top.lbwxxc.api;


import top.lbwxxc.api.dto.article.DeleteArticleRequestDTO;
import top.lbwxxc.api.dto.article.PublishArticleRequestDTO;
import top.lbwxxc.api.response.Response;

public interface IAdminArticleService {

    Response<String> publishArticle(PublishArticleRequestDTO publishArticleRequestDTO);

    Response<String> deleteArticle(DeleteArticleRequestDTO deleteArticleRequestDTO);
}
