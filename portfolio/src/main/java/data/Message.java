package data;
public class Message {
    private final String name;
    private final String email;
    private final String text;
    private final long timestamp;
  
    public Message(String name, String email, String text, long timestamp) {
      this.name = name;
      this.email = email;
      this.text = text;
      this.timestamp = timestamp;
    }
}
