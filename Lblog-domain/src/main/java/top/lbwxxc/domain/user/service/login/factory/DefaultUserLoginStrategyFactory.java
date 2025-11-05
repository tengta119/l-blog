package top.lbwxxc.domain.user.service.login.factory;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.user.model.entity.UserAccountEntity;
import top.lbwxxc.domain.user.model.entity.LoginUserEntity;
import top.lbwxxc.domain.user.service.login.node.RootNode;
import top.lbwxxc.types.design.framework.tree.StrategyHandler;

@Service
public class DefaultUserLoginStrategyFactory {

    private final RootNode rootNode;

    public DefaultUserLoginStrategyFactory(RootNode rootNode) {
        this.rootNode = rootNode;
    }

    public StrategyHandler<LoginUserEntity, DynamicContext, UserAccountEntity> strategyHandler() {
        return rootNode;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DynamicContext {

        private boolean isLogin;
        private String password;
        private Long userId;

    }
}
