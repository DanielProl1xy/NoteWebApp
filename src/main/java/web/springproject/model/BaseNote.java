package web.springproject.model;

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
        if(!(other instanceof BaseNote)) return false;

        BaseNote othernote = (BaseNote) other;
        return othernote.uniqueID == this.uniqueID;
    }

}
