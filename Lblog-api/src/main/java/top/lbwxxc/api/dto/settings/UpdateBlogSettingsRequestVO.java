package top.lbwxxc.api.dto.settings;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateBlogSettingsRequestVO {

    private String logo;

    private String name;

}
