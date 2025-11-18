package top.lbwxxc.domain.blog.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExternalUrlEntity {

    private Long id;
    private String name;
    private String url;
    private String logo;
}
