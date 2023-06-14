package Commands;

import CollectionData.Discipline;
import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Класс для вывода количества элементов, значение поля discipline которых меньше заданного
 * @author Arina Nikitochkina
 */

public class Count_less_than_discipline extends Command {
    public Count_less_than_discipline() {
        this.description = "вывести количество элементов, значение поля discipline которых меньше заданного";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Count_less_than_discipline
     */
    @Override
    public void execute(Object object) {

        Type dataType = new TypeToken<Discipline>(){}.getType();
        String data = (String) object;
        Discipline discipline2 = gson.fromJson(data, dataType);

        Discipline.DisciplineComparator disciplineComparator = new Discipline.DisciplineComparator();

        ArrayList<Discipline> list = new ArrayList<>();
        Lab5.collection.forEach(labWork -> list.add(labWork.getDiscipline()));

        int counter = 0;

        for(Discipline discipline : list)
            if(disciplineComparator.compare(discipline, discipline2) < 0)
                counter++;

        Lab5.output = "\nЭлементов с полем discipline, меньшим, чем заданное: " + counter;
    }

    @Override
    public void execute() {
        Discipline discipline2 = Discipline.create();

        Discipline.DisciplineComparator disciplineComparator = new Discipline.DisciplineComparator();

        ArrayList<Discipline> list = new ArrayList<>();
        Lab5.collection.forEach(labWork -> list.add(labWork.getDiscipline()));

        int counter = 0;

        for(Discipline discipline : list)
            if(disciplineComparator.compare(discipline, discipline2) < 0)
                counter++;

        System.out.println("\nЭлементов с полем discipline, меньшим, чем заданное: " + counter);
    }
}
