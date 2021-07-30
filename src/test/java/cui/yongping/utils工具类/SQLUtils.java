package cui.yongping.utils工具类;

import cui.yongping.pojo实体类.Member;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author 崔崔
 * @date 2021/7/19-14:36
 * 小崔hello
 */
public class SQLUtils {

    public static Object getSingleResult(String sql) {
        if (StringUtils.isBlank(sql)) {
            return null;
        }
        //1、定义返回值
        Object result = null;
        try {
            //2、创建DBUtils sql语句操作类
            QueryRunner runner = new QueryRunner();
            //3、获取数据库连接
            Connection conn = JDBCUtils.getConnection();
            //4、创建ScalarHandler，针对单行单列的数据
            ScalarHandler handler = new ScalarHandler<>();
            //5、执行sql语句
            result = runner.query(conn, sql, handler);
            //6、关闭数据库连接
            JDBCUtils.close(conn);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void scalarHandler() throws SQLException {
//        QueryRunner runner = new QueryRunner();
//        String sql = "select count(*) from  member where id = 2073699;";
//        Connection conn = JDBCUtils.getConnection();
//        ScalarHandler<Long> handler = new ScalarHandler<>();
//        Long count = runner.query(conn, sql, handler);
//        System.out.println(count);
//        System.out.println(count.getClass());
//        JDBCUtils.close(conn);
        QueryRunner runner = new QueryRunner();
        String sql = "select leave_amount from  member where id = 2073699;";
        Connection conn = JDBCUtils.getConnection();
        ScalarHandler<BigDecimal> handler = new ScalarHandler<>();
        BigDecimal amount = runner.query(conn, sql, handler);
        System.out.println(amount);
        System.out.println(amount.getClass());
        JDBCUtils.close(conn);
    }

    private static void beanListHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from  member limit 5;";
        Connection conn = JDBCUtils.getConnection();
        BeanListHandler<Member> handler = new BeanListHandler<>(Member.class);
        List<Member> list = runner.query(conn, sql, handler);
        for (Member member : list) {
            System.out.println(member);
        }
        JDBCUtils.close(conn);
    }

    private static void beanHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from  member where id = 2073699;";
        Connection conn = JDBCUtils.getConnection();
        BeanHandler<Member> handler = new BeanHandler<>(Member.class);
        Member member = runner.query(conn, sql, handler);
        System.out.println(member);
        System.out.println(member.getId());
        System.out.println(member.getLeave_amount());
        JDBCUtils.close(conn);
    }

    private static void mapHandler() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "select * from  member where id = 2073699;";
        Connection conn = JDBCUtils.getConnection();
        MapHandler handler = new MapHandler();
        Map<String, Object> map = runner.query(conn, sql, handler);
        System.out.println(map);
        JDBCUtils.close(conn);
    }

    private static void insert() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "insert into member values(null,'罗杰2','123456','13211223356',1,10000,NOW());";
        Connection conn = JDBCUtils.getConnection();
        int count = runner.update(conn, sql);
        System.out.println(count);
        JDBCUtils.close(conn);
    }

    private static void update() throws SQLException {
        QueryRunner runner = new QueryRunner();
        String sql = "update member set leave_amount = 500 where id = 2073699;";
        Connection conn = JDBCUtils.getConnection();
        int count = runner.update(conn, sql);
        System.out.println(count);
        JDBCUtils.close(conn);
    }
}