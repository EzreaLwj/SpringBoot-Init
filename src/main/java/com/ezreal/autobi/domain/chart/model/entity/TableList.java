package com.ezreal.autobi.domain.chart.model.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ezreal
 * @Date 2023/6/22
 */
@Data
public class TableList {
    private List<String> columnName;

    private List<List<String>> values;

    public TableList() {
        this.values = new ArrayList<>();
    }
}
