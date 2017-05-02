package TestofSql;

import java.sql.*;

/**
 * Created by zhuch on 2017/4/30.
 */
public class selectTable {
    public static void main (String args[]) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("成功加载MySQL驱动");
        } catch (ClassNotFoundException e) {
            System.out.println("找不到mysql驱动");
            e.printStackTrace();
        }

        // 连接数据库管理工具MySQL中的test数据库
        String url = "jdbc:mysql://localhost:3306/test";
        // 调用DirverManager对象的一个getConnection()方法，创建一个连接对象
        Connection conn;
        try {
            conn = DriverManager.getConnection(url, "doubibobo", "12151618");

            Statement stmt = conn.createStatement();
            System.out.println("成功连接到数据库");

            String sql = "select * from basic";
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("学号"+"\t" + "\t" +"姓名");

            while (rs.next()) {
                System.out.print(rs.getInt(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.println();
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
