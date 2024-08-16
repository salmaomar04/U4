package View;

public enum ButtonType {
    START("Start"),
    NEW_GAME("New Game"),
    SHOW_HIGH_SCORE_LIST("Show High-Score List");

    private final String label;

    ButtonType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
