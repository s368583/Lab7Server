package Program;

import CollectionData.LabList;
import CollectionData.LabWork;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;

/**
 * Класс для файла и заполнения его данных в коллекцию
 * @author Arina Nikitochkina
 */
public class CollectionFiller extends Command {
    //protected Scanner scanner = new Scanner(new File("src/save.json"));
    //protected Gson gson = new Gson();


    /**
     * Метод для учета возможной ошибки по ходу чтения файла
     * @throws FileNotFoundException
     */
    public CollectionFiller() throws FileNotFoundException {}

    /**
     * Метод для заполнения коллекции из файла
     */
    @Override
    public void execute(Object object) {

        JAXBContext jaxbContext = null;
        try {
            CollectionDeque deque =  new CollectionDeque();
            jaxbContext = JAXBContext.newInstance(LabList.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            LabList labs = (LabList) jaxbUnmarshaller.unmarshal(new File(System.getProperty("user.dir")+"/Lab6Server/src/" + String.valueOf(object)));
            for (LabWork labWork : labs.getLabs()) {
                //System.out.println(labWork.toString());
                deque.add(labWork);
            }
            Lab5.setCollection(deque);

        } catch (JAXBException e) {
            System.out.println("Файл пустой");
        }

    }
}
