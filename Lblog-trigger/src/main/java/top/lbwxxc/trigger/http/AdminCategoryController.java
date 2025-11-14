package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminCategoryService;
import top.lbwxxc.api.dto.AddCategoryRequestDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.service.ICategoryService;
import top.lbwxxc.types.enums.ResponseCode;

@Slf4j
@RestController
@RequestMapping("/category/")
public class AdminCategoryController implements IAdminCategoryService {

    @Resource
    private ICategoryService categoryService;


    @PostMapping("add")
    @Override
    public Response<String> addCategory(@RequestBody AddCategoryRequestDTO addCategoryRequestDTO) {

        int addCategory = categoryService.addCategory(addCategoryRequestDTO.getName());
        if (addCategory > 0) {
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        }

        return Response.<String>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("添加文章分类失败")
                .build();
    }
}
