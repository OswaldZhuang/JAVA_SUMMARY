package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;


public class UseJdbc {
    
    public void usejdbc() throws Exception{
        //加载数据库驱动
        Class.forName("com.mysql.jdbc.Driver");
        
        //得到数据库连接
        Connection connection = 
                DriverManager.getConnection("db_url");
        //该对象用于执行静态的sql语句
        Statement statement = connection.createStatement();
        
        //Statement执行，返回结果集
        ResultSet resultSet = statement.executeQuery("select *"
                + " from student "
                + "where age > 10 ");
        
        
        
        //PreparedStatement可以预编译sql语句，
        //预编译后的内容存储在该对象中，这样就可以多次执行，提高效率
        //同时，其支持占位符?
        //另一个优点是,PreparedStatement可以防止SQL注入
        PreparedStatement p_Statement = connection.prepareStatement(""
                + "insert into student values(?,22,18)");
        
        p_Statement.setString(0, "zhangsan");
        p_Statement.executeUpdate();
    }

}
