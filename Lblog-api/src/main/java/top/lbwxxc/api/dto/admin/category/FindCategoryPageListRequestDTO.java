package top.lbwxxc.api.dto.admin.category;


import lombok.*;
import top.lbwxxc.api.dto.BasePageQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindCategoryPageListRequestDTO extends BasePageQuery {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 创建的起始日期
     */
    private LocalDateTime startDate;

    /**
     * 创建的结束日期
     */
    private LocalDateTime endDate;
}
