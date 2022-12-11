package dao;

import pojo.Car;
import pojo.LogHistory;
import util.JdbcUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HistoryDao {
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
    public List<LogHistory> searchHisAll(){
        Connection conn = null;
        Statement st = null;
        ResultSet rs = null;
        try{
            conn = JdbcUtil.getConnection();
            st = conn.createStatement();
            String sql = "select * from history";
            System.out.println("执行了history查全部");
            rs = st.executeQuery(sql);
            List<LogHistory> list = new ArrayList<>();
            while (rs.next()){
                LogHistory logHistory = new LogHistory(rs.getInt("carNumber"), rs.getString("brand"),
                        rs.getString("userName"),rs.getString("idCard"),
                        rs.getString("borrowTime"),rs.getString("returnTime"));
                list.add(logHistory);
            }
            return list;
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void insert(LogHistory log){
        String sql = "insert into history(carNumber,brand,userName,idCard,borrowTime,returnTime) values (?,?,?,?,?,?)";
        this.executeUpdate(sql,log.getCarNumber(),log.getBrand(),log.getUserName()
        ,log.getIdCard(),log.getBorrowTime(),log.getReturnTime());

    }
}
