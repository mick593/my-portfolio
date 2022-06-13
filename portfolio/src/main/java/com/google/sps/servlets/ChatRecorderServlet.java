package com.google.sps.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/chat")
public class ChatRecorderServlet extends HttpServlet {
    ArrayList<String> messages;
    public ChatRecorderServlet() {
        messages = new ArrayList<>();
    }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    
    
    String json = convertStrToJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    if (messages.size() > 15) {
        messages = new ArrayList<>();
    }
    String text = request.getParameter("text");
    messages.add(text);
    doGet(request, response);
  }

  private String convertStrToJson(ArrayList<String> messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
}
