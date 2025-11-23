package top.lbwxxc.api.dto.front.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.lbwxxc.api.dto.BasePageQuery;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindCategoryArticlePageListRequestDTO extends BasePageQuery {

    private Long id;
}
