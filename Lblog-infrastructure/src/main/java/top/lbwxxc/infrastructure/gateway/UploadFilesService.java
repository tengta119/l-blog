package top.lbwxxc.infrastructure.gateway;


import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class UploadFilesService {

    @Resource(name = "aliyun-oss")
    private OSS aliyunOSS;

    @Value("${aliyun.oss.bucket-name}")
    private String bucketName;

    @Value("${aliyun.oss.file-path-prefix}")
    private String filePathPrefix;

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    /**
     * 上传文件到阿里云 OSS
     * @param file 前端传入的文件（MultipartFile）
     * @return 上传后的文件访问 URL
     */
    public String uploadFile(MultipartFile file) {
        try {
            // 1. 校验文件是否为空
            if (file.isEmpty()) {
                throw new RuntimeException("上传文件不能为空");
            }

            // 2. 处理文件名（避免重复：UUID + 原文件名）
            String originalFilename = file.getOriginalFilename(); // 原文件名（如 "test.png"）
            String fileName = UUID.randomUUID().toString() + "_" + originalFilename; // 如 "a1b2c3_test.png"

            // 3. 拼接文件在 OSS 中的完整路径（前缀 + 文件名）
            String fileKey = filePathPrefix + fileName; // 如 "upload/a1b2c3_test.png"

            // 4. 上传文件（通过 InputStream 上传）
            PutObjectRequest request = new PutObjectRequest(bucketName, fileKey, file.getInputStream());
            aliyunOSS.putObject(request);

            // 5. 生成文件访问 URL（格式：https://bucketName.endpoint/fileKey）
            String fileUrl = "https://" + bucketName + "." + endpoint + "/" + fileKey;
            log.info("文件上传成功，URL：{}", fileUrl);
            return fileUrl;

        } catch (IOException e) {
            log.error("文件上传失败", e);
            throw new RuntimeException("文件上传失败：" + e.getMessage());
        }
    }
}
