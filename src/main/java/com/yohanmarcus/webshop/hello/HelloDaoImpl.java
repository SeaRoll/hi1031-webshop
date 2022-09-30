package com.yohanmarcus.webshop.hello;

import com.yohanmarcus.webshop.util.DatabaseConfig;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class HelloDaoImpl implements HelloDao {

  private final DataSource dataSource;

  public HelloDaoImpl() {
    dataSource = DatabaseConfig.getDataSource();
  }

  @Override
  public List<Hello> findAll() {
    Connection conn = null;
    try {
      conn = dataSource.getConnection();
      conn.setAutoCommit(false);

      Statement stmt = conn.createStatement();
      ResultSet rs = stmt.executeQuery("SELECT * FROM T_SOMETABLE");
      List<Hello> hellos = new ArrayList<>();
      while (rs.next()) {
        int id = rs.getInt("id");
        hellos.add(new Hello(id));
      }
      conn.commit();
      return hellos;
    } catch (SQLException e) {
      try {
        assert conn != null;
        conn.rollback();
      } catch (SQLException ex) {
        throw new RuntimeException(ex);
      }
      throw new RuntimeException(e);
    } finally {
      try {
        assert conn != null;
        conn.close();
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    }
  }
}
