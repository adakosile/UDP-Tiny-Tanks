import greenfoot.*;
import java.awt.Color;
import java.awt.Font;
public class Start extends World {
    public static GreenfootSound music;
    private Server server;
    public Start() {
        super(1000, 800, 1, false);
        GreenfootImage image = getBackground();
        image.setColor(Color.BLACK);

        image.setFont(new Font("Monospaced",Font.BOLD,125));
        image.drawString("Tiny Tanks",130,190);

        image.setFont(new Font("Monospaced",Font.ITALIC,50));
        image.drawString("Red: WSAD, E to fire\n"+
            "Green: IKJL, O to fire\n"+
            "Blue: Arrows, Enter to fire\n"+
            "Yellow: 8546, 7 to fire",100,310);

        image.setFont(new Font("Monospaced",Font.BOLD,45));
        image.drawString("The first one to 3 wins",200,600);

        image.setFont(new Font("Monospaced",Font.BOLD,75));
        //image.drawString("Waiting for Server!",60,675);
        image.drawString("Press SPACE to Play!",60,675); 
        if(music == null || music.isPlaying() == false) {
            music = new GreenfootSound("game_music.wav");
        }
    }

    public Start(Server server) {    
        super(1000, 800, 1, false);
        this.server = server;
        server.world = this;
        GreenfootImage image = getBackground();
        image.setColor(Color.BLACK);

        image.setFont(new Font("Monospaced",Font.BOLD,125));
        image.drawString("Tiny Tanks",130,190);

        image.setFont(new Font("Monospaced",Font.ITALIC,50));
        image.drawString("Red: WSAD, E to fire\n"+
            "Green: IKJL, O to fire\n"+
            "Blue: Arrows, Enter to fire\n"+
            "Yellow: 8546, 7 to fire",100,310);

        image.setFont(new Font("Monospaced",Font.BOLD,45));
        image.drawString("The first one to 3 wins",200,600);

        image.setFont(new Font("Monospaced",Font.BOLD,75));
        //image.drawString("Waiting for Server!",60,675);
        image.drawString("Press SPACE to Play!",60,675); 
        if(music == null || music.isPlaying() == false) {
            music = new GreenfootSound("game_music.wav");
        }
    }

    public void started() {
        server = new Server(this);
        server.world = this;
    }

    public void stopped() {
        if(server != null) {
            server.rt.interrupt();
            server.st.interrupt();
            server.socket.close();
        }
    }

    public void act() {
        if(Greenfoot.isKeyDown("SPACE")) {
            Greenfoot.setWorld(new Arena(server));
        }
        if(music.isPlaying() == false) {
            music.playLoop();
        }
    }
}