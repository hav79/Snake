package snake;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("snake_game.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();

        Scene scene = new Scene(root);
        controller.setScene(scene);
        primaryStage.setTitle("Snake game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);

        Canvas canvas = controller.getCanvas();

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeRect(0, 0, 400, 400);
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(0.3);
        for (int i = 1; i < Room.ROOM_SIZE; i++) {
            gc.strokeLine(i * Room.FIELD_SIZE, 0, i * Room.FIELD_SIZE, canvas.getHeight());
            gc.strokeLine(0, i * Room.FIELD_SIZE, canvas.getWidth(), i * Room.FIELD_SIZE);
        }

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
