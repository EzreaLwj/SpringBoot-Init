package com.ezreal.autobi.testApi;

import cn.hutool.core.io.resource.ResourceUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class EasyExcelTest {

    @Test
    public void testEasyExcel() throws FileNotFoundException {
        InputStream stream = ResourceUtil.getStream("classpath:test.xlsx");


        List<Map<Integer, String>> list = EasyExcel.read(stream)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet()
                .headRowNumber(2)
                .doReadSync();

        Map<Integer, String> integerStringMap = list.get(0);

        System.out.println(integerStringMap);



        System.out.println(list);

    }

    public static void main(String[] args) {
        InputStream stream = ResourceUtil.getStream("classpath:test.xlsx");


        List<Map<Integer, String>> list = EasyExcel.read(stream)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet()
                .headRowNumber(0) // 从第几行开始读
                .doReadSync();

        Map<Integer, String> integerStringMap = list.get(0);

        System.out.println(integerStringMap);



        System.out.println(list);
    }

}
