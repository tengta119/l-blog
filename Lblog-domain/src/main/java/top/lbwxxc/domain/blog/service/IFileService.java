package top.lbwxxc.domain.blog.service;


import org.springframework.web.multipart.MultipartFile;


public interface IFileService {

    String uploadFile(MultipartFile file);
}
