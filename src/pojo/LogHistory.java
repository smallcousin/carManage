package pojo;

import java.sql.Date;

public class LogHistory {
    int carNumber;//车牌号
    String brand;//车的品牌
    String idCard;//租主的身份证号
    String userName;//租主的姓名
    String borrowTime;//借车日期
    String returnTime;//归还日期

    public LogHistory(int carNumber, String brand, String idCard, String userName, String borrowTime, String returnTime) {
        this.carNumber = carNumber;
        this.brand = brand;
        this.idCard = idCard;
        this.userName = userName;
        this.borrowTime = borrowTime;
        this.returnTime = returnTime;
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

    public String getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(String borrowTime) {
        this.borrowTime = borrowTime;
    }

    public String getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(String returnTime) {
        this.returnTime = returnTime;
    }
}
