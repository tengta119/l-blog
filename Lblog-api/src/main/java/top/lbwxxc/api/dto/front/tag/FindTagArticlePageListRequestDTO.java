package top.lbwxxc.api.dto.front.tag;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.lbwxxc.api.dto.BasePageQuery;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindTagArticlePageListRequestDTO extends BasePageQuery {

    private long tagId;
}
