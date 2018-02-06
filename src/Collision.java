import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Collision {

    private Point a;
    private Point b;
    private int time;

    public Collision(Point a, Point b, int time) {
        this.a = a;
        this.b = b;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public Point getA() {
        return a;
    }

    public Point getB() {
        return b;
    }

    public static void main(String... args) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        List<Point> points = new ArrayList<>();
        Pattern p = Pattern.compile("-?\\d+");

        for (int i = 0; i < 1000; i++) {
            Point point = new Point();
            Matcher m = p.matcher(in.readLine());
            int[] pos = new int[3];
            m.find();
            pos[0] = Integer.parseInt(m.group());
            m.find();
            pos[1] = Integer.parseInt(m.group());
            m.find();
            pos[2] = Integer.parseInt(m.group());

            int[] vel = new int[3];
            m.find();
            vel[0] = Integer.parseInt(m.group());
            m.find();
            vel[1] = Integer.parseInt(m.group());
            m.find();
            vel[2] = Integer.parseInt(m.group());

            int[] acc = new int[3];
            m.find();
            acc[0] = Integer.parseInt(m.group());
            m.find();
            acc[1] = Integer.parseInt(m.group());
            m.find();
            acc[2] = Integer.parseInt(m.group());

            point.setPosition(pos);
            point.setVelocity(vel);
            point.setAcceleration(acc);

            points.add(point);
        }

        in.close();

        List<Collision> collisions = new ArrayList<>();

        for (int i = 0; i < points.size() - 1; i++) {
            for(int j = i+1; j < points.size(); j++) {
                collisions.addAll(points.get(i).intersect(points.get(j)));
            }
        }

        collisions.sort(Comparator.comparingInt(Collision::getTime));

        for (Collision c : collisions) {
            if(!c.getA().isDead(c.getTime()) && !c.getB().isDead(c.getTime())) {
                c.getA().setDead(c.getTime());
                c.getB().setDead(c.getTime());
            }
        }

        System.out.println(points.stream().filter(c -> ! c.isReallyDead()).count());

    }

}
