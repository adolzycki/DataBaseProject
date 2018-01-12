import Exceptions.MissingFromParameterException;
import Exceptions.MissingSelectParameterException;
import Exceptions.WrongParametersException;
import Exceptions.WrongTableExceptions;
import com.mysql.jdbc.MysqlDataTruncation;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Arrays;
import java.util.Vector;


public class JDBC {
    Connection connection;
    Statement statement;
    CallableStatement callableStatement;
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/szkola_projekt";



    public static JDBC connect(String username, String password) throws SQLException {

        JDBC jdbc = new JDBC();

        try {
            Class.forName(JDBC_DRIVER);
            jdbc.connection = DriverManager.getConnection(DB_URL,username,password);
        }
        catch( ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return jdbc;
    }

    public void createQuerry(String select, String from, String where,String group) throws WrongTableExceptions,
            MissingSelectParameterException, MissingFromParameterException {

        if(select.equals(""))
            throw new MissingSelectParameterException();

        if(from.equals(""))
            throw new MissingFromParameterException();

        String[] tables = new String[]{"Tytul","Klasa","Przedmiot","Nauczyciel","Nieobecnosc","Ocena","Test","Uczen",
                "informacje_klasy","informacje_klasy_km", "przedmioty_ilosc_nauczycieli", "roknauki_ilosc_uczniow", "uczniowie_najwiecej_nieobecnosci"};

        if(!Arrays.asList(tables).contains(from))
            throw new WrongTableExceptions();

        String sql = "SELECT " + select + " FROM " + from;

        if(!where.equals(""))
            sql = sql.concat(" WHERE ") + where;

        if(!group.equals(""))
            sql = sql.concat(" GROUP BY ") +group;

        try {
            statement = connection.createStatement();

            ResultSet rs = statement.executeQuery(sql);

            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table),"Answer",JOptionPane.INFORMATION_MESSAGE);
        }
        catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }

    public void createCall(String callName,String value1,String value2, String value3) throws WrongParametersException {

        try {
            String sql = "{call " + callName + " ( ";
            if(!value1.equals(""))
                sql = sql.concat("? ");
            if(!value2.equals(""))
                sql = sql.concat(",? ");
            if(!value3.equals(""))
                sql = sql.concat(",? ");

            sql = sql.concat(")}");
            callableStatement = connection.prepareCall(sql);

            if(!value1.equals(""))
                callableStatement.setString(1,value1);
            if(!value2.equals(""))
                callableStatement.setString(2,value2);
            if(!value3.equals(""))
                callableStatement.setString(3,value3);

            callableStatement.execute();
            ResultSet rs = callableStatement.getResultSet();
            JTable table = new JTable(buildTableModel(rs));
            JOptionPane.showMessageDialog(null, new JScrollPane(table),"Answer",JOptionPane.INFORMATION_MESSAGE);

        }
        catch (MysqlDataTruncation ex ) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

        catch (SQLException ex) {
            throw  new WrongParametersException();
        }
        catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null,"Success","Alert",JOptionPane.INFORMATION_MESSAGE);
        }


    }


    public void update(String update, String set, String where) {

        String sql = "UPDATE " + update + " SET " + set + " WHERE " + where;
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    public void insert(String insert, String value) {

        String sql = "INSERT INTO " + insert + " VALUES (" + value +")";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }

    }

    public void delete(String from, String where) {

        String sql = "DELETE FROM " + from + " WHERE " +where;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage(),"Error",JOptionPane.ERROR_MESSAGE);
        }
    }


    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException, NullPointerException {
        ResultSetMetaData metaData = rs.getMetaData();

        // names of columns
        Vector<String> columnNames = new Vector<String>();
        int columnCount = metaData.getColumnCount();
        for (int column = 1; column <= columnCount; column++) {
            columnNames.add(metaData.getColumnName(column));
        }

        // data of the table
        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) {
                vector.add(rs.getObject(columnIndex));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);

    }
}
