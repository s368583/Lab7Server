package CollectionData;

import Program.Lab5;

import javax.xml.bind.annotation.XmlElement;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Описание дисциплины
 * @author Arina Nikitochkina
 */
public class Discipline  implements Comparable<Discipline> {

    /** Название дисциплины  */
    private String name;

    /** Количество лекционных часов  */

    private Integer lectureHours;

    /** Количество часов самоподготовки */
    private int selfStudyHours;

    /** Количество лабораторных работ  */
    private int labsCount;

    public Discipline(String name, int lectureHours, int selfStudyHours, int labsCount) {
        this.name = name;
        this.lectureHours = lectureHours;
        this.selfStudyHours = selfStudyHours;
        this.labsCount = labsCount;
    }

    public Discipline() {
    }

    /**
     * Метод, для заполнения информации о дисциплине из скрипта или вручную пользователем с консоли
     * @return информация о дисциплине
     */
    public static Discipline create() {
        Scanner scanner = new Scanner(System.in);

            boolean check = false;
        if(Lab5.script) {
            String name = Lab5.scriptLines.poll();
            Integer lecHours = Integer.parseInt(Lab5.scriptLines.poll());
            int selfStudyHours = Integer.parseInt(Lab5.scriptLines.poll());
            int labsCount = Integer.parseInt(Lab5.scriptLines.poll());
            return new Discipline(name,lecHours, selfStudyHours, labsCount);
        }
            System.out.print("Введите название дисципины: ");
            String name = null;
            while (!check) {
                try {
                    name = scanner.nextLine();
                    if (name != null && !name.equals(""))
                        check = true;
                    else {
                        System.out.println("\nДанные введены неверно");
                        System.out.print("Введите название дисципины: ");
                    }
                } catch (Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите название дисципины: ");
                }
            }


            check = false;
            System.out.print("Введите количество лекционных часов: ");
            Integer lectureHours = null;
            while (!check) {
                try {
                    lectureHours = Integer.parseInt(scanner.nextLine());
                    check = true;
                } catch (Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите количество лекционных часов: ");
                }
            }


            check = false;
            System.out.print("Введите количество часов самоподготовки: ");
            Integer selfStudyHours = null;
            while (!check) {
                try {
                    selfStudyHours = Integer.parseInt(scanner.nextLine());
                    check = true;
                } catch (Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите количество часов самоподготовки: ");
                }
            }


            check = false;
            System.out.print("Введите количество лабораторных работ: ");
            Integer labsCount = null;
            while (!check) {
                try {
                    labsCount = Integer.parseInt(scanner.nextLine());
                    check = true;
                } catch (Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите количество лабораторных работ: ");
                }
            }

        return new Discipline(name, lectureHours, selfStudyHours, labsCount);
    }

    public String getName() {
        return name;
    }
    @XmlElement
    public void setName(String name) {
        this.name = name;
    }
    public Integer getLectureHours() {
        return lectureHours;
    }
    @XmlElement
    public void setLectureHours(Integer lectureHours) {
        this.lectureHours = lectureHours;
    }
    public int getSelfStudyHours() {
        return selfStudyHours;
    }
    @XmlElement
    public void setSelfStudyHours(int selfStudyHours) {
        this.selfStudyHours = selfStudyHours;
    }
    public int getLabsCount() {
        return labsCount;
    }
    @XmlElement
    public void setLabsCount(int labsCount) {
        this.labsCount = labsCount;
    }

    /**
     * Класс компаратор
     */
    public static class DisciplineComparator implements Comparator<Discipline> {

        /**
         * Метод для сравнения элементов коллекции по полю discipline (для класса Count_less_than_discipline)
         * @param d1 параметр дисциплины, уже имеющийся в коллекции
         * @param d2 заданный параметр дисциплины
         * @return итог сравнения
         */
        @Override
        public int compare(Discipline d1, Discipline d2) {
            int ret = 0;
            double r1 = d1.getLectureHours();
            double r2 = d2.getLectureHours();
            if(Math.round(r1 - r2) > 0) ret = 1;
            if(Math.round(r1 - r2) == 0) ret = 0;
            if(Math.round(r1 - r2) < 0) ret = -1;
            return ret;
        }
    }

    /**
     * Метод для сравнения элементов коллекции по полю discipline
     * @param d входящий параметр discipline
     * @return итог сравнения
     */
    @Override
    public int compareTo(Discipline d) {
        int ret = 0;
        if(Math.round(this.getLectureHours() - d.getLectureHours()) > 0) ret = 1;
        if(Math.round(this.getLectureHours() - d.getLectureHours()) == 0) ret = 0;
        if(Math.round(this.getLectureHours() - d.getLectureHours()) < 0) ret = -1;
        return ret;
    }

    @Override
    public String toString() {
        String toString = this.name + "(lectures: " + this.lectureHours +
                ", self-study: " + this.selfStudyHours + ", labs: " + this.labsCount + ")";
        return toString;
    }
}