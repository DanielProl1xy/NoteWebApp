package web.springproject.model;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import web.springproject.model.interfaces.ITokensStorage;
import web.springproject.model.interfaces.IUsersStorage;

@Component
public class UserLoginService {

    @Autowired
    @Qualifier("usersStorage")
    private IUsersStorage usersStorage; 

    @Autowired
    @Qualifier("tokensStorage")
    private ITokensStorage tokensStorage;

    public enum LoginResult {
        LOGIN_TAKEN,
        LOGIN_FAILED,
        REGISTER_SUCCESS,
        LOGIN_SUCCESS
    }

    public class LoginResponseStructure {
        public final LoginResult result;
        public final String token;
        public final User user;

        public LoginResponseStructure(LoginResult res, User usr , String tkn) {
            result = res;
            token = tkn == null ? "" : new String(tkn);
            user = usr;
        }
    }

    public LoginResponseStructure RegisterUser(@NonNull final String login, 
                @NonNull final String name, @NonNull final String password) {

        if(usersStorage.IsLoginTaken(login))
        {
            return new LoginResponseStructure(LoginResult.LOGIN_TAKEN, null, null);
        }
        
        final User user = usersStorage.RegisterUser(login, name, password.hashCode());
        final String token = createToken(user);
        return new LoginResponseStructure(LoginResult.LOGIN_SUCCESS, user, token); 
    }

    public LoginResponseStructure LoginUser(@NonNull final String login, @NonNull final String password) {
        final User user = usersStorage.LoginUser(login, password.hashCode());
        
        if(user != null)
        {
            final String token = createToken(user);
            return new LoginResponseStructure(LoginResult.LOGIN_SUCCESS, user, token);
        }
        return new LoginResponseStructure(LoginResult.LOGIN_FAILED, null, null);

    }

    public User CheckToken(final String token) {
        return tokensStorage.GetUserByToken(token);
    }

    public void InvalidateToken(@NonNull final String token) {

        tokensStorage.DeleteToken(token);
    }

    private String createToken(@NonNull final User user) {

        final String token = UUID.randomUUID().toString();
        tokensStorage.StoreToken(user, token);
        return token;
    }
    
}
