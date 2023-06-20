package com.ezreal.autobi.domain.chart.model.req;

import lombok.Data;

@Data
public class ChartReq {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 名称
     */
    private String name;

    /**
     * 分析目标
     */
    private String goal;


    /**
     * 图表类型
     */
    private String charType;

}
