import java.util.ArrayList;
import java.util.List;

public class Point {

    private int[] position = new int[3];
    private int[] velocity = new int[3];
    private int[] acceleration = new int[3];
    private boolean dead;
    private int deathTime;

    public Point() {
        dead = false;
        deathTime = -1;
    }

    private double getC(int p) {
        return (double) position[p];
    }

    private double getB(int p) {
        return (double) velocity[p] + getA(p);
    }

    private double getA(int p) {
        return (double) acceleration[p]/2;
    }

    public List<Collision> intersect(Point point) {

        double a = getA(0) - point.getA(0);
        double b = getB(0) - point.getB(0);
        double c = getC(0) - point.getC(0);

        double answer1;
        double answer2;

        if(a != 0.0) {
            answer1 = (-b + Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
            answer2 = (-b - Math.sqrt(Math.pow(b, 2) - (4 * a * c))) / (2 * a);
        } else if (b != 0.0){
            answer1 = answer2 = -c/b;
        } else if (c == 0.0){
            answer1 = answer2 = 0;
        } else {
            answer1 = answer2 = -1;
        }


        List<Collision> collisions = new ArrayList<>();
        if (!invalid(answer1) && getReal(point, (int) answer1) > -1) {
            collisions.add(new Collision(this, point, getReal(point, (int) answer1)));
        }
        if (!invalid(answer2) && getReal(point, (int) answer2) > -1) {
            collisions.add(new Collision(this, point, getReal(point, (int) answer2)));
        }

        return collisions;
    }

    private int getReal(Point a, int time) {
        if(compare(a, time - 1, 1) && compare(a, time - 1, 2)) {
            return (time * 3) - 2;
        } else if (compare(a, time, 1) && compare(a, time - 1, 2)) {
            return (time * 3) - 1;
        } else if (compare(a, time, 1) && compare(a, time, 2)) {
            return time;
        } else {
            return -1;
        }
    }

    private boolean compare(Point a, int time, int xyz) {
        return position(time, xyz) == a.position(time, xyz);
    }

    private double position(int time, int xyz) {
        return getA(xyz) * Math.pow(time, 2) + getB(xyz) * time + getC(xyz);
    }

    private boolean isInt(double a) {
        return a % 1 == 0;
    }

    private boolean invalid(double a) {
        return Double.isNaN(a) || a < 0.0 || !isInt(a);
    }

    void setDead(int time) {
        dead = true;
        deathTime = time;
    }

    public boolean isDead(int time) {
        return dead && deathTime != time;
    }

    public boolean isReallyDead() {
        return dead;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public void setVelocity(int[] velocity) {
        this.velocity = velocity;
    }

    public void setAcceleration(int[] acceleration) {
        this.acceleration = acceleration;
    }
}
