package snake;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

public class Controller {

    private final int initialDelay = 520;
    private final int delayStep = 20;

    @FXML
    private Canvas canvas;

    @FXML
    private Button startButton;

    @FXML
    private Label countLabel;

    private Scene scene;

    private Snake snake;
    private Mouse mouse;

    private Room room;


    @FXML
    public void initialize() {
        room = new Room(Room.ROOM_SIZE, Room.ROOM_SIZE);
        snake = new Snake(this);
        drawField(FieldType.SNAKE_HEAD, snake.getX(), snake.getY());
        startButton.setOnAction(event -> gameLoop());
    }

    private void gameLoop() {
        snake.setDirection(SnakeDirection.DOWN);
        createMouse();
        new Thread(() -> {
            while (snake.isAlive()) {
                snake.move();
                sleep();
            }
            Platform.runLater(this::endGame);
        }).start();
    }

    private void createMouse() {
        int x = (int) (Math.random() * Room.ROOM_SIZE);
        int y = (int) (Math.random() * Room.ROOM_SIZE);
        // FIXME Иногда мышь появляется на змее...
        while (snake.containsSnake(x, y)) {
            x = (int) (Math.random() * Room.ROOM_SIZE);
            y = (int) (Math.random() * Room.ROOM_SIZE);
        }
        mouse = new Mouse(x, y);
        drawField(FieldType.MOUSE, x, y);
    }

    public void eatMouse() {
        clearField(mouse.getX(), mouse.getY());
        createMouse();
    }

    private void sleep() {
        try {
            int level = snake.getSections().size();
            int delay = level < 15 ? (initialDelay - delayStep * level) : 200;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void setScene(Scene scene) {
        this.scene = scene;
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this::handleKeyEvent);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void handleKeyEvent(KeyEvent event) {
        switch (event.getCode()) {
            case Q:
                System.exit(0);
            case LEFT:
                if (snake.getDirection() != SnakeDirection.RIGHT)
                    snake.setDirection(SnakeDirection.LEFT);
                break;
            case RIGHT:
                if (snake.getDirection() != SnakeDirection.LEFT)
                    snake.setDirection(SnakeDirection.RIGHT);
                break;
            case UP:
                if (snake.getDirection() != SnakeDirection.DOWN)
                    snake.setDirection(SnakeDirection.UP);
                break;
            case DOWN:
                if (snake.getDirection() != SnakeDirection.UP)
                    snake.setDirection(SnakeDirection.DOWN);
                break;
        }
    }

    public void endGame() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game over!");
        alert.setHeaderText(null);
        alert.setContentText(String.format("Your game is over!%nYour snake include %s sections",
                countLabel.getText()));
        alert.showAndWait();
    }

    /**
     * Проверяет находится ли элемент внутри комнаты
     */
    public boolean isInsideRoom(int x, int y) {
        return x >= 0 && x < room.getRoomWidth() && y >= 0 && y < room.getRoomHeight();
    }

    public void drawField(FieldType type, int x, int y) {
        switch (type) {
            case MOUSE:
                canvas.getGraphicsContext2D().setFill(Color.DARKGREEN);
                canvas.getGraphicsContext2D().fillOval(x * Room.FIELD_SIZE + 2.5,
                        y * Room.FIELD_SIZE + 2.5,
                        Room.FIELD_SIZE - 5, Room.FIELD_SIZE - 5);
                break;
            case SNAKE_SECTION:
                canvas.getGraphicsContext2D().setFill(Color.CHOCOLATE);
                canvas.getGraphicsContext2D().fillRect(x * Room.FIELD_SIZE + 2.5,
                        y * Room.FIELD_SIZE + 2.5,
                        Room.FIELD_SIZE - 5, Room.FIELD_SIZE - 5);
                break;
            case SNAKE_HEAD:
                canvas.getGraphicsContext2D().setFill(Color.CHOCOLATE);
                canvas.getGraphicsContext2D().fillOval(x * Room.FIELD_SIZE + 1.5,
                        y * Room.FIELD_SIZE + 1.5,
                        Room.FIELD_SIZE - 3, Room.FIELD_SIZE - 3);
                break;
        }
    }

    public void clearField(int x, int y) {
        canvas.getGraphicsContext2D().clearRect(x * Room.FIELD_SIZE + 1.5,
                y * Room.FIELD_SIZE + 1.5,
                Room.FIELD_SIZE - 3, Room.FIELD_SIZE - 3);
    }

    public void setText(String text) {
        countLabel.setText(text);
    }

    public Mouse getMouse() {
        return mouse;
    }
}
