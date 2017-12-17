package robots;

public class Robot {

    private static final double POSITION_TOLERANCE = 0.00001;

    private final double posX;
    private final double posY;

    public Robot(double posX, double posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getDistance(Robot other) {
        double xDist = this.getPosX() - other.getPosX();
        double yDist = this.getPosY() - other.getPosY();

        return Math.sqrt(xDist*xDist + yDist*yDist);
    }

    private boolean positionEquals(Robot other, double tolerance) {
        return getDistance(other) < tolerance;
    }

    public boolean positionEquals(Robot other) {
        return positionEquals(other, POSITION_TOLERANCE);
    }

    public String toString() {
        return String.format("R(%.2f,%.2f)", this.posX, this.posY);
    }

}