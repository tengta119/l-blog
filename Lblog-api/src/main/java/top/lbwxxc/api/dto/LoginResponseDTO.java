package top.lbwxxc.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class LoginResponseDTO {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 手机号
     */
    private String phone;

    // 邮箱
    private String email;

    private String token;
}
