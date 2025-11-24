package top.lbwxxc.api.dto.front.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindArticleDetailResponseDTO {

    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章正文（HTML）
     */
    private String content;
    /**
     * 发布时间
     */
    private LocalDateTime createTime;
    /**
     * 分类 ID
     */
    private Long categoryId;
    /**
     * 分类名称
     */
    private String categoryName;
    /**
     * 阅读量
     */
    private Long readNum;

    /**
     * 标签集合
     */
    private List<FindTagListResponseDTO> tags;

    /**
     * 上一篇文章
     */
    private FindPreNextArticleResponseDTO preArticle;
    /**
     * 下一篇文章
     */
    private FindPreNextArticleResponseDTO nextArticle;
}
