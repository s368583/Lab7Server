package Commands;

import Program.Command;
import Program.Lab5;
import Program.SQLManager;

import java.sql.SQLException;

/**
 * Класс для очистки коллекции
 * @author Arina Nikitochkina
 */
public class Clear extends Command {
    public Clear() {
        this.description = "очистить коллекцию";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Clear
     */
    @Override
    public void execute() {
        try {
            SQLManager.clear();
            SQLManager.fill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (Lab5.console){
            System.out.println("\nКоллекция очищена");
        }
        else {Lab5.output = "\nКоллекция очищена";}
    }
}
