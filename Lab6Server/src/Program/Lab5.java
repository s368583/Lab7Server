package Program;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Класс для выполнения команд, записи коллекции, чтения скрипта
 * Главный класс
 * @author Arina Nikitochkina
 */


public class Lab5 {

    public static Server server;
    public static Thread consoleCommandReader;
    public static String lastString = "";
    public static String output = "";

    public static CollectionDeque collection = new CollectionDeque();


    public static void setCollection(CollectionDeque collection) {
        Lab5.collection = collection;
    }

    /** Имя файла коллекции */
    public static String collectionPath = "collection.xml";

    /** Имя файла скрипта     */
    public static String scriptPath = "";

    /** Очередь из команд     */
    public static Queue<String> scriptLines = new LinkedList<String>() {
    };

    /** script для того, чтобы при чтении скрипта он не перескакивал на консоль и до конца прочитал и понял скрипт   */
    public static boolean script = false;
    public static boolean console = true;

    public static void main(String[] args) throws Exception {
        String fileName = "collection.xml";

        if(args.length==1) {
            fileName = args[0];
        }
        else {
            if(args.length>0) {
                System.out.println("Ошибка аргумента. Буду использвать src/collection.xml");
            }
            fileName="collection.xml";
        }

        System.out.printf("Имя файла: %s\n", fileName);

        File f = new File("/home/studs/s368583/Lab6Server/Lab6Server/src/" + fileName);
        if (f.isFile() && f.canRead()) {
            System.out.println("файл существует");
        } else {
            fileName = "collection.xml";
            System.out.println("Такого файла не существует, буду использвать src/collection.xml");
        }

        new CollectionFiller().execute(fileName);

        server = new Server("localhost", 8989);
        server.start();
    }
}