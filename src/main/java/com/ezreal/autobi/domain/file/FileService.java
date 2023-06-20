package com.ezreal.autobi.domain.file;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.domain.file.model.FileUploadResp;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {

    BaseResponse<FileUploadResp> upload(MultipartFile multipartFile);
}
