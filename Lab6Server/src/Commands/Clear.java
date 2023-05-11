package Commands;

import Program.Command;
import Program.Lab5;

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
        Lab5.collection.clear();
        if (Lab5.console){
            System.out.println("\nКоллекция очищена");
        }
        else {Lab5.output = "\nКоллекция очищена";}
    }
}
