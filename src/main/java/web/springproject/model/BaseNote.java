package web.springproject.model;

import java.util.Objects;

public class BaseNote {

    private String text;
    private String uniqueID;
    
    public BaseNote(String defaultText, String uid)
    {
        text = defaultText;
        uniqueID = uid;
    }

    public String getText()
    {
        return text;
    }
    
    public void setText(String newText)
    {
        text = newText;
    }

    public final String getUniqueID()
    {
        return uniqueID;
    }

    @Override
    public String toString()
    {
        return this.text;
    }
    
    @Override 
    public boolean equals(Object other)
    {
        if(other == null) return false;
        if(other.getClass() != getClass()) return false;

        return Objects.equals(other, this);
    }
}
