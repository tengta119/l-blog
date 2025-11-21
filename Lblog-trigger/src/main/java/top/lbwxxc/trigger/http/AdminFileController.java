package top.lbwxxc.trigger.http;


import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import top.lbwxxc.api.IAdminFileService;
import top.lbwxxc.api.dto.oss.UploadFileResponseDTO;
import top.lbwxxc.api.response.Response;
import top.lbwxxc.domain.blog.service.IFileService;
import top.lbwxxc.types.enums.ResponseCode;

@Slf4j
@RestController
@RequestMapping("/admin/oss/")
public class AdminFileController implements IAdminFileService {

    @Resource
    private IFileService fileService;

    @PostMapping("/upload")
    @Override
    public Response<UploadFileResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {

        String url = fileService.uploadFile(file);

        if (!url.isEmpty()) {
            UploadFileResponseDTO uploadFileResponseDTO = UploadFileResponseDTO.builder()
                    .url(url)
                    .build();

            return Response.<UploadFileResponseDTO>builder()
                    .code(ResponseCode.SUCCESS.getCode())
                    .info(ResponseCode.SUCCESS.getInfo())
                    .data(uploadFileResponseDTO).build();
        }

        return Response.<UploadFileResponseDTO>builder()
                .code(ResponseCode.UN_ERROR.getCode())
                .info("上传文件失败")
                .build();
    }
}
