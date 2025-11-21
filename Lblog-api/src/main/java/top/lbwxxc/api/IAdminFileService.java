package top.lbwxxc.api;


import org.springframework.web.multipart.MultipartFile;
import top.lbwxxc.api.dto.admin.oss.UploadFileResponseDTO;
import top.lbwxxc.api.response.Response;

public interface IAdminFileService {

    Response<UploadFileResponseDTO> uploadFile(MultipartFile file);
}
