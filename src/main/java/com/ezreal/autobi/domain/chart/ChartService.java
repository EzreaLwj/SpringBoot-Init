package com.ezreal.autobi.domain.chart;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ezreal.autobi.common.BaseResponse;
import com.ezreal.autobi.domain.chart.model.entity.Chart;
import com.ezreal.autobi.domain.chart.model.req.ChartInfoReq;
import com.ezreal.autobi.domain.chart.model.req.ChartReq;
import com.ezreal.autobi.domain.chart.model.resp.ChartInfoResp;
import com.ezreal.autobi.domain.chart.model.resp.ChartResp;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Ezreal
 * @description 针对表【chart(图表表)】的数据库操作Service
 * @createDate 2023-06-17 12:08:59
 */
public interface ChartService {

    BaseResponse<ChartResp> getChartData(MultipartFile multipartFile, ChartReq chartReq);

    BaseResponse<List<ChartInfoResp>> getChartInfoList(ChartInfoReq chartInfoReq);

    /**
     * 解析整理数据，保存到数据库中
     *
     * @param chartReq  请求
     * @param csvResult Excel 解析后的数据
     * @return 结果
     */
    ChartResp saveChartDataToDB(ChartReq chartReq, String csvResult);

    /**
     * 调用 AI 接口
     *
     * @param problem 问题
     * @return 结果
     */
    String callAIInterface(StringBuilder problem);
}
