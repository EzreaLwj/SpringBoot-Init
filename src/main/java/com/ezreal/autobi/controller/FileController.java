package com.ezreal.autobi.controller;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.file.FileService;
import com.ezreal.autobi.domain.file.model.FileUploadResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Slf4j
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/file")
    public BaseResponse<FileUploadResp> uploadFile(@RequestPart("file") MultipartFile multipartFile) {
        if (multipartFile == null) {
            log.warn("FileController|uploadFile| multipartFile 参数为空");
            return ResultsUtils.fail(Code.FileCode.FILE_UPLOAD_FAIL.getCode(), Code.FileCode.FILE_UPLOAD_SUCCESS.getMessage());
        }
        BaseResponse<FileUploadResp> upload = fileService.upload(multipartFile);

        return upload;
    }
}
