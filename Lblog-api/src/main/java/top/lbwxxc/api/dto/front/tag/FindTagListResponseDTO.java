package top.lbwxxc.api.dto.front.tag;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FindTagListResponseDTO {

    private Long id;
    private String name;
}
