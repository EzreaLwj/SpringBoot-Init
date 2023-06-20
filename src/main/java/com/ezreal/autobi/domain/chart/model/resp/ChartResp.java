package com.ezreal.autobi.domain.chart.model.resp;

import lombok.Data;

@Data
public class ChartResp {

    /**
     * 图表 ID
     */
    private Long chartId;

    /**
     * 生成的图表数据
     */
    private String genChart;

    /**
     * 生成图表结果
     */
    private String genResult;
}
