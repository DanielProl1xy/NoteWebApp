package web.springproject.model.interfaces;

import java.util.List;

import web.springproject.model.User;

public interface ITokensStorage {
    
    public void StoreToken(final User user, final String token);

    public void DeleteToken(final String token);

    public boolean ContainsToken(final User user, final String token);

    public User GetUserByToken(final String token);
    
    public List<String> GetTokensForUser(final User user);
}
