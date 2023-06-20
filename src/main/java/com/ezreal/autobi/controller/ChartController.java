package com.ezreal.autobi.controller;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.chart.ChartService;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    @Autowired
    private ChartService chartService;

    @PostMapping("/file")
    public BaseResponse<ChartResp> getChartData(@RequestPart("file") MultipartFile multipartFile,
                                                ChartReq chartReq) {

        if (multipartFile == null) {
            log.warn("FileController|uploadFile| multipartFile 参数为空");
            return ResultsUtils.fail(Code.ChartCode.CHART_UPLOAD_FAIL.getCode(), Code.ChartCode.CHART_UPLOAD_FAIL.getMessage());
        }

        if (chartReq == null) {
            log.warn("FileController|uploadFile| chartReq 参数为空");
            return ResultsUtils.fail(Code.ChartCode.CHART_UPLOAD_FAIL.getCode(), Code.ChartCode.CHART_UPLOAD_FAIL.getMessage());
        }

        return chartService.getChartData(multipartFile, chartReq);
    }
}
