package pogodynka;

import java.sql.*;
import java.time.LocalDateTime;

public class DataBase {

    private static String connectionUrl = "jdbc:sqlserver://localhost:1433;database=WeatherDB;user=test;password=123456";
    private static String table = "WeatherTable";
    private static Connection connection = null;
    private static Statement statement = null;

    public static void createConnection() {
        try {
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            connection = DriverManager.getConnection(connectionUrl);

        } catch (Exception except) {
            except.printStackTrace();
        }
    }

    public static void insertEntry(int id, DataEntry entry) {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("INSERT INTO " + table + "(ID, CITY, COUNTRY, TEMPERATURE, PRESSURE) values (?, ?, ?, ?, ?)");
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, entry.getCountry()  );
            preparedStatement.setString(3, entry.getCity());
            preparedStatement.setFloat(4,   entry.getTemperature()-273);
            preparedStatement.setFloat(5, entry.getPressure());
            preparedStatement.executeUpdate();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

    public static DataEntry selectEntry() {
        DataEntry entry = new DataEntry();
        try {
            statement = connection.createStatement();
            ResultSet results = statement.executeQuery("SELECT * FROM " + table);

            while (results.next()) {

                String country = results.getString("COUNTRY");
                String city = results.getString("City");
                float temperature = results.getFloat("Temperature");
                float pressure = results.getFloat("Pressure");
               entry = new DataEntry(city, country, temperature, pressure);

            }
            results.close();
            statement.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }

        return entry;
    }
    public static void deleteAllEntries() {
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM " + table);
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }

}