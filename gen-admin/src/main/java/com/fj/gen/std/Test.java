package com.fj.gen.std;

import com.alibaba.excel.EasyExcel;
import com.fj.ui.ModelData;

/**
 * @author fjding
 * @date 2022/11/30
 */
public class Test {

    public static void main(String[] args) {
        ModelData modelData = ModelData.builder()
                .name("test")
                .pkg("com.fj")
                .author("fjding")
                .path("C:\\Users\\fjding\\Desktop\\test\\gen\\test.xlsx")
                .build();

        EasyExcel.read(modelData.getPath()).head(StdData.class).registerReadListener(new StdReadListener(modelData)).doReadAll();
    }
}
