import greenfoot.*;
import java.awt.Color;
public class Score extends Actor{
    public static int scoreR, scoreG, scoreB, scoreY;
    private Server server;
    public Score() {
        scoreR = scoreG = scoreB = scoreY = 0;
        String text = "Red: "+scoreR+" / Green: "+scoreG+" / Blue: "+scoreB+" / Yellow: "+scoreY;
        GreenfootImage image = new GreenfootImage(text,35,Color.WHITE,Color.BLACK);
        setImage(image);
    }

    public Score(Server server) {
        this.server = server;
        scoreR = scoreG = scoreB = scoreY = 0;
        String text = "Red: "+scoreR+" / Green: "+scoreG+" / Blue: "+scoreB+" / Yellow: "+scoreY;
        GreenfootImage image = new GreenfootImage(text,35,Color.WHITE,Color.BLACK);
        setImage(image);
    }

    public void act() {
        String text = "Red: "+scoreR+" / Green: "+scoreG+" / Blue: "+scoreB+" / Yellow: "+scoreY;
        GreenfootImage image = new GreenfootImage(text,35,Color.WHITE,Color.BLACK);
        setImage(image);
        if(server != null) {
            if(scoreR == 3) {Greenfoot.setWorld(new Winner("Red",server));}
            if(scoreG == 3) {Greenfoot.setWorld(new Winner("Green",server));}
            if(scoreB == 3) {Greenfoot.setWorld(new Winner("Blue",server));}
            if(scoreY == 3) {Greenfoot.setWorld(new Winner("Yellow",server));}
        }
    }    
}