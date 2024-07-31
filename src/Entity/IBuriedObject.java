package Entity;

public interface IBuriedObject {
    void digObject(Player player, int row, int col);
    boolean isDug(int row, int col);
    int[] getPosition();
}
