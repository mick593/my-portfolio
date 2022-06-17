package com.google.sps.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.Datastore;
import com.google.cloud.datastore.DatastoreOptions;
import com.google.cloud.datastore.Entity;
import com.google.cloud.datastore.FullEntity;
import com.google.cloud.datastore.KeyFactory;
import com.google.cloud.datastore.Query;
import com.google.cloud.datastore.QueryResults;
import com.google.cloud.datastore.StructuredQuery;
import com.google.cloud.datastore.IncompleteKey;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.sps.data.ChatResponse;

/** Handles requests sent to the /hello URL. Try running a server and navigating to /hello! */
@WebServlet("/chat")
public class ChatRecorderServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    // Retrieve the last 15 text messages from the datastore, ordered by timestamp.
    Query<Entity> query =
        Query.newEntityQueryBuilder()
            .setKind("ChatMessage")
            .setOrderBy(StructuredQuery.OrderBy.desc("timestamp"))
            .setLimit(15)
            .build();
    QueryResults<Entity> results = datastore.run(query);

    ArrayList<ChatResponse> messages = new ArrayList<>();
    while (results.hasNext()) {
      Entity entity = results.next();
      messages.add(new ChatResponse(entity.getString("text"), entity.getTimestamp("timestamp")));
    }
    
    String json = convertStrToJson(messages);
    response.setContentType("application/json;");
    response.getWriter().println(json);
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String text = request.getParameter("text");
    Timestamp timestamp = Timestamp.now();

    // store a message to the database.
    Datastore datastore = DatastoreOptions.getDefaultInstance().getService();
    KeyFactory keyFactory = datastore.newKeyFactory().setKind("ChatMessage");
    FullEntity<IncompleteKey> msg =
        Entity.newBuilder(keyFactory.newKey())
            .set("text", text)
            .set("timestamp", timestamp)
            .build();
    datastore.put(msg);
  }

  private String convertStrToJson(ArrayList<ChatResponse> messages) {
    Gson gson = new Gson();
    String json = gson.toJson(messages);
    return json;
  }
}
