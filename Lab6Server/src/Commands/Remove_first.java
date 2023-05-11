package Commands;

import Program.Command;
import Program.Lab5;

/**
 * Класс для удаления первого элемента из коллекции
 * @author Arina Nikitochkina
 */

public class Remove_first extends Command {
    public Remove_first() {
        this.description = "удалить первый элемент из коллекции";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Remove_first
     */

    @Override
    public void execute() {
        Lab5.collection.removeFirst();
        if (Lab5.console){
            System.out.println("\nПервый элемент был удалён");
        }
        else {Lab5.output = "\nПервый элемент был удалён";}
    }
}