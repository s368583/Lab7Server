package Commands;

import Program.*;

import java.io.IOException;
import java.sql.SQLException;

public class Register extends Command {

    public Register() throws IOException {
        this.description = "зарегистрировать пользователя\n";
    }

    @Override
    public void execute(Object object) {
        String username = object.toString().split("::")[0];
        String password = object.toString().split("::")[1];

        try {
            if (SQLManager.userExists(username)) {
                Lab5.output = "\nПользователь с таким именем уже существует";
            } else {
                SQLManager.addUser(username, password);
                Lab5.output = "\nВы успешно зарегистрированы. Выполнен вход в систему";
            }
        } catch (SQLException ignored) {}
    }
}