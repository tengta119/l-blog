package top.lbwxxc.domain.blog.service.category;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import top.lbwxxc.domain.blog.adapter.repository.IBlogRepository;
import top.lbwxxc.domain.blog.service.ICategoryService;

@Service
public class CategoryService implements ICategoryService {

    @Resource
    private IBlogRepository blogRepository;

    @Override
    public int addCategory(String name) {
        return blogRepository.addCategory(name);
    }
}
