package top.lbwxxc.api.dto.front.category;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindCategoryListResponseDTO {

    private Long id;
    private String name;
}
