package test;

import dao.CarDao;
import dao.HistoryDao;
import dao.UserDao;
import pojo.Car;
import pojo.LogHistory;
import pojo.User;

import java.sql.Date;
import java.util.List;

public class TestDao {
    public static void main(String[] args) {
//        List<User> lists = new UserDao().getAllUser();
//        for (User list: lists){
//            String a = list.toString();
//            System.out.println(a);
//        }
//        User user = new UserDao().getOneUser(1235);
//        String a = user.toString();
//        System.out.println(a);
//        System.out.println("main OK");
//        Car car = new Car(100, "本田", null, null);
//        Car car2 = new Car(102, "大众", null, null);
//        new CarDao().addCar(car);
//        new CarDao().addCar(car2);
//        new CarDao().delCar(100);
//        new CarDao().changeCar(102,"James","1235");
//        Car car3 = new CarDao().searchOneCar(101);
//        car3.toString();
//        List<Car> list = new CarDao().searchAllCar();
//        System.out.println(list);
        LogHistory log = new LogHistory(101,"丰田","James","1235","2020-10-10",null);
        new HistoryDao().insert(log);
        LogHistory log1 = new LogHistory(101,"丰田","James","1235","2020-10-10","2020-12-12");
        new HistoryDao().insert(log1);

    }
}

