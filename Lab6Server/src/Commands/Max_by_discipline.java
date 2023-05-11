package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;

import java.io.IOException;
import java.util.Collections;

/**
 * Класс для вывода любого объекта из коллекции, значение поля discipline которого является максимальным
 * @author Arina Nikitochkina
 */

public class Max_by_discipline extends Command {

    public Max_by_discipline() throws IOException {
        this.description = "вывести любой объект из коллекции, значение поля discipline которого является максимальным";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Max_by_discipline
     */

    @Override
    public void execute() {
        if (Lab5.console){
            System.out.println("\n" + String.valueOf(Collections.max(Lab5.collection, new LabWork.DisciplineComparator())));
        }
        else {Lab5.output = "\n" + String.valueOf(Collections.max(Lab5.collection, new LabWork.DisciplineComparator()));}
    }
}