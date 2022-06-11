package com.google.sps.servlets;

import com.google.cloud.language.v1.Document;
import com.google.cloud.language.v1.LanguageServiceClient;
import com.google.cloud.language.v1.Sentiment;
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

    // use NLP score to filter out negative message
    Document doc =
            Document.newBuilder().setContent(textValue).setType(Document.Type.PLAIN_TEXT).build();
    LanguageServiceClient languageService = LanguageServiceClient.create();
    Sentiment sentiment = languageService.analyzeSentiment(doc).getDocumentSentiment();
    float score = sentiment.getScore();
    languageService.close();

    // print a message to the log if the content is not negative.
    if (score > 0) {
        System.out.println("New user #" + count + " info: " + name + " " + email + " " + textValue);
    }
    
    count+=1;
    // redirect to meme song on youtube.com
    response.sendRedirect("https://www.youtube.com/watch?v=dQw4w9WgXcQ");

  }
}