package dao;

import pojo.User;
import util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao implements IUserDao{
    public int executeUpdate(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 1.加载驱动
            // 2.连接数据库
            conn = JdbcUtil.getConnection();
            // 3.创建语句
            ps = conn.prepareStatement(sql);
            // 遍历参数
            for (int i = 0; i < params.length; i++) {
                // ps.setString(1, stu.getName());
                // ps.setInt(2, stu.getAge());
                ps.setObject(i + 1, params[i]);

            }
            // 4.执行语句
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("打个断点1");
        } finally {
            // 5.释放资源
//            try {
//                ps.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//                System.out.println("打个断点2");
//            }
//            JdbcUtil.closeResource(conn);
                JdbcUtil.closeResource(conn,ps,null);
        }
        return 0;
    }
    @Override
    public void addUser(User user) {
        String sql = "insert into user(idCard,userName,carNumber,brand) values (?,?,?,?)";
        this.executeUpdate(sql,user.getIdCard(),user.getUserName(),user.getCarNumber(),user.getBrand());
        System.out.println("ok");
    }

    @Override
    public void updateUser(int userID, int carNum,String brand) {
        String sql = "update user set carNumber=?, brand=? where idCard =? ";
        this.executeUpdate(sql,carNum,brand,userID);
    }

    @Override
    public void delete(int userID) {
        String sql = "delete from user where idCard = ?";
        this.executeUpdate(sql,userID);
    }

    @Override
    public User getOneUser(int userID) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtil.getConnection();
            String sql = "select * from user where idCard = ?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1,userID);
            rs = ps.executeQuery();
            if (rs.next()){
                User user = new User(rs.getString("idCard"), rs.getString("userName"),
                        rs.getInt("carNumber"),rs.getString("brand"));
                return user;
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("这里是得到一个user的地方");
        }finally{
            JdbcUtil.closeResource(conn,ps,rs);
        }
        return null;
    }

    @Override
    public List<User> getAllUser() {
        Connection conn =null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.createStatement();
            String sql = "select * from user";
            System.out.println("执行了user查全部");
            rs = st.executeQuery(sql);
            List<User> list = new ArrayList<>();
            while (rs.next()){
                User user = new User(rs.getString("idCard"), rs.getString("userName"),
                        rs.getInt("carNumber"),rs.getString("brand"));
                list.add(user);
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
