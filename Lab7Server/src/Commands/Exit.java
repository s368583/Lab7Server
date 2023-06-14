package Commands;

import Program.Command;
import Program.Lab5;

import java.io.IOException;

/**
 * Класс для завершения программы
 * @author Arina Nikitochkina
 */

public class Exit extends Command {
    public Exit() {
        this.description = "завершить программу";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Exit
     */

    @Override
    public void execute() {
        try {
            Lab5.server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
}
