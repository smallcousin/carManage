package pojo;

public class Car {
    int carNumber;//车牌号
    String brand;//车的品牌
    String idCard;//租主的身份证号
    String userName;//租主的姓名

    public Car(int carNumber, String brand, String idCard, String userName) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.idCard = idCard;
        this.userName = userName;
    }

    public int getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(int carNumber) {
        this.carNumber = carNumber;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carNumber=" + carNumber +
                ", brand='" + brand + '\'' +
                ", idCard='" + idCard + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
