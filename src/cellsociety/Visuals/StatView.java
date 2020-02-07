package cellsociety.Visuals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;

public class StatView {

    private static final String TITLE_SETUP = "Title: ";
    private static final String AUTHOR_SETUP = "Author: ";
    // resource bundle
    private static final Color BACKGROUND_COLOR = Color.GREY;
    private static final BackgroundFill STAT_BACKGROUND = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    private VBox statBox;
    private HBox myGeneralInfo;
    private HBox myStatsInfo;
    private Map<String, Integer> myStats;
    private String myTitle;
    private String myAuthor;


    public StatView(String title, String author, Map stats) {
        myTitle = title;
        myAuthor = author;
        myStats = stats;
        renderStatBox();
    }

    public void updateStats(Map<String, Integer> stats) {
        myStats = stats;
        myStatsInfo.getChildren().clear();

        for (String key : myStats.keySet()) {
            addLabel(key + ": " + myStats.get(key), myStatsInfo);
        }
    }

    private void renderStatBox() {
        statBox = new VBox();
        statBox.setBackground(new Background(STAT_BACKGROUND));
        visualizeInfo();
        visualizeStats();

        statBox.getChildren().add(myGeneralInfo);
        statBox.getChildren().add(myStatsInfo);
    }

    private void visualizeInfo() {
        myGeneralInfo = new HBox();
        addLabel(AUTHOR_SETUP + myAuthor, myGeneralInfo);
        addLabel(TITLE_SETUP + myTitle, myGeneralInfo);
    }

    private void visualizeStats() {
        myStatsInfo = new HBox();
        for (String key : myStats.keySet()) {
            addLabel(key + ": " + myStats.get(key), myStatsInfo);
        }
    }

    private void addLabel(String info, HBox hbox) {
        Label addedLabel = new Label(info);
        addedLabel.setMaxWidth(Double.MAX_VALUE);
        addedLabel.setAlignment(Pos.CENTER);
        hbox.setHgrow(addedLabel, Priority.ALWAYS);
        hbox.getChildren().add(addedLabel);
    }

    public VBox getStatBox() {
        return statBox;
    }
}
