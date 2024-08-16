package Entity;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameManager {
    private final List<Player> players;
    private final List<Player> highScores;
    private int currentPlayerIndex;
    private final String highScoreFile = "highscores.txt";

    public GameManager() {
        players = new ArrayList<>();
        highScores = new ArrayList<>();
        loadHighScores();
    }

    public void setupPlayers() {
        players.clear();
        String player1Name = JOptionPane.showInputDialog("Enter name for Player 1:");
        String player2Name = null;
        while (player2Name == null || player2Name.trim().isEmpty() || player2Name.equals(player1Name)) {
            player2Name = JOptionPane.showInputDialog("Enter name for Player 2 (different from Player 1):");

            if (player2Name.equals(player1Name)) {
                JOptionPane.showMessageDialog(null, "Player 2's name cannot be the same as Player 1's name. Please enter a different name.");
            }
        }
        players.add(new Player(player1Name, 0));
        players.add(new Player(player2Name, 0));
        currentPlayerIndex = 0;
    }

    public Player getCurrentPlayer() {
        return players.get(currentPlayerIndex);
    }

    public void switchToNextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
    }

    public void updateHighScores() {
        Player winner = determineWinner();
        if (winner != null) {

            boolean alreadyInHighScores = highScores.stream()
                    .anyMatch(player -> player.getName().equals(winner.getName()));
            if (!alreadyInHighScores) {
                highScores.add(winner);
                highScores.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));
                if (highScores.size() > 10) {
                    highScores.subList(10, highScores.size()).clear();
                }
                saveHighScores();
            }
        }
    }

    public List<Player> getHighScores() {
        return new ArrayList<>(highScores);
    }

    private void loadHighScores() {
        try (BufferedReader reader = new BufferedReader(new FileReader(highScoreFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String name = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    highScores.add(new Player(name, score));
                }
            }
        } catch (IOException e) {
            System.out.println("High scores file not found. A new one will be created.");
        }
    }

    private void saveHighScores() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(highScoreFile))) {
            for (Player player : highScores) {
                writer.write(player.getName() + ": " + player.getScore());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving high scores.");
        }
    }

    private Player determineWinner() {
        return players.stream()
                .max(Comparator.comparingInt(Player::getScore))
                .orElse(null);
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(players);
    }
}