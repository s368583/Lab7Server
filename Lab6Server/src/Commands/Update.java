package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Класс для обновления значения элемента коллекции, id которого равен заданному
 * @author Arina Nikitochkina
 */

public class Update extends Command {
    public Update() {
        this.description = "обновить значение элемента коллекции, id которого равен заданному";
        this.argument = true;
    }

    /**
     * Метод для выполнения команды Update
     */

    @Override
    public void execute(Object object) {
        int id = 0;
        try{
            id = Integer.parseInt(((String) object).split("%%%")[0]);
        }
        catch(Exception e) {
            if(!Lab5.console) {
                Lab5.output = "\nДанные введены неверно\n";
            }
            else{
                System.out.println("\nДанные введены неверно\n");
            }
        }
        int count = 0;
        Iterator<LabWork> iter = Lab5.collection.iterator();
        while(iter.hasNext()) {
            LabWork labWork = iter.next();
                if(labWork.getId() == Long.parseLong(String.valueOf(id))) {
                    iter.remove();
                    count++;
                }


        }
        if (count == 0) {
            if(!Lab5.console) {
                Lab5.output = "\nТакого id не существует";
            }
            else{
                System.out.println("\nТакого id не существует");
            }
        }
        else{
            if(!Lab5.console) {
                Type dataType = new TypeToken<LabWork>() {}.getType();
                String data = ((String) object).split("%%%")[1];
                System.out.println(data);
                LabWork labWork = gson.fromJson(data, dataType);
                labWork.setId((long) id);
                Lab5.collection.add(labWork);
                Lab5.output = "\nЭлемент (id: " + id + ") успешно обновлён";
            }
            else {
                LabWork labWork = LabWork.create();
                labWork.setId((long) id);
                Lab5.collection.add(labWork);
                System.out.println("\nЭлемент (id: " + id + ") успешно обновлён");
            }
        }

    }
}
