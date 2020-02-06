package cellsociety.Visuals;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class StatBox {

    private static final String TITLE_SETUP = "Title: ";
    private static final String AUTHOR_SETUP = "Author: ";
    private static final Color GRID_BACKGROUND = Color.DARKGOLDENROD;
    private static final BackgroundFill STAT_BACKGROUND = new BackgroundFill(GRID_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY);
    private VBox statBox;
    private HashMap<String, Double> myStats;
    private String myTitle;
    private String myAuthor;


    public StatBox(String title, String author, HashMap stats) {
        myTitle = title;
        myAuthor = author;
        myStats = stats;
        renderStatBox();
    }

    private void renderStatBox() {
        statBox = new VBox();
        statBox.setBackground(new Background(STAT_BACKGROUND));

        Label authorLabel = new Label(AUTHOR_SETUP + myAuthor);
        statBox.setVgrow(authorLabel, Priority.ALWAYS);
        Label titleLabel = new Label(TITLE_SETUP + myTitle);
        statBox.setVgrow(titleLabel, Priority.ALWAYS);

        for (String key : myStats.keySet()) {
            Label tempLabel = new Label(key + ": " + myStats.get(key));
            statBox.setVgrow(tempLabel, Priority.ALWAYS);
            statBox.getChildren().add(tempLabel);
        }
    }

    public VBox getStatBox() {
        return statBox;
    }
}
