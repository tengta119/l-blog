package top.lbwxxc.trigger.event.subscriber;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import top.lbwxxc.domain.blog.service.IArticleService;
import top.lbwxxc.domain.blog.model.event.ReadArticleEvent;

@Component
@Slf4j
public class ReadArticleSubscriber implements ApplicationListener<ReadArticleEvent> {

    @Resource
    private IArticleService articleService;

    @Override
    @Async("threadPool")
    public void onApplicationEvent(ReadArticleEvent event) {

        Long articleId = event.getArticleId();
        log.info("增加文章 {} 的阅读量", articleId);

        articleService.addArticleReadNum(articleId);
    }
}
