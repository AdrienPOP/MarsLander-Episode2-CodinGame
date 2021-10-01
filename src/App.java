import java.util.*;

class Player {
    public static void main(String args[]) {
       Game game = new Game();
    }
}

class Game {
    private Scanner in = new Scanner(System.in);
    private int landX;
    private int landY;
    private int surfaceN = in.nextInt();
    private ArrayList<Point> surface = new ArrayList<Point>();
    private LandScape landscape;
    private Rocket rocket;

    public Game (){
        this.initializedGame();
        this.startGame();
    }

    public void initializedGame(){

        for (int i = 0; i < surfaceN; i++) {
                landX = in.nextInt(); 
                landY = in.nextInt(); 
                surface.add(new Point(landX, landY));
            }
        for (int i = 0; i < surface.size() - 1; i++){
            if (surface.get(i).getY() == surface.get(i + 1).getY()){
                landscape = new LandScape(surface.get(i), surface.get(i + 1));
            }
        }
        rocket = new Rocket(landscape);
    }

    public void startGame(){

         while (true) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt(); // the thrust power (0 to 4).

            if(rocket.isLanding(X, Y)){
                rocket.landing(vSpeed, hSpeed, Y, X);

            } else if(rocket.rocketIsTooFast(hSpeed)){
                rocket.breakingRocketSpeed(hSpeed);
            } else {
                System.out.println(Integer.toString((int) rocket.calculateRotateRocket(rocket.distanceVectorForLandscape(X, Y), Y)) + " " + 4);

            }
        }
    }
}

class Rocket {

    Point target;
    private int xRocket;
    int powerRocket = 4;
    LandScape landscape;
    int speed;

    public Rocket(LandScape landscape) {
        this.landscape = landscape;
    }

    public void landing(int vSpeed, int hSpeed, int yRocket, int xRocket) {
        double rotate;
        
       if(hSpeed < -5 || hSpeed > 5){
            double vectorOfSpeed = Math.sqrt(Math.pow((double) hSpeed, 2) + Math.pow((double) vSpeed, 2));
            rotate = Math.toDegrees(Math.sin(hSpeed/vectorOfSpeed));
            System.out.println((int)rotate + " " +  4);
        } else if(yRocket > landscape.getFinishPoint().getY() + 1000 && vSpeed < 35){
            System.out.println(Integer.toString((int) this.calculateRotateRocket(this.distanceVectorForLandscape(xRocket, yRocket), yRocket)) + " " + 3);
        } else {
            System.out.println("0 4");
        }
    }

    public boolean isLanding(int xRocket, int yRocket) {
        if(xRocket > landscape.getStartPoint().getX() - 500 && xRocket < landscape.getFinishPoint().getX() + 500){
            return true;
        }
        return false;
    }

    public boolean rocketIsTooFast(int hSpeed){
        if(hSpeed < -80 || hSpeed > 80) {
            return true;
        } else{
            return false;
        }
    }

    public double distanceVectorForLandscape(int xRocket, int yRocket) {
        double distance;
        this.xRocket = xRocket;
        this.target = new Point((this.landscape.startPoint.getX() + this.landscape.finishPoint.getX()) / 2, this.landscape.startPoint.getY());
        distance = Math.sqrt(Math.pow((double) xRocket - target.getX(), 2.0) + Math.pow((double) yRocket - target.getY(), 2.0));
        return distance;
    }

    public double calculateRotateRocket(double distance, int yRocket){
        double rotate = Math.toDegrees(Math.sin((yRocket - target.getY())/distance));
        if(this.target.getX() > this.xRocket){
            return rotate * (-1);
        } else {
            return rotate;
        }
    }

    public void breakingRocketSpeed(int hSpeed){
        if (hSpeed < -70 ) {
            System.out.println(-70 + " " + 4);
        }
        if (hSpeed > 70){
            System.out.println(70 + " " + 4);
            
        }
    }
}

class LandScape{

   Point startPoint;
   Point finishPoint;

    public LandScape(Point startPoint, Point finishPoint){
        this.startPoint = startPoint;
        this.finishPoint = finishPoint;
    }
    public Point getStartPoint(){
        return this.startPoint;
    }
    public Point getFinishPoint(){
        return this.finishPoint;
    }
}

class Point {
    private int x;
    private int y;

    public Point (int x, int y){
        this.x = x;
        this.y = y;
    }
    public int getX(){
        return this.x;
    }
    public int getY(){
        return this.y;
    }
}