import greenfoot.*;
import java.awt.Color;
import java.awt.Font;
public class Winner extends World {
    private Server server;
    public String color;
    public Winner(String color) {    
        super(1000, 800, 1, false);
        this.color = color;
        GreenfootImage image = getBackground();
        image.setColor(Color.BLACK);

        image.setFont(new Font("Monospaced",Font.BOLD,125));
        image.drawString(color+" wins!",100,250);

        GreenfootImage tankImage = new GreenfootImage("tank_"+color+".png");
        tankImage.scale(50*4,45*4);
        image.drawImage(tankImage,400,300);

        image.setFont(new Font("Monospaced",Font.BOLD,70));
        image.drawString("Press R to restart",150,600);
    }

    public Winner(String color, Server server) {    
        super(1000, 800, 1, false);
        this.color = color;
        this.server = server;
        server.world = this;
        GreenfootImage image = getBackground();
        image.setColor(Color.BLACK);

        image.setFont(new Font("Monospaced",Font.BOLD,125));
        image.drawString(color+" wins!",100,250);

        GreenfootImage tankImage = new GreenfootImage("tank_"+color+".png");
        tankImage.scale(50*4,45*4);
        image.drawImage(tankImage,400,300);

        image.setFont(new Font("Monospaced",Font.BOLD,70));
        image.drawString("Press R to restart",150,600);
    }

    public void stopped() {
        if(server != null) {
            server.rt.interrupt();
            server.st.interrupt();
            server.socket.close();
        }
    }

    public void act() {
        if(Greenfoot.isKeyDown("R")) {
            Greenfoot.setWorld(new Start(server));
        }
    }
}