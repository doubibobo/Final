package TestofSql;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by zhuch on 2017/4/30.
 */
public class test {
    public static void main (String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到MySQL驱动！");
            e.printStackTrace();
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/mysql";
        // 调用DriverManager对象的getConnection()方法，获得一个Connection对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "root", "12151618");
            // 创建一个statement对象
            Statement stmt = conn.createStatement();
            System.out.println("成功连接到数据库！");
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
