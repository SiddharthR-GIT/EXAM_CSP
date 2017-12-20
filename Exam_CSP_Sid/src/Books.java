import java.io.IOException;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/Books"})
public class Books extends HttpServlet {

    private Connection connection;
    private Statement statement;
    private PreparedStatement find;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        response.setContentType("text/html");
        PrintWriter pr = response.getWriter();

        try{
            String driver = "com.mysql.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/Books?autoReconnect=true&useSSL=false";
            Class.forName(driver);  // load the driver
            connection = DriverManager.getConnection(url,"root","password");

            find = connection.prepareStatement("SELECT *" + "FROM Books");
            statement = connection.createStatement();

            String sqlStatement = "INSERT INTO Books (First_Name, Last_Name, ISBN, Title, Copyright, Comments) " +
                    "VALUES("
                    //+"'" + request.getParameter("id") +"'" +','
                    +"'" + request.getParameter("first_name") +"'" +','
                    +"'" + request.getParameter("last_name")+"'" +','
                    +"'" + request.getParameter("ISBN") +"'"+ ','
                    +"'" + request.getParameter("Title") +"'" +','
                    +"'" + request.getParameter("Copyright") +"'" +','
                    +"'" + request.getParameter("comments") + "')";

            statement.executeUpdate(sqlStatement);

            ResultSet resultSet = find.executeQuery();

            pr.println("<html><head><title>Results</title></head><body>");
            pr.println("<p> Values have been entered into Database");
            pr.println("<p> ID , First Name, Last Name, ISBN, Title, Copyright, Comments");

            while (resultSet.next()) {
                pr.println("<p>Succes - ID Number:"+resultSet.getString(1)+" </p>");
                pr.println();
                pr.println("<p>" + resultSet.getString(1)
                        + ", " + resultSet.getString("first_name")
                        + ", " + resultSet.getString("last_name")
                        + ", " + resultSet.getString("ISBN")
                        + ", " + resultSet.getString("Title")
                        + ", " + resultSet.getString("Copyright")
                        + ", " + resultSet.getString("comments")
                        + "</p>");
            }

        }catch(SQLException sql){
            sql.printStackTrace();
            pr.println("<html><head><title>ERROR</title></head><body>");
            pr.println("<p>You Problem is: " + sql + "</p>");
            pr.println(sql);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            pr.close();
            try{
                if(statement != null){
                    statement.close();
                }
                if (connection != null){
                    connection.close();
                }
            }catch(SQLException sql){
                sql.printStackTrace();
            }
        }

    }
}
