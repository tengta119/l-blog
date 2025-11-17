package top.lbwxxc.domain.blog.service.oss;


import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import top.lbwxxc.domain.blog.adapter.port.IFilePort;
import top.lbwxxc.domain.blog.service.IFileService;

@Service
public class FileService implements IFileService {

    @Resource
    private IFilePort filePort;

    @Override
    public String uploadFile(MultipartFile file) {
        return filePort.uploadFile(file);
    }
}
