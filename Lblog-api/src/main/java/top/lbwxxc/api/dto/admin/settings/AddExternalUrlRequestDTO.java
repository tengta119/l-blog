package top.lbwxxc.api.dto.admin.settings;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddExternalUrlRequestDTO {

    private String name;
    private String url;
    private String logo;
}
