package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lbwxxc.api.IAdminTagService;
import top.lbwxxc.api.dto.SelectResponse;
import top.lbwxxc.api.dto.tag.*;
import top.lbwxxc.api.response.PageResponse;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.model.entity.TagEntity;
import top.lbwxxc.domain.blog.service.ITagService;
import top.lbwxxc.types.enums.ResponseCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/tag/")
public class AdminTagController implements IAdminTagService {

    @Resource
    private ITagService tagService;

    @PostMapping("add")
    @Override
    public Response<String> addTag(@RequestBody AddTagRequestDTO addTagRequestDTO) {

        List<String> tags = addTagRequestDTO.getTags();

        Response<String> response = new Response<>();
        if (tags == null || tags.isEmpty()) {
            response.setCode(ResponseCode.UN_ERROR.getCode());
            response.setInfo("名字不能为空");
            return response;
        }

        int i = tagService.addTags(tags);
        if (i > 0) {
            response.setCode(ResponseCode.SUCCESS.getCode());
            response.setInfo(ResponseCode.SUCCESS.getInfo());

            return response;
        }

        response.setCode(ResponseCode.UN_ERROR.getCode());
        response.setInfo("名字不能为空");
        return response;
    }

    @PostMapping("list")
    @Override
    public PageResponse<FindTagPageListResponseDTO> findTagList(@RequestBody FindTagPageListRequestDTO findTagPageListRequestDTO) {

        int current = findTagPageListRequestDTO.getCurrent();
        int size = findTagPageListRequestDTO.getSize();
        LocalDateTime startDate = findTagPageListRequestDTO.getStartDate();
        LocalDateTime endDate = findTagPageListRequestDTO.getEndDate();
        String name = findTagPageListRequestDTO.getName();

        log.info("请求参数 current: {}, size: {}, startDate: {}, endDate: {}, name: {}", current, size, startDate, endDate, name);
        List<TagEntity> categoryList = tagService.findTagList(current, size, name, startDate, endDate);
        int categorySize = tagService.findTagSize();
        PageResponse<FindTagPageListResponseDTO> pageResponse = new PageResponse<>();
        if (categoryList.isEmpty()) {
            pageResponse.setCode(ResponseCode.UN_ERROR.getCode());
            pageResponse.setInfo("文章分类不存在");
            return pageResponse;
        }

        ArrayList<FindTagPageListResponseDTO> tagPageListResponseDTOS = new ArrayList<>();
        for (TagEntity categoryEntity : categoryList) {
            tagPageListResponseDTOS.add(FindTagPageListResponseDTO.builder()
                    .id(categoryEntity.getId())
                    .name(categoryEntity.getName())
                    .createTime(categoryEntity.getCreateTime())
                    .build());
        }
        pageResponse.setData(tagPageListResponseDTOS);
        pageResponse.setCode(ResponseCode.SUCCESS.getCode());
        pageResponse.setInfo(ResponseCode.SUCCESS.getInfo());
        pageResponse.setPages(current);
        pageResponse.setSize(size);
        pageResponse.setTotal(categorySize);
        return pageResponse;

    }

    @PostMapping("delete")
    @Override
    public Response<String> deleteTag(@RequestBody DeleteTagRequestDTO deleteTagRequestDTO) {
        int i = tagService.deleteTag(deleteTagRequestDTO.getId());
        if (i > 0) {
            return Response.<String>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .build();
        }

        return Response.<String>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("删除文章标签失败")
                .build();
    }

    @PostMapping("select/list")
    @Override
    public Response<List<SelectResponse>> findTagSelectList(@RequestBody SearchTagRequestDTO searchTagRequestDTO) {
        List<TagEntity> allTag = tagService.searchTagByKey(searchTagRequestDTO.getKey());
        List<SelectResponse> selectRspVOS = null;
        if (!allTag.isEmpty()) {
            selectRspVOS = allTag.stream().map(tagEntity ->  SelectResponse.builder()
                            .label(tagEntity.getName())
                            .value(tagEntity.getId())
                            .build())
                    .toList();
        }

        if (selectRspVOS != null) {
            return Response.<List<SelectResponse>>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(selectRspVOS)
                    .build();
        }

        return Response.<List<SelectResponse>>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("获取文章标签失败")
                .build();
    }
}
