package top.lbwxxc.api.dto.admin.oss;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UploadFileResponseDTO {

    /**
     * 文件的访问链接
     */
    private String url;
}
