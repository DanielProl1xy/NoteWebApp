package web.springproject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import web.springproject.model.NotesService;
import web.springproject.model.UserLoginService;
import web.springproject.model.interfaces.INotesStorage;
import web.springproject.model.interfaces.ITokensStorage;
import web.springproject.model.interfaces.IUsersStorage;
import web.springproject.model.storages.db.SQLNotesStorage;
import web.springproject.model.storages.db.SQLTokensStorage;
import web.springproject.model.storages.db.SQLUsersStorage;

@Configuration
public class AppConfig {
    
    @Bean("userLoginServiceBean")
    public UserLoginService GetUserLoginService() {
        return new UserLoginService();
    }

    @Bean("notesServiceBean")
    public NotesService GetNotesService() {
        return new NotesService();
    }

    @Bean("notesStorage")
    public INotesStorage GetNotesStorage() {
        return new SQLNotesStorage();
    }

    @Bean("tokensStorage")
    public ITokensStorage GetTokensStorage() {
        return new SQLTokensStorage();
    }

    @Bean("usersStorage")
    public IUsersStorage GetUsersStorage() {
        return new SQLUsersStorage();
    }
}
