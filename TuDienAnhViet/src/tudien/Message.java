package tudien;

import java.io.Serializable;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;
    public String type;  
    public String word;
    public String meaning;

    public Message(String type, String word, String meaning) {
        this.type = type;
        this.word = word;
        this.meaning = meaning;
    }
}
