package com.Nandini.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class HelloNandiniServlet
 */
public class HelloNandiniServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HelloNandiniServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the SQLite database
            String url = "jdbc:sqlite:/Users/saisatyajonnalagadda/eclipse-workspace/Nandinijava/src/mydatabase.db";
            Connection conn = DriverManager.getConnection(url);

            // SQL Select Query
            String query = "SELECT * FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet resultSet = pstmt.executeQuery();

            // Write HTML response with table
            PrintWriter out = response.getWriter();
            out.println("<html><body>");
            out.println("<h1>User Data</h1>");
            out.println("<table border='1'>");
            out.println("<tr><th>Username</th><th>Email</th><th>Password</th></tr>");

            // Loop through the result set and populate the table rows
            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");

                out.println("<tr><td>" + username + "</td><td>" + email + "</td><td>" + password + "</td></tr>");
            }

            out.println("</table>");
            out.println("</body></html>");

            // Close resources
            resultSet.close();
            pstmt.close();
            conn.close();

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error: Failed to fetch data.");
        }
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Create a connection to the SQLite database
            String url = "jdbc:sqlite:/Users/saisatyajonnalagadda/eclipse-workspace/Nandinijava/src/mydatabase.db";
            Connection conn = DriverManager.getConnection(url);


            // SQL Insert Query
            String query = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, email);
            pstmt.setString(3, password);
            pstmt.executeUpdate();

            // Close resources
            pstmt.close();
            conn.close();

            response.getWriter().write("Registration Successful!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.getWriter().write("Error: Registration failed.");
        }
    }

}
