package web.springproject.model.interfaces;

import org.springframework.lang.Nullable;

import web.springproject.model.User;

public interface IUsersStorage {
    
    public boolean IsLoginTaken(final String login);

    @Nullable
    public User RegisterUser(final String login, final String name, final int hashedpassword);

    @Nullable
    public User LoginUser(final String login, final int hashedpassword);

    @Nullable
    public User GetUserByLogin(final String login);
}
