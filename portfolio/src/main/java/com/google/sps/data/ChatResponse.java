package com.google.sps.data;

import com.google.cloud.Timestamp;

public class ChatResponse {
    private final Timestamp timestamp;
    private final String text;
  
    public ChatResponse(String text, Timestamp timestamp) {
      this.text = text;
      this.timestamp = timestamp;
    }

    public String getText() {
        return text;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}

