package Entity;

public class Trap extends BuriedObject {
    private final int penaltyPoints;

    public Trap(int penaltyPoints, int row, int col) {
        super(row, col);
        this.penaltyPoints = penaltyPoints;
    }

    @Override
    public void digObject(Player player, int row, int col) {
        if (!isDug(row, col)) {
            super.digObject(player, row, col);
            player.addScore(-penaltyPoints);
            System.out.println("You have hit a trap and lost " + penaltyPoints + " points!");
        }
    }
}
