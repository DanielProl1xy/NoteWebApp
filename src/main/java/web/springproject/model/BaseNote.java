package web.springproject.model;

import org.springframework.lang.NonNull;

public class BaseNote {

    private String text;
    public final String uniqueID;
    
    public BaseNote(String defaultText, @NonNull final String uid) {
        text = defaultText;
        uniqueID = new String(uid);
    }

    public String getText() {
        return text;
    }
    
    public void setText(String newText) {
        text = newText;
    }
    
    @Override
    public String toString() {
        return this.text;
    }
}
