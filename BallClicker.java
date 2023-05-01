package com.example.assignment8;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BallClicker extends Application {
    private int missed = 0, hit = 0, xVelocity = 2;
    private HBox textStuff, buttons;
    private Pane graphics, bottom;
    private Rectangle background;
    private Circle bola;

    private Text scoring, gameover;
    private Button pause, reset;
    private BallAnimation animation;
    private boolean isRunning = true;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        graphics = new Pane();

        background = new Rectangle(0, 0, 400, 400);

        bola = new Circle(-50, 200, 20, Color.YELLOWGREEN);
        bola.setOnMouseReleased(new ClickyEvent());

        scoring = new Text("Hits: " + hit + "   Misses: " + missed);
        textStuff = new HBox(scoring);
        textStuff.setAlignment(Pos.TOP_LEFT);
        scoring.setFill(Color.YELLOWGREEN);
        gameover = new Text(150, 200, "Game Over");
        gameover.setFont(Font.font("Comic Sans MS", FontWeight.BOLD, FontPosture.REGULAR, 36 ));
        gameover.setFill(Color.TRANSPARENT);

        graphics.getChildren().addAll(background, bola, textStuff, gameover);
        animation = new BallAnimation();
        animation.start();

        pause = new Button("Pause");
        pause.setOnAction(new PauseEvent());

        reset = new Button("Reset");
        reset.setOnAction(new ResetEvent());

        buttons = new HBox(40, pause, reset);
        buttons.setAlignment(Pos.CENTER);
        bottom = new Pane(buttons);

        root.setBottom(bottom);
        root.setCenter(graphics);

        Scene scene = new Scene(root);
        primaryStage.setTitle("Ball game");
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    private class BallAnimation extends AnimationTimer {
        @Override
        public void handle(long now) {
            double x = bola.getCenterX();
            if (bola.getCenterX() > 450) { //cannot use == 450 to check
                x = -5;
                missed++;
                scoring.setText("Hits: " + hit + "   Misses: " + missed);
            } else {
                x += xVelocity;
            }
            System.out.println(bola.getCenterX());
            bola.setCenterX(x); //to get it moving, keep moving the centerX

            if (missed == 5) {
                bola.setCenterX(-200);
                xVelocity = 0;
                gameover.setFill(Color.YELLOWGREEN);
                isRunning = false;
            }

        }
    }

    private class PauseEvent implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (e.getSource() == pause && isRunning) {
                animation.stop();
                isRunning = false;
            } else {
                animation.start();
                isRunning = true;
            }
        }
    }

    private class ResetEvent implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent e) {
            if (e.getSource() == reset) {
                animation.stop();
                xVelocity = 1;
                missed = 0;
                hit = 0;
                scoring.setText("Hits: " + hit + "   Misses: " + missed);
                bola.setCenterX(-50);
                bola.setFill(Color.YELLOWGREEN);
                gameover.setFill(Color.TRANSPARENT);
                isRunning = true;
                animation.start();
            }
        }
    }

    private class ClickyEvent implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if(isRunning) {
                if (e.getButton() == MouseButton.SECONDARY && isRunning) {
                    hit++;
                    xVelocity ++;
                    scoring.setText("Hits: " + hit + "   Misses: " + missed);
                    bola.setRadius(bola.getRadius()-5);
                    bola.setCenterX(-50);
                }
                else {
                    hit++;
                    xVelocity += 0.85;
                    scoring.setText("Hits: " + hit + "   Misses: " + missed);
                    bola.setCenterX(-50);
                }
            }

        }
    }
}
