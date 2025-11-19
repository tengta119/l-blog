package top.lbwxxc.domain.blog.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PublishArticleEntity {
    // 标题
    private String title;

    // 内容
    private String content;

    // 封面
    private String cover;

    private String summary;

    // 文章分类
    private Long categoryId;

    // 文章标签
    private List<String> tags;
}
