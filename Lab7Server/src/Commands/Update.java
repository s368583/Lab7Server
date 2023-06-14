package Commands;

import CollectionData.LabWork;
import Program.*;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
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
        int id = Integer.parseInt(((String) object).split("%%%")[0]);
        int count = 0;
        for(LabWork labWork : Lab5.collection)
            if(labWork.getId() == id)
                count++;
        if(count == 0) {
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
                try {
                    SQLManager.updateLabWork(id, labWork);
                    SQLManager.fill();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                Lab5.output = "\nЭлемент (id: " + id + ") успешно обновлён";
            }
            else {
                LabWork labWork = LabWork.create();
                labWork.setId((long) id);
                try {
                    SQLManager.updateLabWork(id, labWork);
                    SQLManager.fill();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("\nЭлемент (id: " + id + ") успешно обновлён");
            }
        }
    }
}
