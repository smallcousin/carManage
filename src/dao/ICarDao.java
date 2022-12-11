package dao;
import pojo.Car;

import java.util.List;

public interface ICarDao {
    //增
    public void addCar(Car car);

    //删
    public void delCar(int CarID);

    //改  包括换车，借车
    public void changeCar(int carNum, String userName, String idCard);

    //查
    public Car searchOneCar(int carNum);

    public List<Car>  searchAllCar();
}
