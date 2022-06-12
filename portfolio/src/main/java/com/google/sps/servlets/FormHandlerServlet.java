package com.google.sps.servlets;

import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.IncompleteKey;
import com.google.cloud.datastore.KeyFactory;
// import com.google.cloud.language.v1.Document;
// import com.google.cloud.language.v1.LanguageServiceClient;
// import com.google.cloud.language.v1.Sentiment;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/form-handler")
public class FormHandlerServlet extends HttpServlet {
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

    // Get the value entered in the form.
    String name = request.getParameter("name-input");
    String email = request.getParameter("email-input");
    String textValue = request.getParameter("text-input");
    long timestamp = System.currentTimeMillis();


    // store a message to the database.
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("Message");
    FullEntity<IncompleteKey> msg =
        Entity.newBuilder(keyFactory.newKey())
            .set("name", name)
            .set("email", email)
            .set("text", textValue)
            .set("timestamp", timestamp)
            .build();
    datastore.put(msg);
    
    
    // redirect to meme song on youtube.com
    response.sendRedirect("https://www.youtube.com/watch?v=dQw4w9WgXcQ");

  }
}