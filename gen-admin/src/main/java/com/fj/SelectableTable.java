package com.fj;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author fjding
 * @date 2022/11/13
 */
public class SelectableTable extends Application {
    @Override
    public void start(Stage primaryStage) {
        TableView<Item> itemTable = new TableView<>();
        for (int i=1; i<=40; i++) {
            itemTable.getItems().add(new Item("Item "+i));
        }
        TableColumn<Item, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));


        TableColumn<Item, Item> selectedCol = new TableColumn<>("Select");

        // Collection of items currently selected via checkboxes in the table
        // This will be passed to the TableCell implementation.
        ObservableSet<Item> selectedItems = FXCollections.observableSet();

        selectedCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Item,Item>, ObservableValue<Item>>() {

            @Override
            public ObservableValue<Item> call(TableColumn.CellDataFeatures<Item, Item> data) {
                return new ReadOnlyObjectWrapper<>(data.getValue());
            }
        });

        selectedCol.setCellFactory(new Callback<TableColumn<Item, Item>, TableCell<Item, Item>>() {

            @Override
            public TableCell<Item, Item> call(
                    TableColumn<Item, Item> param) {
                return new CheckBoxCell(selectedItems);
            }
        });

        itemTable.getColumns().addAll(selectedCol, nameCol);

        Button displayButton = new Button("Display selected");
        displayButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Item item : selectedItems) {
                    System.out.println(item.getName());
                }
            }
        });

        Button selectAllButton = new Button("Select all");
        selectAllButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectedItems.addAll(itemTable.getItems());
            }
        });

        Button selectNoneButton = new Button("Select none");
        selectNoneButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                selectedItems.clear();
            }
        });

        HBox buttons = new HBox(5);
        buttons.getChildren().addAll(selectAllButton, selectNoneButton, displayButton);

        BorderPane root = new BorderPane();
        root.setCenter(itemTable);
        root.setBottom(buttons);

        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static class CheckBoxCell extends TableCell<Item, Item> {

        private final ObservableSet<Item> selectedItems ;
        private final CheckBox checkBox ;

        public CheckBoxCell(ObservableSet<Item> selectedItems) {
            this.selectedItems = selectedItems ;
            this.checkBox = new CheckBox() ;


            // listener to update the set of selected items when the
            // check box is checked or unchecked:
            checkBox.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (checkBox.isSelected()) {
                        selectedItems.add(getItem());
                    } else {
                        selectedItems.remove(getItem());
                    }
                }
            });

            // listener to update the check box when the collection of selected
            // items changes:
            selectedItems.addListener(new SetChangeListener<Item>() {

                @Override
                public void onChanged(Change<? extends Item> change) {
                    Item item = getItem();
                    if (item != null) {
                        checkBox.setSelected(selectedItems.contains(item));
                    }
                }

            });
        }

        @Override
        public void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                checkBox.setSelected(selectedItems.contains(item));
                setGraphic(checkBox);
            }
        }
    }

    public static class Item {
        private final StringProperty name = new SimpleStringProperty(this, "name");
        public StringProperty nameProperty() {
            return name ;
        }
        public final String getName() {
            return name.get();
        }
        public final void setName(String name) {
            this.name.set(name);
        }
        public Item(String name) {
            setName(name);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
