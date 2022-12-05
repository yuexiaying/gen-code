package com.fj;

import com.fj.ui.App;
import javafx.application.Application;
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

    public static void main(String[] args) {
        SpringApplication.run(AppMain.class, args);
        Application.launch(App.class, args);
    }

}
