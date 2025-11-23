package top.lbwxxc.api.dto.front.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.YearMonth;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArchiveArticlePageListResponseDTO {

    /**
     * 归档的月份
     */
    private YearMonth month;

    private List<FindArchiveArticleResponseDTO> articles;
}
