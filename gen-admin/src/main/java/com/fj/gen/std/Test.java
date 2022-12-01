package com.fj.gen.std;

import com.alibaba.excel.EasyExcel;
import com.fj.ui.ModelData;

/**
 * @author fjding
 * @date 2022/11/30
 */
public class Test {

    public static void main(String[] args) {
        String fileName = "C:\\Users\\fjding\\Desktop\\test\\gen\\test.xlsx";
        ModelData modelData = ModelData.builder()
                .name("test")
                .pkg("com.fj")
                .author("fjding")
                .path("C:\\Users\\fjding\\Desktop\\test\\gen")
                .build();

        EasyExcel.read(fileName).head(StdData.class).registerReadListener(new StdReadListener(modelData)).doReadAll();
    }
}
