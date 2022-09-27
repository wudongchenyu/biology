package pers.wdcy.pool;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PoolTest {

	private static HikariConfig buildConfig (){
        HikariConfig hikariConfig = new HikariConfig() ;
        // 基础配置
        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/junit_test?characterEncoding=utf8");
        // 连接池配置
        hikariConfig.setPoolName("dev-hikari-pool");
        hikariConfig.setMinimumIdle(4);
        hikariConfig.setMaximumPoolSize(8);
        return hikariConfig ;
    }
    public static void main(String[] args) throws Exception {
        // 构建数据源
        HikariDataSource dataSource = new HikariDataSource(buildConfig()) ;
        // 获取连接
        Connection connection = dataSource.getConnection() ;
        // 声明SQL执行
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT count(1) num FROM jt_activity") ;
        // 输出执行结果
        if (resultSet.next()) {
            System.out.println("query-count-result："+resultSet.getInt("num"));
        }
    }

}
