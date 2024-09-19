package web.springproject.model;

public class User {

    public final String login;
    public final String name;

    public User(final String log, final String username)
    {
        login = log;
        name = username;
    }

    @Override
    public String toString()
    {
        return login;
    }
}
