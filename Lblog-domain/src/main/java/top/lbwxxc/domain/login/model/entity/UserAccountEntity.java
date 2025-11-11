package top.lbwxxc.domain.login.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserAccountEntity {

    /**
     * 主键ID
     */
    private Long id;

    private String token;

}
