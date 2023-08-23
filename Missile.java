import greenfoot.*;
public class Missile extends Actor {
    int bounce;
    public Missile(int rotation) {
        bounce = 0;
        setRotation(rotation);
        getImage().scale(25,15);
    }
    public void addedToWorld(World world) {
        move(45);
    }
    public void act() {
        move(8);
        Actor a = getOneIntersectingObject(Wall.class);
        if(a != null) {
            move(-8);
            int deltaX = Math.abs(getX() - a.getX());
            int deltaY = Math.abs(getY() - a.getY());
            if(deltaX > deltaY) {
                // Left/Right Collision
                setRotation( 180-getRotation() );
            }
            else {
                // Top/Bottom Collision
                setRotation( 360-getRotation() );
            }
            bounce++;
            if(bounce == 3) {
                getWorld().removeObject(this);
            }
        }
    }
}