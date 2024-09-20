package web.springproject.model.interfaces;

import java.util.List;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import web.springproject.model.User;

public interface ITokensStorage {
    
    public void StoreToken(@NonNull final User user, @NonNull final String token);
    public void DeleteToken(@NonNull final String token);
    public boolean ContainsToken(@NonNull final User user, @NonNull final String token);

    @Nullable
    public User GetUserByToken(@NonNull final String token);
    
    public List<String> GetTokensForUser(@NonNull final User user);
}
