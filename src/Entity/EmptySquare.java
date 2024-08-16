package Entity;

public class EmptySquare extends BuriedObject {
    public EmptySquare(int row, int col) {
        super(row, col);
    }

    @Override
    public void digObject(Player player, int row, int col) {
        if (!isDug(row, col)) {
            super.digObject(player, row, col);
        }
    }
}
