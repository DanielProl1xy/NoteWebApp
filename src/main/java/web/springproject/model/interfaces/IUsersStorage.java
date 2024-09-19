package web.springproject.model.interfaces;

import web.springproject.model.User;

public interface IUsersStorage {
    
    public boolean IsLoginTaken(final String login);
    public boolean RegisterUser(final String login, final String name, final String password);
    public User LoginUser(final String login, final String password);
}
