package controller;

import dao.CarDao;
import dao.HistoryDao;
import dao.UserDao;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import pojo.Car;
import pojo.LogHistory;
import pojo.User;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class mainController implements Initializable {

    @FXML
    private TableColumn CcarBrandT;

    @FXML
    private TableColumn CcarIDT;

    @FXML
    private TableColumn CnowUser;

    @FXML
    private TableColumn CnowUserID;

    @FXML
    private TableColumn LcarB;

    @FXML
    private TableColumn LcarID;

    @FXML
    private TableColumn LuserBT;

    @FXML
    private TableColumn LuserID;

    @FXML
    private TableColumn LuserN;

    @FXML
    private TableColumn LuserRT;

    @FXML
    private TableColumn UubCarB;

    @FXML
    private TableColumn UubCarID;

    @FXML
    private TableColumn  UuserIDT;

    @FXML
    private TableColumn  UuserNT;

    @FXML
    private TableView carTableView;

    @FXML
    private TableView<User> userTableView;

    @FXML
    private SplitPane splitPane;

    @FXML
    private TableView logTableView;

    @FXML
    private Tab tab_car;

    @FXML
    private Tab tab_log;

    @FXML
    private Tab tab_user;

    @FXML
    void addCar(ActionEvent event) {
        Dialog<NewCar> dialog = new Dialog<>();
        dialog.setTitle("公司买入新车");
        dialog.setHeaderText("请输入新车ID和品牌");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,60,10,10));

        TextField carId = new TextField();
        TextField carBrand = new TextField();

        gridPane.add(new Label("新车ID:"), 0, 0);
        gridPane.add(carId,1,0);
        gridPane.add(new Label("品牌:"), 0, 1);
        gridPane.add(carBrand,1,1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new NewCar(carId.getText(),carBrand.getText());
            }
            return null;
        });

        Optional<NewCar> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((NewCar results) -> {
            Car car = new CarDao().searchOneCar(Integer.parseInt(results.carID));
            if(car!= null){
                alert("错误提示","ID为【" + results.carID + "】的车已经存在，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new CarDao().addCar(new Car(Integer.parseInt(results.carID), results.carBrand, null,null));

                alert("成功提示","成功保存ID为【" + results.carID + "】的车辆数据！",null, Alert.AlertType.INFORMATION);
                refreshCarTable(); // 刷新界面
            }
        });
    }
    private static class NewCar{
        private String carID;
        private String carBrand;
        public NewCar(String carID, String carBrand){
            this.carID = carID;
            this.carBrand = carBrand;
        }
    }
    /**
     * 弹框
     */
    private void alert(String title, String content, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }



    @FXML
    void addUser(ActionEvent event) {
        Dialog<NewUser> dialog = new Dialog<>();
        dialog.setTitle("新用户开始使用产品");
        dialog.setHeaderText("请输入新用户ID和姓名");

        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,60,10,10));

        TextField userId = new TextField();
        TextField userName = new TextField();

        gridPane.add(new Label("新用户ID:"), 0, 0);
        gridPane.add(userId,1,0);
        gridPane.add(new Label("姓名:"), 0, 1);
        gridPane.add(userName,1,1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new NewUser(userId.getText(),userName.getText());
            }
            return null;
        });

        Optional<NewUser> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((NewUser results) -> {
            User user = new UserDao().getOneUser(Integer.parseInt(results.userID));
            if(user!= null){
                alert("错误提示","ID为【" + results.userID + "】的用户已经存在，无法添加！",null, Alert.AlertType.ERROR);
            }else{
                // 保存信息到数据库
                new UserDao().addUser(new User(results.userID, results.userName, 0,null));

                alert("成功提示","成功保存ID为【" + results.userID + "】的用户数据！",null, Alert.AlertType.INFORMATION);
                refreshUserTable(); // 刷新界面
            }
        });
    }
    private static class NewUser{
        private String userID;
        private String userName;
        public NewUser(String userID, String userName){
            this.userID = userID;
            this.userName = userName;
        }
    }

    @FXML
    void carDelete(ActionEvent event) {

        Dialog<String> dialog = new Dialog<>();
        dialog.setHeaderText("请输入要回收的车辆ID");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,60,10,10));

        TextField carID = new TextField();
        gridPane.add(new Label("汽车ID"),0,0);
        gridPane.add(carID, 1,0);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK){
                return carID.getText();
            }
            return null;
        });

        Optional<String> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((String results) -> {
            Car car = new CarDao().searchOneCar(Integer.parseInt(results));
            if(car == null){
                alert("错误提示","ID为【" + results + "】的车不存在，无法回收！",null, Alert.AlertType.ERROR);
            }else if(car.getIdCard() != null){
                alert("错误提示","ID为【" + results + "】的车还有用户使用，无法回收！",null, Alert.AlertType.ERROR);
            } else{
                // 保存信息到数据库
                new CarDao().delCar(Integer.parseInt(results));

                alert("成功提示","成功回收ID为【" + results+ "】的汽车，已删除信息！",null, Alert.AlertType.INFORMATION);
                refreshCarTable(); // 刷新界面
            }
        });

    }



    @FXML
    void userBorrowCar(ActionEvent event){
        Dialog<ID> dialog = new Dialog<>();
        dialog.setHeaderText("请输入用户ID和要借的汽车ID");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,60,10,10));

        TextField userID = new TextField();
        TextField carID = new TextField();
        TextField borrowTime = new TextField();
        gridPane.add(new Label("输入用户ID"),0,0);
        gridPane.add(userID, 1,0);
        gridPane.add(new Label("输入汽车ID"),0,1);
        gridPane.add(carID, 1,1);
        gridPane.add(new Label("输入租借时间"),0,2);
        gridPane.add(borrowTime, 1,2);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK){
                return new ID(userID.getText(),carID.getText());
            }
            return null;
        });

        Optional<ID> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((ID results) -> {
            User user = new UserDao().getOneUser(Integer.parseInt(results.userID));
            Car car = new CarDao().searchOneCar(Integer.parseInt(results.carID));
            if(user == null || car == null) {
                alert("错误提示","输入ID中有ID不存在",null, Alert.AlertType.ERROR);
            }else if(user.getBrand() != null){
                alert("错误提示","ID为【" + results.userID + "】的用户还在进行租车服务！",null, Alert.AlertType.ERROR);
            } else if(car.getUserName() != null){
                alert("错误提示","ID为【" + results.carID + "】的汽车在被其他用户租借！",null, Alert.AlertType.ERROR);
            } else{
                // 保存信息到数据库
                new UserDao().updateUser(Integer.parseInt(results.userID),car.getCarNumber(), car.getBrand());
                new CarDao().changeCar(car.getCarNumber(),user.getUserName(),user.getIdCard());
                new HistoryDao().insert(new LogHistory(car.getCarNumber(),car.getBrand(),user.getIdCard(),user.getUserName(),borrowTime.getText(),null));
                alert("成功提示","Id为【" + results.userID +
                        "】的用户成功租到ID为【" + results.userID +"】的车",null, Alert.AlertType.INFORMATION);
                refreshUserTable(); // 刷新界面
                refreshCarTable();
                refreshHisTable();
            }
        });
    }
    private static class ID{
        private String userID;
        private String carID;
        public ID(String userID,String carID){
            this.carID = carID;
            this.userID = userID;
        }
    }

    @FXML
    void userReturnCar(ActionEvent event){
        Dialog<User> dialog = new Dialog<>();
        dialog.setHeaderText("请输入还车用户的ID");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,60,10,10));

        TextField userID = new TextField();
        TextField returnTime = new TextField();
        gridPane.add(new Label("输入用户ID"),0,0);
        gridPane.add(userID, 1,0);
        gridPane.add(new Label("归还时间"),0,1);
        gridPane.add(returnTime, 1,1);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK){
                return new UserDao().getOneUser(Integer.parseInt(userID.getText()));
            }
            return null;
        });

        Optional<User> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((User results) -> {
            Car car = new CarDao().searchOneCar(results.getCarNumber());
            if (results.getBrand() == null){
                alert("错误提示","改用户HI还未租车",null, Alert.AlertType.ERROR);
            } else {
                // 保存信息到数据库
                new UserDao().updateUser(Integer.parseInt(results.getIdCard()), 0, null);
                new CarDao().changeCar(car.getCarNumber(), null, null);
                new HistoryDao().insert(new LogHistory(car.getCarNumber(), car.getBrand(), results.getIdCard(), results.getUserName(), null, returnTime.getText()));
                alert("成功提示", "Id为【" + results.getIdCard() +
                        "】的用户成功归还ID为【" + car.getCarNumber() + "】的车", null, Alert.AlertType.INFORMATION);
                refreshUserTable(); // 刷新界面
                refreshCarTable();
                refreshHisTable();
            }
        });
    }

    //删除用户
    @FXML
    void deleteCourse(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setHeaderText("请输入要清除的用户ID");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20,60,10,10));

        TextField userID = new TextField();
        gridPane.add(new Label("用户ID"),0,0);
        gridPane.add(userID, 1,0);

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK){
                return userID.getText();
            }
            return null;
        });

        Optional<String> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((String results) -> {
            User user = new UserDao().getOneUser(Integer.parseInt(results));
            if(user == null){
                alert("错误提示","ID为【" + results + "】的用户不存在，无法拉黑！",null, Alert.AlertType.ERROR);
            }else if(user.getIdCard() != null){
                alert("错误提示","ID为【" + results + "】的用户还在进行租车服务，无法拉黑！",null, Alert.AlertType.ERROR);
            } else{
                // 保存信息到数据库
                new UserDao().delete(Integer.parseInt(results));

                alert("成功提示","成功拉黑ID为【" + results+ "】的用户，已删除信息！",null, Alert.AlertType.INFORMATION);
                refreshUserTable(); // 刷新界面
            }
        });
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshUserTable();
        refreshCarTable();
        refreshHisTable();
        //设置表格与按钮对应
        setTabVisible(tab_car,carTableView);
        setTabVisible(tab_user,userTableView);
        setTabVisible(tab_log,logTableView);
    }

    /**
     * 三个更新函数，用于各个逻辑中进行表格更新
     */
    private void refreshUserTable(){
        UuserIDT.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        UuserNT.setCellValueFactory(new PropertyValueFactory<>("userName"));
        UubCarID.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
        UubCarB.setCellValueFactory(new PropertyValueFactory<>("brand"));
        List<User> users = new UserDao().getAllUser();
        ObservableList<User> data = FXCollections.observableArrayList();
        for (User user : users) {
            data.add(user);
            // System.out.println(students);
        }
        userTableView.setItems(data);
//        userTableView.getColumns().addAll(UuserIDT,UuserNT,UubCarID,UubCarB);
    }
    private void refreshCarTable(){
        CcarIDT.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
        CcarBrandT.setCellValueFactory(new PropertyValueFactory<>("brand"));
        CnowUser.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        CnowUserID.setCellValueFactory(new PropertyValueFactory<>("userName"));
        List<Car> cars = new CarDao().searchAllCar();
        ObservableList<Car> data = FXCollections.observableArrayList();
        for (Car car : cars){
            data.add(car);
        }
        carTableView.setItems(data);
    }
    private void refreshHisTable(){
        LcarID.setCellValueFactory(new PropertyValueFactory<>("carNumber"));
        LcarB.setCellValueFactory(new PropertyValueFactory<>("brand"));
        LuserN.setCellValueFactory(new PropertyValueFactory<>("idCard"));
        LuserID.setCellValueFactory(new PropertyValueFactory<>("userName"));
        LuserBT.setCellValueFactory(new PropertyValueFactory<>("borrowTime"));
        LuserRT.setCellValueFactory(new PropertyValueFactory<>("returnTime"));
        List<LogHistory> logs = new HistoryDao().searchHisAll();
        ObservableList<LogHistory> data = FXCollections.observableArrayList();
        for (LogHistory log : logs){
            data.add(log);
        }
        logTableView.setItems(data);
    }
    /**
     * Task接口
     */
    private interface Task {
        void execute();
    }
    /**
     * 点击跳转的函数
     */
    private void setTabVisible(Tab tab, TableView tableView){
        setTabAction(tab, new Task() {
            @Override
            public void execute() {
                if(tableView.equals(carTableView)){
                    userTableView.setVisible(false);
                    carTableView.setVisible(true);
                    logTableView.setVisible(false);
                }
                else if(tableView.equals(userTableView)){
                    carTableView.setVisible(false);
                    userTableView.setVisible(true);
                    logTableView.setVisible(false);
                }
                else if(tableView.equals(logTableView)){
                    carTableView.setVisible(false);
                    userTableView.setVisible(false);
                    logTableView.setVisible(true);
                }
            }
        });
    }
    /**
     * 事件监听绑定
     * @param tab
     * @param task
     */
    private void setTabAction(Tab tab, Task task) {
        tab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                task.execute();
            }
        });
    }
}
