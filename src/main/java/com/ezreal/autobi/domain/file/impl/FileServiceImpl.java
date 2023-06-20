package com.ezreal.autobi.domain.file.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.file.FileService;
import com.ezreal.autobi.domain.file.model.FileUploadResp;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.model.DevChatRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Autowired
    private YuCongMingClient yuCongMingClient;

    @Override
    public BaseResponse<FileUploadResp> upload(MultipartFile multipartFile) {

        try {
            log.info("FileServiceImpl|upload|上传文件名, {}", multipartFile.getOriginalFilename());
            InputStream inputStream = multipartFile.getInputStream();

            List<Map<Integer, String>> list = EasyExcel.read(inputStream)
                    .excelType(ExcelTypeEnum.XLSX)
                    .sheet()
                    .headRowNumber(0)
                    .doReadSync();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResultsUtils.success(Code.FileCode.FILE_UPLOAD_SUCCESS.getCode(),
                Code.FileCode.FILE_UPLOAD_SUCCESS.getMessage(), null);
    }
}
