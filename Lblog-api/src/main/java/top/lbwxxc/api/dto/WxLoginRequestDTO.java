package top.lbwxxc.api.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WxLoginRequestDTO {

    // 前端生成二维码的 ticket
    private String ticket;

}
