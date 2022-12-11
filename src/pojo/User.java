package pojo;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class User {
    private final SimpleStringProperty userName;//姓名
    private final SimpleStringProperty idCard;//身份证号
    private final SimpleIntegerProperty carNumber;//所租车的车牌号，未租车则默认为-1
    private final SimpleStringProperty brand;//所租车的品牌

    public User( String idCard, String userName, int carNumber, String brand) {
        this.userName = new SimpleStringProperty(userName);
        this.idCard = new SimpleStringProperty(idCard);
        this.carNumber = new SimpleIntegerProperty(carNumber);
        this.brand = new SimpleStringProperty(brand);
    }

    public String getUserName() {
        return this.userName.get();
    }

    public void setUserName(String userName) {
        this.userName.set(userName);
    }

    public String getIdCard() {
        return this.idCard.get();
    }

    public void setIdCard(String idCard) {
        this.idCard.set(idCard);
    }

    public int getCarNumber() {
        return this.carNumber.get();
    }

    public void setCarNumber(int carNumber) {
        this.carNumber.set(carNumber);
    }

    public String getBrand() {
        return this.brand.get();
    }

    public void setBrand(String brand) {
        this.brand.set(brand);
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", carNumber=" + carNumber +
                ", brand='" + brand + '\'' +
                '}';
    }
}
