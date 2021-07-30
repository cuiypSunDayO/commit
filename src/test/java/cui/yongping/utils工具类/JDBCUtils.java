package cui.yongping.utils工具类;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 崔崔
 * @date 2021/7/19-14:30
 * 小崔hello
 */
public class JDBCUtils {
    //定义数据库连接
    public static Connection getConnection() {
        //定义数据库连接
        //jdbc:oracle:thin:@127.0.0.1:1521:XE
        //jdbc:sqlserver://localhost:1433;DatabaseName=Java
        String url=Constants.JDBC_URL;
        String user=Constants.JDBC_USER;
        String password=Constants.JDBC_PASSWORD;
        //定义数据库连接对象
        Connection conn = null;
        try {
            //你导入的数据库驱动包， mysql。
            conn = DriverManager.getConnection(url, user,password);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static void close(Connection conn) {
        if(conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}