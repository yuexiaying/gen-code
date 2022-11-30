package com.fj;

import cn.hutool.core.util.StrUtil;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * 新增页面
 * @author fjding
 * @date 2022/11/13
 */
public class AddStage {

    private Stage stage = new Stage();

    ObservableList<ModelData> list;

    Map<String, TextField> map;

    Alert alert;
    private static String T_NAME = "name";
    private static String T_PACKAGE = "package";
    private static String T_AUTHOR = "author";
    private static String T_PATH = "path";

    public AddStage(ObservableList<ModelData> list) {
        stage.getIcons().add(new Image("/image/2.png"));
        this.list = list;
        map = new HashMap<>();
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("提示");
        ((Stage)alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/image/2.png"));
        alert.setHeaderText("");
    }

    public void updateData(){
        FilteredList<ModelData> filteredList = list.filtered(e -> e.isChecked());
        if (!(filteredList.size() == 1)) {
            alert("仅且选择一个！");
            return;
        }
        ModelData modelData = filteredList.get(0);
        base("修改数据");
        map.get(T_NAME).setText(modelData.getName());
        map.get(T_PACKAGE).setText(modelData.getPkg());
        map.get(T_AUTHOR).setText(modelData.getAuthor());
        map.get(T_PATH).setText(modelData.getPath());
        list.removeIf(e -> e.isChecked());
        ConfigUtils.delete(modelData.getName());
        stage.show();
    }

    public void addData(){
        base("新增数据");
        stage.show();
    }
    public void base(String title) {
        BorderPane root = new BorderPane();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(50, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        Text nameText = new Text("名字");
        TextField nameTextField = new TextField();
        map.put(T_NAME, nameTextField);
        gridPane.add(nameText, 0, 0);
        gridPane.add(nameTextField, 1, 0);

        Text packageText = new Text("包名");
        TextField packageTextField = new TextField();
        map.put(T_PACKAGE, packageTextField);
        gridPane.add(packageText, 0, 1);
        gridPane.add(packageTextField, 1, 1);

        Text authorText = new Text("作者");
        TextField authorTextField = new TextField();
        map.put(T_AUTHOR, authorTextField);
        gridPane.add(authorText, 0, 2);
        gridPane.add(authorTextField, 1, 2);

        Text pathText = new Text("生成路径");
        TextField pathTextField = new TextField();
        map.put(T_PATH, pathTextField);
        gridPane.add(pathText, 0, 3);
        gridPane.add(pathTextField, 1, 3);

        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setPadding(new Insets(10, 10, 120, 10));
        Button submitBtn = new Button("提交");
        submitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                add();
            }
        });
        Button clearBtn = new Button("清除");
        clearBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                map.values().forEach(e -> e.clear());
            }
        });
        hBox.getChildren().addAll(submitBtn, clearBtn);
        //gridPane.add(submitBtn,0,4);
        //gridPane.add(clearBtn,1,4);
        root.setCenter(gridPane);
        root.setBottom(hBox);
        Scene scene = new Scene(root, 400, 400);

        stage.setTitle(title);
        stage.setScene(scene);
    }

    /**
     * 新增数据
     */
    @SneakyThrows
    private void add() {
        String text = map.get(T_NAME).getText();
        Optional<ModelData> optional = list.filtered(e -> e.getName().equals(text)).stream().findAny();
        if (optional.isPresent()) {
            alert(text + "已存在");
            return;
        }
        // 数据检查
        if (!check()) {
            return;
        }
        ModelData data = ModelData.builder().name(text)
                .pkg(map.get(T_PACKAGE).getText())
                .author(map.get(T_AUTHOR).getText())
                .path(map.get(T_PATH).getText())
                .build();
        list.add(data);
        ConfigUtils.addData(data);
        stage.close();
    }

    private boolean check() {
        for (String key : map.keySet()) {
            if (StrUtil.isEmpty(map.get(key).getText())) {
                alert(key + "不能为空！");
                return false;
            }
        }
        return true;
    }

    private void alert(String msg) {
        alert.setContentText(msg);
        alert.show();
    }
}
