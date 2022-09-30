package com.yohanmarcus.webshop.database;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Använder oss av database config som en slags "singleton" för att ha en enda connection pool.
 * Detta är okej eftersom vi använder inte oss av datasource connections, utan Connection pools som
 * finns inuti DataSource. Även om vi använder oss av en JNDI metod istället så är det också en
 * singleton så det kvittar. Detta är en mer programmatisk approach.
 */
public class DatabaseConfig {

  private static String URL = "jdbc:postgresql://db:5432/postgres";
  private static final String DRIVER_CLASS = "org.postgresql.Driver";
  private static final String DATABASE_NAME = "postgres";
  private static final String DATABASE_PASSWORD = "mysecretpassword";
  private static DataSource dataSource = null;

  public DatabaseConfig() {}

  public static void setURL(String URL) {
    DatabaseConfig.URL = URL;
  }

  private static void buildDataSource() {
    PoolProperties p = new PoolProperties();
    p.setUrl(URL);
    p.setDriverClassName(DRIVER_CLASS);
    p.setUsername(DATABASE_NAME);
    p.setPassword(DATABASE_PASSWORD);

    dataSource = new DataSource(p);
    dataSource.setPoolProperties(p);
  }

  /**
   * Returns active datasource. if not, return a new datasource
   *
   * @return a datasource
   */
  public static DataSource getDataSource() {
    if (dataSource == null) {
      buildDataSource();
    }
    return dataSource;
  }

  public static Connection getConnection(Connection optionalConn) throws SQLException {
    return optionalConn == null ? getDataSource().getConnection() : optionalConn;
  }

  public static void closeConnection(Connection conn, Connection optionalConn) throws SQLException {
    if (optionalConn == null) {
      assert conn != null;
      conn.close();
    }
  }
}
