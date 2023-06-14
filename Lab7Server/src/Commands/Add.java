package Commands;

import CollectionData.LabWork;
import Program.Command;
import Program.Lab5;
import Program.SQLManager;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.SQLException;
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
        SQLManager.addLabWork(labWork); //добавляет элемент в бд
        try {
            SQLManager.fill(); //обновляет коллекцию из бд и запоминает на сервере
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Lab5.output = "\nЭлемент успешно добавлен";}
//для сервера
    @Override
    public void execute() {
        SQLManager.addLabWork(LabWork.create());
        try {
            SQLManager.fill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        System.out.println("\nЭлемент успешно добавлен");
    }
}
