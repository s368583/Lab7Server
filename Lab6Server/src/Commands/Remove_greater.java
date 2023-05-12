package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Класс для удаления из коллекции всех элементов, превышающих заданный
 * @author Arina Nikitochkina
 */

public class Remove_greater extends Command {

    public Remove_greater() throws IOException {
        this.description = "удалить из коллекции все элементы, превышающие заданный";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Remove_greater
     */

    @Override
    public void execute(Object object) {
        Type dataType = new TypeToken<LabWork>(){}.getType();
        String data = (String) object;
        LabWork labWork2 = gson.fromJson(data, dataType);

/*
        int count = 0;
        Iterator<LabWork> iter = Lab5.collection.iterator();
        while(iter.hasNext()) {
            LabWork l = iter.next();
            if(l.compareTo(labWork2) > 0) {
                //Lab5.collection.remove(l);
                count++;
            }
        }
*/

        Lab5.collection.stream().filter(x -> x.compareTo(labWork2)>0).forEach(Lab5.collection::remove);
        //Lab5.output = "\nПодходящие элементы (" + count + ") были удалены";
        Lab5.output = "\nПодходящие элементы () были удалены";

    }

    @Override
    public void execute() {
        LabWork labWork2 = LabWork.create();
        int count = 0;
        Iterator<LabWork> iter = Lab5.collection.iterator();
        ArrayList <LabWork> list = new ArrayList<>();
        list.addAll(Lab5.collection);
        for(int i = 0; i<Lab5.collection.size(); i++){
            LabWork labWork1 = list.get(i);
            if (labWork1.compareTo(labWork2)>0){
                Lab5.collection.remove(labWork1);
                count++;
            }
        }


        System.out.println("\nПодходящие элементы (" + count + ") были удалены");
    }
}