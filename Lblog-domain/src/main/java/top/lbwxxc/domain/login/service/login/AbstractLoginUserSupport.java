package top.lbwxxc.domain.login.service.login;


import top.lbwxxc.domain.login.service.login.factory.DefaultUserLoginStrategyFactory;
import top.lbwxxc.types.design.framework.tree.AbstractStrategyRouter;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public abstract class AbstractLoginUserSupport<LoginUserEntity, DynamicContext, IsLoginEntity> extends AbstractStrategyRouter<LoginUserEntity, DefaultUserLoginStrategyFactory.DynamicContext, IsLoginEntity> {


    @Override
    protected void multiThread(LoginUserEntity requestParameter, DefaultUserLoginStrategyFactory.DynamicContext dynamicContext) throws ExecutionException, InterruptedException, TimeoutException {
        //
    }
}
