package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Класс для сортировки коллекции в порядке, обратном нынешнему
 * @author Arina Nikitochkina
 */

public class Reorder extends Command {
    public Reorder() {
        this.description = "отсортировать коллекцию в порядке, обратном нынешнему";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Reorder
     */

    @Override
    public void execute() {
        ArrayList<LabWork> list = new ArrayList<>();
        list.addAll(Lab5.collection);
        Collections.reverse(list);
        Lab5.collection.clear();
        Lab5.collection.addAll(list);
        if (Lab5.console){
            System.out.println("\nТеперь коллекция отсортирована в обратном порядке");
        }
        else {Lab5.output = "\nТеперь коллекция отсортирована в обратном порядке";}
    }
}
