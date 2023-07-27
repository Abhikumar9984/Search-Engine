package com.Accio;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/History")
public class History extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)  {
        Connection connection  = DataBaseConnection.getConnection();
        try{
            ResultSet resultSet  = connection.createStatement().executeQuery("select * from history;");
            ArrayList<HistoryResult> results  = new ArrayList<HistoryResult>();
            while(resultSet.next()) {
                HistoryResult historyResult = new HistoryResult();
                historyResult.setKeyword(resultSet.getString("keyword"));
                historyResult.setLink(resultSet.getString("link"));
                results.add(historyResult);
            }
            req.setAttribute("results" , results );
            req.getRequestDispatcher("history.jsp").forward(req,resp);
            resp.setContentType("text/html");
            PrintWriter out  = resp.getWriter();
        }
        catch(SQLException sqlException){
            sqlException.printStackTrace();
        }
        catch(ServletException | IOException e){
            throw new RuntimeException(e);
        }
    }
}
