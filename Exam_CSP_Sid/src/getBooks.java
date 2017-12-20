import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getBooks extends HttpServlet {
    private Connection connection;
    private Statement statement;
    private PreparedStatement lastID;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/Books?autoReconnect=true&useSSL=false";
            Class.forName(driver);  // load the driver
            connection = DriverManager.getConnection(url,"root","password");

            lastID = connection.prepareStatement("SELECT ID,First_Name, Last_Name, ISBN, Title, Cpoyright, Comments"
                                                    + "FROM Books"+ "WHERE ID = LAST_INSERT_ID()");

            statement = connection.createStatement();
        }catch(SQLException sql){
            sql.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
