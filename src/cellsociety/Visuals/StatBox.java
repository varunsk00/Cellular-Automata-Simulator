package cellsociety.Visuals;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class StatBox {

    private static final String TITLE_SETUP = "Title: ";
    private static final String AUTHOR_SETUP = "Author: ";
    private static final Color GRID_BACKGROUND = Color.BLUE;
    private static final BackgroundFill STAT_BACKGROUND = new BackgroundFill(GRID_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY);
    private VBox statBox;
    private Map<String, Double> myStats;
    private String myTitle;
    private String myAuthor;


    public StatBox(String title, String author, Map stats) {
        myTitle = title;
        myAuthor = author;
        myStats = stats;
        renderStatBox();
    }

    private void renderStatBox() {
        statBox = new VBox();
        statBox.setBackground(new Background(STAT_BACKGROUND));
        statBox.setMaxHeight(Double.MAX_VALUE);
        Text authorText = new Text(AUTHOR_SETUP + myAuthor);
        statBox.setVgrow(authorText, Priority.ALWAYS);
        Label titleText = new Label(TITLE_SETUP + myTitle);
        statBox.setVgrow(titleText, Priority.ALWAYS);
        statBox.setMinWidth(30);

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
