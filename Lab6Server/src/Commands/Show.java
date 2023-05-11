package Commands;

import Program.Command;
import Program.Lab5;

/**
 * Класс для вывода всех элементов коллекции в строковом представлении
 * @author Arina Nikitochkina
 */

public class Show extends Command {
    public Show() {
        this.description = "вывести все элементы коллекции в строковом представлении";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Show
     */

    @Override
    public void execute() {
        final String[] out = {""};
        if(Lab5.collection.isEmpty())
            out[0] = ("Коллекция пуста");
        else {
            Lab5.collection.forEach(labwork -> out[0] += labwork);

        }
        if (Lab5.console){
            System.out.println(out[0]);
        }
        else {Lab5.output = out[0];}
    }
}
