package com.google.sps.servlets;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {
  private int count = 0;
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the value entered in the form.
    String name = request.getParameter("name-input");
    String email = request.getParameter("email-input");
    String textValue = request.getParameter("text-input");

    // Print the value so you can see it in the server logs.
    System.out.println("New user #" + count + " info: " + name + " " + email + " " + textValue);
    count+=1;
    // Go back to the home page
    response.sendRedirect("https://www.youtube.com/watch?v=dQw4w9WgXcQ");

  }
}