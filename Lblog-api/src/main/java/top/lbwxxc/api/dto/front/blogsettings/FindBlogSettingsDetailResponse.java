package top.lbwxxc.api.dto.front.blogsettings;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class FindBlogSettingsDetailResponse {

    private String logo;
    private String name;
    private String author;
    private String introduction;
    private String avatar;
}
