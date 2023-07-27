 package com.Accio;

import com.Accio.DataBaseConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")
public class Search extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
          String keyword  =  req.getParameter("keyword");

          Connection connection  = DataBaseConnection.getConnection();
          try {
              // Store the query of the user
              PreparedStatement preparedStatement = connection.prepareStatement("Insert into history values(? , ?);");
              preparedStatement.setString(1 , keyword);
              preparedStatement.setString(2 ,"http://localhost:8080/SearchEngine/Search?keyword="+keyword);
              preparedStatement.executeUpdate();

              ResultSet resultSet = connection.createStatement().executeQuery("select distinct(pagetitle) ,pagelink , (length(lower(pagetext))-length(replace(lower(pagetext) , '" + keyword.toLowerCase() + "' , '')))/length('" + keyword.toLowerCase() + "') as countoccurance from pages order by countoccurance desc limit 30;");
              ArrayList<SearchResult> results = new ArrayList<SearchResult>();

              while (resultSet.next()) {
                  SearchResult searchResult = new SearchResult();
                  searchResult.setTitle(resultSet.getString("pageTitle"));
                  searchResult.setLink(resultSet.getString("pageLink"));
                  results.add(searchResult);
              }

              for(SearchResult result : results){
                  System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
              }

               req.setAttribute("results" , results);
               req.getRequestDispatcher("/search.jsp").forward(req , resp);
               resp.setContentType("text/html");
               PrintWriter out  = resp.getWriter();
          }
          catch(SQLException | ServletException sqlException){
              sqlException.printStackTrace();
          }

    }
}
