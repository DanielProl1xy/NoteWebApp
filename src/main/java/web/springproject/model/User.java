package web.springproject.model;

import java.util.Objects;

public class User {

    public final String login;
    public final String name;

    public User(final String log, final String username) {
        login = new String(log);
        name = new String(username);
    }

    @Override
    public String toString() {
        return login;
    }

    @Override
    public int hashCode() {
        return login.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if(other == null) return false;
        if(other.getClass() != this.getClass()) return false;
        if(other == this) return true;
        
        User otherUser = (User) other;
        return Objects.equals(otherUser.login, this.login) &&
                Objects.equals(otherUser.name, this.name);
    }
}
