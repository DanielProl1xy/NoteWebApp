package web.springproject.model.interfaces;


import web.springproject.model.User;

public interface IUsersStorage {
    
    public boolean IsLoginTaken(final String login);

    public User RegisterUser(final String login, final String name, final int hashedpassword);

    public User LoginUser(final String login, final int hashedpassword);

    public User GetUserByLogin(final String login);
}
