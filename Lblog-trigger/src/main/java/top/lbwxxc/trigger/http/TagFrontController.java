package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.ITagFrontService;
import top.lbwxxc.api.dto.front.tag.FindTagListResponseDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.TagEntity;
import top.lbwxxc.domain.blog.service.ITagService;
import top.lbwxxc.types.enums.ResponseCode;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tag/")
public class TagFrontController implements ITagFrontService {

    @Resource
    private ITagService tagService;

    @PostMapping("list")
    @Override
    public Response<List<FindTagListResponseDTO>> findTagList() {

        List<TagEntity> allTag = tagService.findAllTag();

        Response<List<FindTagListResponseDTO>> response = new Response<>();
        if (allTag == null || allTag.isEmpty()) {
            log.warn("文章标签不存在");
            response.setCode(ResponseCode.UN_ERROR.getCode());
            response.setInfo("文章标签不存在");
        } else {
            List<FindTagListResponseDTO> findTagListResponseDTOS = allTag.stream().map(tagEntity -> FindTagListResponseDTO.builder()
                            .id(tagEntity.getId())
                            .name(tagEntity.getName())
                            .build())
                    .toList();
            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());
            response.setData(findTagListResponseDTOS);
        }

        return response;
    }
}
