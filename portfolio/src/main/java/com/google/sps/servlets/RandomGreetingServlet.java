package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/rand-hello")
public class RandomGreetingServlet extends HttpServlet {
    ArrayList<String> messages = new ArrayList<>();
    public RandomGreetingServlet() {
        messages.add("Unlucky, this message doesn't mean anything.");
        messages.add("Have a nice day!");
        messages.add("Meow meow 🐱🐈");
    }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    
    String json = convertStrToJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  private String convertStrToJson(ArrayList<String> messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
}
