package com.magnit;

/**
 * Created by Oleg Letunovskij on 11.08.2016.
 */

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class JdbcMain {

    private String dbConnection;
    private String userName;
    private String password;
    private int count;

    public String getDbConnection() {
        return dbConnection;
    }

    public void setDbConnection(String dbConnection) {
        this.dbConnection = dbConnection;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    JdbcMain() {
        this.dbConnection = DatabaseConfig.DB_CONNECTION;
        this.userName = DatabaseConfig.DB_USER;
        this.password = DatabaseConfig.DB_PASSWORD;
        this.count = 100;
    }

    JdbcMain(String con, String usr, String pass, int cnt) {
        this.dbConnection = con;
        this.userName = usr;
        this.password = pass;
        this.count = cnt;
    }

    public void execute() {
        long startTime = System.currentTimeMillis();
        try {
            try {
                this.query("DELETE FROM test;");
            } catch (Exception ex) {
                ex.printStackTrace();
            }

            // заполнить таблицу в базе данных
            try {
                this.query(prepareSql());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            // извлечь данные и сформировать xml document
            XmlBuilder.generateXml(this.extractFields());


            // преобразовать xml документ
            XmlBuilder.transformXslt();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);
    }

    private Connection createConnection() {
        Connection con = null;
        try {
            Class.forName(DatabaseConfig.DB_DRIVER);
            con = DriverManager.getConnection(this.dbConnection, this.userName, this.password);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return con;
    }

    /**
     * Выполнить запрос в БД
     * @param sql
     */
    private void query(String sql) {
        ResultSet rs = null;
        Statement stmt = null;
        Connection con = createConnection();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery(sql);
        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Извлечь записи field из таблицы Test
     * @return
     */
    private List<Integer> extractFields() {

        LinkedList<Integer> ls = new LinkedList<>();
        ResultSet rs = null;
        Statement stmt = null;
        Connection con = createConnection();

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT field FROM test;");
            while (rs.next()) {
                ls.add(rs.getInt("field"));
            }

        } catch(SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                rs.close();
                stmt.close();
                con.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return ls;
    }

    /**
     * Вставить записи в БД максимально эффективно (batch, preparedStatement)
     * @return
     */
    private String prepareSql() {
        StringBuilder insertInTable = new StringBuilder("INSERT INTO test(FIELD) VALUES");
        for (int i=0; i < this.getCount(); i++) {
            insertInTable.append("(");
            insertInTable.append(String.valueOf(i));
            insertInTable.append("),");
        }
        insertInTable.deleteCharAt(insertInTable.length() - 1);
        insertInTable.append(';');
        return insertInTable.toString();
    };
}

final class DatabaseConfig {
    public static final String DB_DRIVER = "org.postgresql.Driver";
    public static final String DB_CONNECTION = "jdbc:postgresql://localhost:5432/test_";
    public static final String DB_USER = "postgres";
    public static final String DB_PASSWORD = "postgres";
}
