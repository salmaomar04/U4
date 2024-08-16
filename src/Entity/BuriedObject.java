package Entity;

public class BuriedObject implements IBuriedObject {
    private final int row;
    private final int col;
    private boolean isDug;

    public BuriedObject(int row, int col) {
        this.row = row;
        this.col = col;
        this.isDug = false;
    }

    @Override
    public void digObject(Player player, int row, int col) {
        if (this.row == row && this.col == col && !isDug) {
            isDug = true;
        }
    }

    @Override
    public boolean isDug(int row, int col) {
        return isDug && this.row == row && this.col == col;
    }
}