package Entity;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private final List<Player> players;
    private int currentPlayerIndex;
    private List<String> highScores;
    private final String highScoreFile = "highscores.txt";

    public GameManager() {
        players = new ArrayList<>();
        highScores = new ArrayList<>();
        loadHighScores();
    }

    public void setupPlayers() {
        players.clear();
        String player1Name = JOptionPane.showInputDialog("Enter name for Player 1:");
        String player2Name = JOptionPane.showInputDialog("Enter name for Player 2:");
        players.add(new Player(player1Name));
        players.add(new Player(player2Name));
        currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void switchToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void updateHighScores() {
        players.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        highScores.clear();
        for (Player player : players) {
            highScores.add(player.getName() + ": " + player.getScore());
        }

        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);
        }

        saveHighScores();
    }

    public List<String> getHighScores() {
        return highScores;
    }

    private void loadHighScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                highScores.add(line);
            }
        } catch (IOException e) {
            System.out.println("High scores file not found. A new one will be created.");
        }
    }

    private void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            for (String highScore : highScores) {
                writer.write(highScore);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving high scores.");
        }
    }

    public void resetScores() {
        for (Player player : players) {
            player.resetScore();
        }
    }
}
