package com.fj;

import com.fj.ui.AddStage;
import com.fj.ui.ConfigUtils;
import com.fj.ui.ModelData;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * 入口
 *
 * @author fjding
 * @date 2022/11/10
 */
public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    AddStage addStage;

    ObservableList<ModelData> list = FXCollections.observableArrayList();

    @Override
    public void init() throws Exception {
        List<ModelData> data = ConfigUtils.getData();
        list.addAll(data);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.getIcons().add(new Image("/image/2.png"));
        addStage = new AddStage(list);
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(20);
        Button add = new Button("增加");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addStage.addData();
            }
        });
        Button del = new Button("删除");
        del.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Set<String> set = list.stream().filter(e -> e.isChecked()).map(e -> e.getName()).collect(Collectors.toSet());
                ConfigUtils.delete(set);
                list.removeIf(e -> e.isChecked());
            }
        });
        Button update = new Button("修改");
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                addStage.updateData();
            }
        });
        hBox.getChildren().addAll(add, update, del);

        TableView<ModelData> tableView = new TableView<>(list);
        TableColumn<ModelData,ModelData> selectColumn = new TableColumn<>("选择");

        selectColumn.setCellFactory(new Callback<TableColumn<ModelData, ModelData>, TableCell<ModelData, ModelData>>() {
            @Override
            public TableCell<ModelData, ModelData> call(TableColumn<ModelData, ModelData> param) {
                return new MyCell();
            }
        });
        selectColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ModelData,ModelData>, ObservableValue<ModelData>>() {

            @Override
            public ObservableValue<ModelData> call(TableColumn.CellDataFeatures<ModelData, ModelData> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue());
            }
        });

        TableColumn<ModelData,ModelData> doColumn = new TableColumn<>("操作");

        doColumn.setCellFactory(new Callback<TableColumn<ModelData, ModelData>, TableCell<ModelData, ModelData>>() {
            @Override
            public TableCell<ModelData, ModelData> call(TableColumn<ModelData, ModelData> param) {
                return new MyCell();
            }
        });

        TableColumn<ModelData, String> nameColumn = new TableColumn<>("名字");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<ModelData, String> pkgColumn = new TableColumn<>("包名");
        pkgColumn.setCellValueFactory(new PropertyValueFactory<>("pkg"));
        TableColumn<ModelData, String> authorColumn = new TableColumn<>("作者");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        TableColumn<ModelData, String> pathColumn = new TableColumn<>("路径");
        pathColumn.setCellValueFactory(new PropertyValueFactory<>("path"));

        tableView.getColumns().addAll(selectColumn,nameColumn, pkgColumn, authorColumn,pathColumn,doColumn);

        BorderPane root = new BorderPane();
        root.setTop(hBox);
        root.setCenter(tableView);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class MyCell extends TableCell<ModelData,ModelData> {
        @Override
        protected void updateItem(ModelData item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null){
                setGraphic(null);
            }else {
                CheckBox checkBox = new CheckBox();
                checkBox.setSelected(item.isChecked());
                checkBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        item.setChecked(newValue);
                    }
                });
                setGraphic(checkBox);
            }
        }
    }

}
