package Tools;

import java.sql.*;

/**
 * Created by zhuch on 2017/4/30.
 */
public class find {
    private static String url = null;          //连接目的地址
    private static Connection connection = null;   //创建连接对象
    private static Statement statement = null;               //基本连接语句
    private static ResultSet resultSet = null;                 //查询结果
    private static int rows = 0;                                // 受影响的结果
    private static String username = null;             //数据库用户名
    private static String password = null;             //数据库密码
    private static String sql = null;                  //查询语句

    /**
     * 方法功能：连接基本的数据库
     */
    public static void setValue() {
        url = "jdbc:mysql://localhost:3306/test";
        username = "doubibobo";
        password = "12151618";
        try {
            connection = DriverManager.getConnection(url, username, password);
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法功能：检查用户是否已经注册
     * @param uname
     * @param pwd
     * @return
     */
    public static boolean checkLogin(String uname, String pwd) {
        String databasepwd = null;      //数据库中密码
        setValue();
        sql = "select * from user where user_name = '"+ uname+"'";
        try {
            resultSet  = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    databasepwd = resultSet.getString(3);
                    if (pwd.equals(databasepwd)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 方法功能：获取性别
     * @param theName
     * @return
     */
    public static String getSex(String theName) {
        int databasesex = 0;
        String sex = null;
        setValue();
        sql = "select * from user where user_name = '"+ theName+"'";
        try {
            resultSet  = statement.executeQuery(sql);
            if (resultSet != null) {
                while (resultSet.next()) {
                    databasesex = resultSet.getInt(4);
                    if (databasesex == 0) {
                        sex = "男";
                    } else if (databasesex == 1){
                        sex = "女";
                    } else {
                        sex = "保密";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sex;
    }

    /**
     * 验证是否已经注册过
     * @param theName
     * @param sex
     * @param userpassword
     * @return
     */
    public static boolean checkItems(String theName, String sex, String userpassword) {
        setValue();
        sql = "select * from user where user_name = '"+ theName+"'";
        try {
            resultSet  = statement.executeQuery(sql);
            System.out.println(resultSet.getRow());
            if (resultSet.next() == false) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 方法功能：数据库中添加一个用户
     * @param theName
     * @param sex
     * @param userpassword
     * @return
     */
    public static boolean addUser(String theName, String sex, String userpassword) {
        // 首先对性别进行调整，男为0，女为1，保密为2
        int theSex = 2;
        int count;
        switch (sex) {
            case "boy": theSex = 0;break;
            case "girl": theSex = 1;break;
            case "unknown": theSex = 2;break;
            default:return false;
        }
        sql = "SELECT COUNT(user_id) FROM user";
        try {
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                count = resultSet.getInt(1);
                count++;
                sql = "INSERT INTO user VALUES (" + count + ", '" + theName + "', '"+ userpassword + "', " + theSex +")";
                try {
                    rows = statement.executeUpdate(sql);
                    if (rows != 0) {
                        return true;
                    } else {
                        return false;
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
