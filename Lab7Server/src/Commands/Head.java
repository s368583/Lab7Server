package Commands;

import Program.Command;
import Program.Lab5;

/**
 * Класс для вывода первого элемента коллекции
 * @author Arina Nikitochkina
 */

public class Head extends Command {
    public Head() {
        this.description = "вывести первый элемент коллекции";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Head
     */

    @Override
    public void execute() {
        if (Lab5.console){
            System.out.println(Lab5.collection.head());
        }
        else {Lab5.output = Lab5.collection.head();}
    }
}
