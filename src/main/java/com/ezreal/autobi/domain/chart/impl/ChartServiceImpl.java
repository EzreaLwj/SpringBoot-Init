package com.ezreal.autobi.domain.chart.impl;

import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.PageParams;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.chart.AbstractChartService;
import com.ezreal.autobi.domain.chart.ChartService;
import com.ezreal.autobi.domain.chart.model.req.ChartInfoReq;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartInfoResp;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import com.ezreal.autobi.mapper.ChartMapper;
import com.ezreal.autobi.utils.ExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author Ezreal
 * @description 针对表【chart(图表表)】的数据库操作Service实现
 * @createDate 2023-06-17 12:08:59
 */
@Service
@Slf4j
public class ChartServiceImpl extends AbstractChartService {


    @Resource
    private ChartMapper chartMapper;

    private final String problemFormat = "分析需求：\n" +
            "%s, 请生成%s \n" +
            "原始数据：\n";


    @Override
    public BaseResponse<List<ChartInfoResp>> getChartInfoList(ChartInfoReq chartInfoReq) {
        Long userId = chartInfoReq.getUserId();

        PageParams pageParams = chartInfoReq.getPageParams();
        pageParams.setPageNum((pageParams.getPageNum() - 1) * pageParams.getPageSize());
        List<ChartInfoResp> chartRespList = chartMapper.selectChartInfoList(userId, pageParams);
        return ResultsUtils.success(Code.ChartCode.CHART_SELECT_SUCCESS.getCode(),
                Code.ChartCode.CHART_SELECT_SUCCESS.getMessage(), chartRespList);
    }

    @Override
    public BaseResponse<ChartResp> getChartData(MultipartFile multipartFile, ChartReq chartReq) {

        String goal = chartReq.getGoal();
        String charType = chartReq.getChartType();
        try {

            // 生成 prompt
            log.info("ChartServiceImpl|getChartData|开始生成 prompt, userId: {}", chartReq.getUserId());
            StringBuilder problem = new StringBuilder();
            problem.append(String.format(problemFormat, goal, charType));

            // 处理 Excel 文件
            String csvResult = ExcelUtils.excelToCsv(multipartFile.getInputStream());
            problem.append(csvResult);
            log.info("ChartServiceImpl|getChartData|生成的prompt: {}", problem);


            // 调用 AI 接口
            String content = callAIInterface(problem);
            log.info("ChartServiceImpl|getChartData|生成的内容为：{}", content);

            // 处理数据库的结果
            ChartService ChartService = (ChartService) AopContext.currentProxy();
            ChartResp chartResp = ChartService.saveChartDataToDB(chartReq, content, csvResult);

            return ResultsUtils.success(Code.ChartCode.CHART_UPLOAD_SUCCESS.getCode(),
                    Code.ChartCode.CHART_UPLOAD_SUCCESS.getMessage(),
                    chartResp);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return ResultsUtils.fail(Code.ChartCode.CHART_UPLOAD_FAIL.getCode(),
                Code.ChartCode.CHART_UPLOAD_FAIL.getMessage());
    }
}



