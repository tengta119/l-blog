package top.lbwxxc.domain.blog.service.settings;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.domain.blog.service.IBlogSettingsService;

@Service
public class BlogSettingsService implements IBlogSettingsService {

    @Resource
    private IBlogRepository blogRepository;

    @Override
    public int updateBlogSettings(String logo, String name) {
        return blogRepository.updateBlogSettings(logo, name);
    }

    @Override
    public int addExternalUrl(String name, String logo, String url) {
        return blogRepository.addExternalUrl(name, logo, url);
    }

    @Override
    public int updateExternalUrl(Long id, String name, String logo, String url) {
        return blogRepository.updateExternalUrl(id, name, logo, url);
    }

}
