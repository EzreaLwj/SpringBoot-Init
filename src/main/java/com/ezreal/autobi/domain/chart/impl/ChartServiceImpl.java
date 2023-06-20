package com.ezreal.autobi.domain.chart.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.common.ResultsUtils;
import com.ezreal.autobi.domain.chart.ChartService;
import com.ezreal.autobi.domain.chart.exception.ChartException;
import com.ezreal.autobi.domain.chart.model.entity.Chart;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import com.ezreal.autobi.mapper.ChartMapper;
import com.ezreal.autobi.utils.ExcelUtils;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
* @author Ezreal
* @description 针对表【chart(图表表)】的数据库操作Service实现
* @createDate 2023-06-17 12:08:59
*/
@Service
@Slf4j
public class ChartServiceImpl extends ServiceImpl<ChartMapper, Chart>
    implements ChartService{

    @Resource
    private YuCongMingClient yuCongMingClient;

    @Value("${yuapi.client.modelId}")
    private Long modelId;

    @Resource
    private ChartMapper chartMapper;

    private final String problemFormat = "分析需求：\n" +
            "%s, 请生成%s \n" +
            "原始数据：\n";

    @Override
    public BaseResponse<ChartResp> getChartData(MultipartFile multipartFile, ChartReq chartReq) {

        String goal = chartReq.getGoal();
        String charType = chartReq.getCharType();
        try {

            // 生成 prompt
            log.info("ChartServiceImpl|getChartData|开始生成 prompt, userId: {}", chartReq.getUserId());
            StringBuilder problem = new StringBuilder();
            problem.append(String.format(problemFormat, goal, charType));

            // 处理 Excel 文件
            String csvResult = ExcelUtils.excelToCsv(multipartFile.getInputStream());
            problem.append(csvResult);
            log.info("ChartServiceImpl|getChartData|生成的prompt: {}", problem);

            // 调用 AI
            DevChatRequest devChatRequest = new DevChatRequest();
            devChatRequest.setModelId(modelId);
            devChatRequest.setMessage(problem.toString());

            DevChatResponse devChatResponse = yuCongMingClient.doChat(devChatRequest).getData();
            String content = devChatResponse.getContent();

            log.info("ChartServiceImpl|getChartData|生成的内容为：{}", content);

            // 处理结果
            String[] split = content.split("【【【【【");

            if (split.length < 3) {
                throw new ChartException(Code.ChartCode.CHART_LENGTH_ERROR);
            }

            ChartResp chartResp = new ChartResp();
            String genChartCode = split[1].trim();
            String genChartResult = split[2].trim();
            chartResp.setGenChart(genChartCode);
            chartResp.setGenResult(genChartResult);


            // 保存图表数据到数据库
            Chart chart = new Chart();
            chart.setName(chartReq.getName());
            chart.setGoal(goal);
            chart.setUserId(chartReq.getUserId());
            chart.setCharData(csvResult);
            chart.setCharType(charType);
            chart.setGenChart(genChartCode);
            chart.setGenResult(genChartResult);
            chart.setUserId(chart.getUserId());
            chartMapper.insert(chart);

            chartResp.setChartId(chart.getId());
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



