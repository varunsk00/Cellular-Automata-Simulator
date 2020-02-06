package cellsociety.Visuals;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.HashMap;

public class StatColumn {

    private static final String TITLE_SETUP = "Title: ";
    private static final Color GRID_BACKGROUND = Color.DARKGOLDENROD;
    private static final BackgroundFill STAT_BACKGROUND = new BackgroundFill(GRID_BACKGROUND, CornerRadii.EMPTY, Insets.EMPTY);
    private VBox statBox;
    private HashMap<String, Double> myStats;
    private String myTitle;


    public StatColumn(String title, HashMap stats) {
        myTitle = title;
        myStats = stats;
        renderStatBox();
    }

    private void renderStatBox() {
        statBox = new VBox();
        statBox.setBackground(new Background((STAT_BACKGROUND));

        statBox.getChildren().add(new Label(TITLE_SETUP + myTitle));

        for (String key : myStats.keySet()) {
            Label tempLabel = new Label(key + ": " + myStats.get(key));
            statBox.setVgrow(tempLabel, true);
            statBox.getChildren().add(tempLabel);
        }
    }


}
