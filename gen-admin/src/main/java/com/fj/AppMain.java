package com.fj;

import com.alibaba.excel.EasyExcel;
import com.fj.gen.std.StdData;
import com.fj.gen.std.StdReadListener;
import com.fj.ui.ModelData;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 程序入口
 *
 * @author fjding
 * @date 2022/12/5
 */
@SpringBootApplication
public class AppMain {

    //public static void main(String[] args) {
    //    SpringApplication.run(AppMain.class, args);
    //    Application.launch(App.class, args);
    //}

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class,args);
        ModelData modelData = ModelData.builder()
                .name("test")
                .pkg("com.fj")
                .author("fjding")
                .path("C:\\Users\\fjding\\Desktop\\test\\gen\\test.xlsx")
                .build();

        EasyExcel.read(modelData.getPath()).head(StdData.class).registerReadListener(new StdReadListener(modelData)).doReadAll();
    }
}
