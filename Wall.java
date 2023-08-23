import greenfoot.*;
public class Wall extends Actor {
    public Wall() {
        //
    }
    public void addedToWorld(World world) {
        int x = getX();
        int y = getY();
        int w = getImage().getWidth();
        int h = getImage().getHeight();
        setLocation(x+w/2-x%w,y+h/2-y%h);
    }  
}