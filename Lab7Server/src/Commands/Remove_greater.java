package Commands;

import CollectionData.LabWork;
import Program.ClientCommandReader;
import Program.Command;
import Program.Lab5;
import Program.SQLManager;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
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
        Type dataType = new TypeToken<LabWork>(){}.getType(); //тип, в который нужно превратить присланную строчку
        String data = (String) object; //превращаем в строку то, что прислали
        System.out.println(data);
        LabWork labWork2 = gson.fromJson(data, dataType);

        try {
            SQLManager.fill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Iterator<LabWork> iter = Lab5.collection.iterator();
        String test = ""; //для запоминания всех строчек вывода (id, которые нужно удвлить)
        ArrayList<Integer> arrayList = new ArrayList<Integer>(); // id, которые нужно удалить
        while(iter.hasNext()) {
            LabWork l = iter.next();
            if(l.compareTo(labWork2) > 0) {
                arrayList.add(Integer.parseInt(String.valueOf(l.getId())));
            }
        }

        String l = Lab5.lastString.substring(0, Lab5.lastString.lastIndexOf("remove_greater")).split("::")[0];
        String p = Lab5.lastString.substring(0, Lab5.lastString.lastIndexOf("remove_greater")).split("::")[1];

        if(!arrayList.isEmpty()) {
            for(Integer x: arrayList){
                try {
                    if(SQLManager.editable(l, p, x)) { //проверка, что можем изменять текущий элемент
                        new Remove_by_id().execute(x);
                        test += Lab5.output;
                    } else test += "\nУ вас нет прав на удаление данного объекта";
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        else test = "\nНет подходящих элементов";
        try {
            SQLManager.fill();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Lab5.output = test;
    }

    @Override
    public void execute() {
        LabWork labWork2 = LabWork.create();
        ArrayList <LabWork> list = new ArrayList<>();
        list.addAll(Lab5.collection);
        for(int i = 0; i < Lab5.collection.size(); i++){
            LabWork labWork1 = list.get(i);
            if(labWork1.compareTo(labWork2)>0){
                new Remove_by_id().execute(labWork1.getId());
                try {
                    SQLManager.fill();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}