package top.lbwxxc.infrastructure.adapter.port;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lbwxxc.domain.blog.adapter.port.IFilePort;
import top.lbwxxc.infrastructure.gateway.UploadFilesService;

@Service
public class FilePort implements IFilePort {

    @Resource
    private UploadFilesService uploadFilesService;


    @Override
    public String uploadFile(MultipartFile file) {
        return uploadFilesService.uploadFile(file);
    }
}
