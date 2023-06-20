package com.ezreal.autobi.utils;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.excel.EasyExcel;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Ezreal
 * @Date 2023/6/20
 */
public class ExcelUtils {

    public static String excelToCsv(InputStream inputStream) {
        // 处理 Excel 文件
        StringBuilder csvResult = new StringBuilder();
        List<LinkedHashMap<String, Object>> dataList = EasyExcel.read(inputStream)
                .sheet()
                .headRowNumber(0)
                .doReadSync();

        LinkedHashMap<String, Object> headLinkedMap = dataList.get(0);
        Collection<Object> values = headLinkedMap.values().stream().filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
        csvResult.append(StringUtils.join(values, ",")).append("\n");

        for (int i = 1; i < dataList.size(); i++) {
            Collection<Object> data = dataList.get(i).values();
            data = data.stream().filter(ObjectUtil::isNotEmpty).collect(Collectors.toList());
            csvResult.append(StringUtils.join(data, ',')).append("\n");
        }
        return csvResult.toString();
    }
}
