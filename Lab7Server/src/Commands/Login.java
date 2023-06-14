package Commands;

import Program.*;

import java.io.IOException;
import java.sql.SQLException;

public class Login extends Command {

    public Login() throws IOException {
        this.description = "войти в систему\n";
    }

    @Override
    public void execute(Object object) {
        String username = object.toString().split("::")[0]; //имя пользователя
        String password = object.toString().split("::")[1]; //зашифрованный пароль

        try {
            if (!SQLManager.userExists(username)) {
                Lab5.output = "\nПользователя с таким именем не существует. Повторите попытку";
            } else {
                if (SQLManager.accessed(username, password)) { //пароль и логин совпадают и есть в бд
                    Lab5.output = "\nВыполнен вход в систему";
                } else {
                    Lab5.output = "\nНеверный пароль";
                }
            }
        } catch (SQLException ignored) {}
    }
}