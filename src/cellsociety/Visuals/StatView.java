package cellsociety.Visuals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import java.util.Map;

/**
 * StatView class holds the title, author, and count for each type of state in the Simulation
 * The class holds two HBoxes: one for the general info (title + author) and another for the stats
 * Used in SimulationView and CAController
 * One StatBox is created for each added Simulation
 * @author Eric Doppelt
 */
public class StatView {

    private static final String TITLE_SETUP = "Title: ";
    private static final String AUTHOR_SETUP = "Author: ";
    private static final Color BACKGROUND_COLOR = Color.GREY;
    private static final BackgroundFill STAT_BACKGROUND = new BackgroundFill(BACKGROUND_COLOR, CornerRadii.EMPTY, Insets.EMPTY);
    private VBox statBox;
    private HBox myGeneralInfo;
    private HBox myStatsInfo;
    private Map<String, Integer> myStats;
    private String myTitle;
    private String myAuthor;

    /**
     * Basic constructor takes in the title, author, and stats of the simulation to display
     * Calls renderStatBox to create the VBox
     * @param title is the title of the simulation
     * @param author is the author of the simulation
     * @param stats is a Map connecting each state to its current count in the instance of the simulation
     */
    public StatView(String title, String author, Map stats) {
        myTitle = title;
        myAuthor = author;
        myStats = stats;
        renderStatBox();
    }

    /**
     * Updates the StatBox by wiping the current stats and replacing them with the new counts for each state
     * Called each time a grid updates itself to reflect the new instance
     * @param stats is a Map connecting each state with their new level of counts
     */
    public void updateStats(Map<String, Integer> stats) {
        myStats = stats;
        myStatsInfo.getChildren().clear();

        for (String key : myStats.keySet()) {
            addLabel(key + ": " + myStats.get(key), myStatsInfo);
        }
    }

    /**
     * Basic getter method that returns the VBox holding all of the information for the Simulation
     * @return the instance variable statBox
     */
    public VBox getStatBox() {
        return statBox;
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
}
