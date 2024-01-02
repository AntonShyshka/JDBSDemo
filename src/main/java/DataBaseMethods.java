import java.sql.*;
import java.util.Scanner;

public class DataBaseMethods {
    protected static void initDB() throws SQLException {
        Statement st = DataBaseInterface.conn.createStatement();
        try {
            st.execute("USE Apartments;");
            /*st.execute("CREATE TABLE ApartmentsTable" +
                    "(id INT AUTO_INCREMENT NOT NULL PRIMARY KEY," +
                    "district VARCHAR(15) NOT NULL," +
                    "address VARCHAR(15) NOT NULL," +
                    "square INT NOT NULL," +
                    "number_of_rooms INT NOT NULL," +
                    "price INT NOT NULL);");
            st.execute("USE ApartmentsTable"); */
        } finally {
            st.close();
        }
    }

    protected static void addApartment(Scanner scanner) throws SQLException {
        System.out.println("Enter apartment district:");
        String district = scanner.nextLine();
        System.out.println("Enter address apartment:");
        String address = scanner.nextLine();
        System.out.println("Enter square of apartment:");
        int square = scanner.nextInt();
        System.out.println("Enter number of rooms");
        int numberOfRooms = scanner.nextInt();
        System.out.println("Enter price:");
        int price = scanner.nextInt();

        PreparedStatement ps = DataBaseInterface.conn.prepareStatement("INSERT INTO ApartmentsTable" +
                "(district, address, square, number_of_rooms, price)" +
                "values " +
                "(?, ?, ?, ?, ?)");
        try {
            ps.setString(1, district);
            ps.setString(2, address);
            ps.setInt(3, square);
            ps.setInt(4, numberOfRooms);
            ps.setInt(5, price);
            ps.executeUpdate();
        } finally {
            ps.close();
        }
    }

    protected static void selectApartments(Scanner scanner) throws SQLException {
        System.out.println("Choose id of apartment for selection: ");
        int id = scanner.nextInt();

        PreparedStatement ps = DataBaseInterface.conn.prepareStatement("SELECT * FROM apartmentstable WHERE id = ?");
        try {
            ResultSet rs = ps.executeQuery();
          try {
              ResultSetMetaData md = rs.getMetaData();

              for (int i = 1; i <= md.getColumnCount(); i++)
                  System.out.print(md.getColumnName(i) + "\t\t");
              System.out.println();

              while (rs.next()) {
                  for (int i = 1; i <= md.getColumnCount(); i++) {
                      System.out.print(rs.getString(id) + "\t\t");
                  }
                  System.out.println();
              }

          } finally {
              rs.close();
          }
        } finally {
            ps.close();
        }
    }
}
