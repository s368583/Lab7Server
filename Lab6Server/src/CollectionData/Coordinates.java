package CollectionData;

import Program.Lab5;

import javax.xml.bind.annotation.XmlElement;
import java.util.Scanner;

/**
 * Ввод координат (x, y)
 * @author Arina Nikitochkina
 */
public class Coordinates {
    private int x;
    private Integer y;

    public Coordinates(int x ,int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinates() {
    }

    /**
     * Метод, для заполнения координат (x, y) из скрипта или вручную пользователем с консоли
     * @return координата (x, y)
     */
    public static Coordinates create() {
        Coordinates coordinates = null;

        if(Lab5.script) {
            int x = Integer.parseInt(Lab5.scriptLines.poll());
            int y = Integer.parseInt(Lab5.scriptLines.poll());

            coordinates = new Coordinates(x, y);
        }
        else {
            Scanner scanner = new Scanner(System.in);

            boolean check = false;

            System.out.print("Введите координату X: ");
            Integer xcoord = null;
            while(!check) {
                try {
                    xcoord = Integer.parseInt(scanner.nextLine());
                    check = true;
                } catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите координату X: ");
                }
            }

            scanner = new Scanner(System.in);
            check = false;
            System.out.print("Введите координату Y: ");
            Integer ycoord = null;
            while(!check) {
                try {
                    ycoord = Integer.parseInt(scanner.nextLine());
                    check = true;
                } catch(Exception e) {
                    System.out.println("\nДанные введены неверно");
                    System.out.print("Введите координату Y: ");
                }
            }

            coordinates = new Coordinates(xcoord, ycoord);
        }
        return coordinates;
    }

    public int getX() {
        return x;
    }
    @XmlElement
    public void setX(int x) {
        this.x = x;
    }
    public Integer getY() {
        return y;
    }
    @XmlElement
    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return ("(" + x + ", " + y + ")");
    }
}