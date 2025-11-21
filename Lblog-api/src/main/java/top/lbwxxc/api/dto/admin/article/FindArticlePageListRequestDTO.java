package top.lbwxxc.api.dto.admin.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.lbwxxc.api.dto.BasePageQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindArticlePageListRequestDTO extends BasePageQuery {

    /**
     * 文章标题
     */
    private String title;

    /**
     * 发布的起始日期
     */
    private LocalDateTime startDate;

    /**
     * 发布的结束日期
     */
    private LocalDateTime endDate;
}
