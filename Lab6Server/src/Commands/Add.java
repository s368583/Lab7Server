package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

/**
 * Класс для вывода справки по доступным командам
 * @author Arina Nikitochkina
 */

public class Add extends Command {
    public Add() {
        this.description = "добавить новый элемент в коллекцию";
        this.argument = false;
    }

    /**
     * Метод для выполнения команды Add
     */
//для клиента
    @Override
    public void execute(Object object) {

        Type dataType = new TypeToken<LabWork>(){}.getType();
        String data = (String) object;
        LabWork labWork = gson.fromJson(data, dataType);
        labWork.setId(labWork.generateId());
        Lab5.collection.add(labWork);
        Lab5.output = "\nЭлемент успешно добавлен";}
//для сервера
    @Override
    public void execute() {
        Lab5.collection.add(LabWork.create());
        System.out.println("\nЭлемент успешно добавлен");
    }
}
