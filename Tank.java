import greenfoot.*;
public class Tank extends Actor {
    private int reload;
    private String keys;
    public String color;
    public Tank(String color) {
        this.color = color;
        reload = 0;
        keys = "";
        if(color.equals("red")) {
            setImage("tank_Red.png");
            setRotation(45);
        }
        if(color.equals("green")) {
            setImage("tank_Green.png");
            setRotation(135);
        }
        if(color.equals("blue")) {
            setImage("tank_Blue.png");
            setRotation(-45);
        }
        if(color.equals("yellow")) {
            setImage("tank_Yellow.png");
            setRotation(-135);
        }
        getImage().scale(50,45);
    }

    public void act() {
        // UPDATE KEYS
        for(ClientData client : Server.clients) {
            if(client.color.equals(color)) {
                keys = client.keys;
            }
        }
        // KEYS IS A STRING ARRAY
        // {123, W, SPACE}
        // DRIVING
        if(keys.contains("W")) {
            move(4);
            if(isTouching(Wall.class)) {
                move(-4);
            }
        }
        if(keys.contains("S")) {
            move(-4);
            if(isTouching(Wall.class)) {
                move(4);
            }
        }
        if(keys.contains("A")) {
            turn(-4);
            if(isTouching(Wall.class)) {
                turn(4);
            }
        }
        if(keys.contains("D")) {
            turn(4);
            if(isTouching(Wall.class)) {
                turn(-4);
            }
        }
        
        // FIRE MISSILES
        reload++;
        if(keys.contains("F") && reload >= 50) {
            Greenfoot.playSound("tank_shoot.wav");
            getWorld().addObject(new Missile(getRotation()),getX(),getY());
            reload = 0;
        }
        
        // HIT BY MISSILE
        Actor a = getOneIntersectingObject(Missile.class);
        if(a != null) {
            Greenfoot.playSound("tank_explode.wav");
            getWorld().removeObject(a);
            getWorld().removeObject(this);
        }
    }    
}