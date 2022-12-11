package dao;

import pojo.Car;
import pojo.User;
import util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDao implements ICarDao{
    public int executeUpdate(String sql, Object... params){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JdbcUtil.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < params.length; i++){
                ps.setObject(i + 1, params[i]);
            }

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("错误断点1");
        }finally {
            JdbcUtil.closeResource(conn, ps,null);
        }

        return 0;
    }
    @Override
    public void addCar(Car car) {
        String sql = "insert into vehicle(carNumber,brand,userName,idCard) values (?,?,?,?)";
        this.executeUpdate(sql,car.getCarNumber(),car.getBrand(),car.getUserName(),car.getIdCard());
        System.out.println("ok");
    }

    @Override
    public void delCar(int CarID) {
        String sql = "delete from vehicle where carNumber = ?";
        this.executeUpdate(sql,CarID);
    }

    @Override
    public void changeCar(int carNum, String userName, String idCard) {
        String sql = "update vehicle set userName=?, idCard=? where carNumber =? ";
        this.executeUpdate(sql,userName,idCard,carNum);
    }

    @Override
    public Car searchOneCar(int carNum) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtil.getConnection();
            String sql = "select * from vehicle where carNumber = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,carNum);
            rs = ps.executeQuery();
            if (rs.next()){
                Car car = new Car(rs.getInt("carNumber"), rs.getString("brand"),
                        rs.getString("userName"),rs.getString("idCard"));
                return car;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("这里是得到一个car的地方,看到这就说明查一条信息这里报错了");
        }finally{
            JdbcUtil.closeResource(conn,ps,rs);
        }
        return null;
    }

    @Override
    public List<Car> searchAllCar() {
        Connection conn =null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.createStatement();
            String sql = "select * from vehicle";
            System.out.println("执行了car查全部");
            rs = st.executeQuery(sql);
            List<Car> list = new ArrayList<>();
            while (rs.next()){
                Car car = new Car(rs.getInt("carNumber"), rs.getString("brand"),
                        rs.getString("userName"),rs.getString("idCard"));
                list.add(car);
            }
            return list;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JdbcUtil.closeResource(conn,st,rs);
        }
        return null;
    }

}
