package com.ezreal.autobi.domain.chart;

import com.ezreal.autobi.common.Code;
import com.ezreal.autobi.domain.chart.exception.ChartException;
import com.ezreal.autobi.domain.chart.model.entity.Chart;
import com.ezreal.autobi.domain.chart.model.entity.TableList;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import com.ezreal.autobi.mapper.ChartMapper;
import com.yupi.yucongming.dev.client.YuCongMingClient;
import com.yupi.yucongming.dev.model.DevChatRequest;
import com.yupi.yucongming.dev.model.DevChatResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Slf4j
public abstract class AbstractChartService implements ChartService {

    @Resource
    private YuCongMingClient yuCongMingClient;

    @Value("${yuapi.client.modelId}")
    private Long modelId;

    @Resource
    private ChartMapper chartMapper;


    @Override
    public String callAIInterface(StringBuilder problem) {
        // 调用 AI
        DevChatRequest devChatRequest = new DevChatRequest();
        devChatRequest.setModelId(modelId);
        devChatRequest.setMessage(problem.toString());

        DevChatResponse devChatResponse = yuCongMingClient.doChat(devChatRequest).getData();
        String content = devChatResponse.getContent();

        log.info("ChartServiceImpl|getChartData|生成的内容为：{}", content);
        return content;
    }

    @Transactional
    @Override
    public ChartResp saveChartDataToDB(ChartReq chartReq, String content, String csvResult) {

        // 处理结果
        String[] results = content.split("【【【【【");

        if (results.length < 2) {
            throw new ChartException(Code.ChartCode.CHART_LENGTH_ERROR);
        }

        ChartResp chartResp = new ChartResp();
        String genChartCode = results[1].trim();
        String genChartResult = results[2].trim();
        chartResp.setGenChart(genChartCode);
        chartResp.setGenResult(genChartResult);


        // 保存图表实体到数据库
        Chart chart = new Chart();
        chart.setName(chartReq.getName());
        chart.setGoal(chartReq.getGoal());
        chart.setUserId(chartReq.getUserId());
        chart.setCharType(chartReq.getChartType());
        chart.setGenChart(genChartCode);
        chart.setGenResult(genChartResult);
        chart.setUserId(chart.getUserId());
        chartMapper.insert(chart);


        // 保存图表数据到 新建的表中
        TableList tableList = new TableList();

        // 数据解析
        String[] split = csvResult.split("\n");
        String[] columnName = split[0].split(",");
        List<String> list = Arrays.asList(columnName);
        tableList.setColumnName(list);

        for (int i = 1; i < split.length; i++) {
            String values = split[i];
            List<String> asList = Arrays.asList(values.split(","));
            tableList.getValues().add(asList);
        }

        // 创建数据库表
        String tableName = String.format("chart_data_%s", chart.getId());
        chartMapper.createGenChartTable(tableName, tableList.getColumnName());

        // 插入数据
        List<List<String>> values = tableList.getValues();
        chartMapper.insertGenChartValue(tableName, values);

        return chartResp;
    }
}
