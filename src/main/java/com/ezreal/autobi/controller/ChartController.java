package com.ezreal.autobi.controller;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.chart.ChartService;
import com.ezreal.autobi.domain.chart.model.req.ChartInfoReq;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartInfoResp;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import com.ezreal.autobi.manager.CurrentLimitManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/chart")
@Slf4j
public class ChartController {

    @Autowired
    private ChartService chartService;

    @Autowired
    private CurrentLimitManager currentLimitManager;

    @GetMapping("/list")
    public BaseResponse<List<ChartInfoResp>> getChartInfoList(ChartInfoReq chartInfoReq) {
        if (chartInfoReq == null) {
            log.warn("FileController|getChartInfoList| chartInfoReq 参数为空");
            return ResultsUtils.fail(Code.ChartCode.CHART_UPLOAD_FAIL.getCode(), Code.ChartCode.CHART_UPLOAD_FAIL.getMessage());
        }

        if (chartInfoReq.getUserId() == null) {
            log.warn("FileController|getChartInfoList| userId 参数为空");
            return ResultsUtils.fail(Code.ChartCode.CHART_UPLOAD_FAIL.getCode(), Code.ChartCode.CHART_UPLOAD_FAIL.getMessage());
        }

        return chartService.getChartInfoList(chartInfoReq);
    }

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

        // 限流处理
        currentLimitManager.doRateLimit(String.format("request-%s", chartReq.getUserId()));

        return chartService.getChartData(multipartFile, chartReq);
    }
}
