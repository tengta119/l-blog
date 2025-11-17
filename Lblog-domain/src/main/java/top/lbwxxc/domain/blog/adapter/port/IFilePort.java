package top.lbwxxc.domain.blog.adapter.port;


import org.springframework.web.multipart.MultipartFile;

public interface IFilePort {

    String uploadFile(MultipartFile file);
}
