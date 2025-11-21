package top.lbwxxc.api.dto.front.article;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.lbwxxc.api.dto.front.category.FindCategoryListResponseDTO;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindIndexArticlePageListResponseDTO {

    private Long id;
    private String cover;
    private String title;
    private LocalDateTime createTime;
    private String summary;

    /**
     * 文章分类
     */
    private FindCategoryListResponseDTO category;

    /**
     * 文章标签
     */
    private List<FindTagListResponseDTO> tags;
}
