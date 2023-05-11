package Commands;

import Program.Command;
import Program.Lab5;

/**
 * Класс для вывода информации о коллекции
 * @author Arina Nikitochkina
 */

public class Info extends Command {
    public Info() {
        this.description = "вывести информацию о коллекции";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Info
     */

    @Override
    public void execute() {
        if (Lab5.console){
            System.out.println(Lab5.collection.info());
        }
        else {Lab5.output = Lab5.collection.info();}

    }
}
