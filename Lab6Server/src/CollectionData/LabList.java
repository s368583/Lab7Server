package CollectionData;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayDeque;

/**
 * Класс, где хранится массив лабораторных работ
 * @author Arina Nikitochkina
 */
@XmlRootElement(name = "Labs")
public class LabList {
    private ArrayDeque<LabWork> labs;

    public LabList() {
        this.labs = new ArrayDeque<>();
    }

    public LabList(ArrayDeque<LabWork> labs) {
        this.labs = labs;
    }

    public ArrayDeque<LabWork> getLabs() {
        return labs;
    }

    @XmlElement(name = "Lab")
    public void setLabs(ArrayDeque<LabWork> labs) {
        this.labs = labs;
    }

}
