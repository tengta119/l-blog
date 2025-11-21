package top.lbwxxc.api.dto.admin.settings;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindExternalUrlListResponseDTO {

    private Long id;
    private String name;
    private String url;
    private String logo;
}
