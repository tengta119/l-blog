package top.lbwxxc.api.dto.admin.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PublishArticleRequestDTO {

    private String title;

    private String content;

    private String cover;

    private String summary;

    private Long categoryId;

    private List<String> tags;
}
