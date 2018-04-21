package snake;

import java.util.ArrayList;

public class Snake {

    //Направление движения змеи
    private SnakeDirection direction;
    //Состояние - жива змея или нет.
    private boolean isAlive;
    //Список кусочков змеи.
    private ArrayList<SnakeSection> sections;

    private Controller controller;


    public Snake(Controller controller) {
        this.controller = controller;
        sections = new ArrayList<>();
        sections.add(new SnakeSection());
        isAlive = true;
    }

    /**
     * Метод перемещает змею на один ход.
     * Направление перемещения задано переменной direction.
     */
    public void move() {
        if (!isAlive) return;

        if (direction == SnakeDirection.UP)
            move(0, -1);
        else if (direction == SnakeDirection.RIGHT)
            move(1, 0);
        else if (direction == SnakeDirection.DOWN)
            move(0, 1);
        else if (direction == SnakeDirection.LEFT)
            move(-1, 0);
    }

    /**
     * Метод перемещает змею в соседнюю клетку.
     * Координаты клетки заданы относительно текущей головы с помощью переменных (dx, dy).
     */
    private void move(int dx, int dy) {
        //Создаем новую голову - новый "кусочек змеи".
        SnakeSection head = sections.get(0);
        head = new SnakeSection(head.getX() + dx, head.getY() + dy);

        //Проверяем - не вылезла ли голова за границу комнаты
        checkBorders(head);
        if (!isAlive) return;

        //Проверяем - не пересекает ли змея  саму себя
        checkBody(head);
        if (!isAlive) return;

        //Проверяем - не съела ли змея мышь.
        Mouse mouse = controller.getMouse();
        if (head.getX() == mouse.getX() && head.getY() == mouse.getY()) //съела
        {
            controller.eatMouse();   //Хвост не удаляем, но создаем новую мышь.
            addNewHead(head);   //добавили новую голову

        } else //просто движется
        {
            addNewHead(head);    //добавили новую голову
            removeTail();        //удалили хвост
        }
    }

    private void addNewHead(SnakeSection head) {
        // Рисуем новую голову
        controller.drawField(FieldType.SNAKE_HEAD, head.getX(), head.getY());
        // Бывшую голову перерисовываем обычной секцией
        controller.clearField(sections.get(0).getX(), sections.get(0).getY());
        controller.drawField(FieldType.SNAKE_SECTION, sections.get(0).getX(), sections.get(0).getY());
        sections.add(0, head);           //Добавили новую голову
    }

    private void removeTail() {
        SnakeSection tail = sections.get(sections.size() - 1);
        controller.clearField(tail.getX(), tail.getY());
        sections.remove(tail);   //удалили последний элемент с хвоста
    }

    /**
     * Метод проверяет - находится ли новая голова в пределах комнаты
     */
    private void checkBorders(SnakeSection head) {
        if (!controller.isInsideRoom(head.getX(), head.getY())) {
            isAlive = false;
        }
    }

    /**
     * Метод проверяет - не совпадает ли голова с каким-нибудь участком тела змеи.
     */
    private void checkBody(SnakeSection head) {
        if (sections.contains(head)) {
            isAlive = false;
        }
    }

    public int getX() {
        return sections.get(0).getX();
    }

    public int getY() {
        return sections.get(0).getY();
    }

    public SnakeDirection getDirection() {
        return direction;
    }

    public void setDirection(SnakeDirection direction) {
        this.direction = direction;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public ArrayList<SnakeSection> getSections() {
        return sections;
    }
}
