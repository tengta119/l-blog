package top.lbwxxc.api.dto.front.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindArticleDetailRequestDTO {

    private Long articleId;
}
