package util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtil {
    private static DataSource source;
    static{
        try {
            Properties pros = new Properties();

            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("druid.properties");

            pros.load(is);

            source = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("断点3");
        }
    }
    public static Connection getConnection() throws SQLException {

        Connection conn = source.getConnection();
        return conn;

    }

    /**
     * 使用dbutils.jar中提供的DbUtils工具类，实现资源的关闭
     * @param conn
     *
     */
    public static void closeResource(Connection conn,Statement st,ResultSet rs){
//        DbUtils.closeQuietly(conn);
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {

                e.printStackTrace();
            }
        }
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

}
