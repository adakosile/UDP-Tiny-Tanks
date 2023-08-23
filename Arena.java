import greenfoot.*;
public class Arena extends World {
    private Server server;
    public Arena() {    
        super(1000, 800, 1, true);
        setPaintOrder(Score.class, Wall.class, Tank.class, Missile.class);
        setActOrder(Score.class, Wall.class, Tank.class, Missile.class);
        addObject(new Score(), 500, 15);
        for(int x = 25; x <= 975; x += 50) {
            addObject(new Wall(),x,25);  // Top Wall
            addObject(new Wall(),x,775); // Bot Wall
        }
        for(int y = 75; y <= 725; y += 50) {
            addObject(new Wall(),25,y);  // Left Wall
            addObject(new Wall(),975,y); // Right Wall
        }
        for(int x = -25; x <= 25; x += 50) {
            for(int y = -25; y <= 25; y += 50) {
                addObject(new Wall(),x+100,y+400); // Left Square
                addObject(new Wall(),x+300,y+250); // TopLeft Square
                addObject(new Wall(),x+300,y+550); // BotLeft Square
                addObject(new Wall(),x+500,y+100); // Top Square
                addObject(new Wall(),x+500,y+400); // Mid Square
                addObject(new Wall(),x+500,y+700); // Bottom Square
                addObject(new Wall(),x+700,y+250); // TopRight Square
                addObject(new Wall(),x+700,y+550); // BotRight Square
                addObject(new Wall(),x+900,y+400); // Right Square
            }
        }
        addObject(new Tank("red") {
            public void act() {}
        },100,100);
        addObject(new Tank("green") {
            public void act() {}
        },900,100);
        addObject(new Tank("blue") {
            public void act() {}
        },100,700);
        addObject(new Tank("yellow") {
            public void act() {}
        },900,700);
    }
    
    public void stopped() {
        if(server != null) {
            server.rt.interrupt();
            server.st.interrupt();
            server.socket.close();
        }
    }

    public Arena(Server server) {    
        super(1000, 800, 1, true);
        this.server = server;
        server.world = this;
        setPaintOrder(Score.class, Wall.class, Tank.class, Missile.class);
        setActOrder(Score.class, Wall.class, Tank.class, Missile.class);
        addObject(new Score(server), 500, 15);
        for(int x = 25; x <= 975; x += 50) {
            addObject(new Wall(),x,25);  // Top Wall
            addObject(new Wall(),x,775); // Bot Wall
        }
        for(int y = 75; y <= 725; y += 50) {
            addObject(new Wall(),25,y);  // Left Wall
            addObject(new Wall(),975,y); // Right Wall
        }
        for(int x = -25; x <= 25; x += 50) {
            for(int y = -25; y <= 25; y += 50) {
                addObject(new Wall(),x+100,y+400); // Left Square
                addObject(new Wall(),x+300,y+250); // TopLeft Square
                addObject(new Wall(),x+300,y+550); // BotLeft Square
                addObject(new Wall(),x+500,y+100); // Top Square
                addObject(new Wall(),x+500,y+400); // Mid Square
                addObject(new Wall(),x+500,y+700); // Bottom Square
                addObject(new Wall(),x+700,y+250); // TopRight Square
                addObject(new Wall(),x+700,y+550); // BotRight Square
                addObject(new Wall(),x+900,y+400); // Right Square
            }
        }
        startMatch();
    }

    public void startMatch() {
        addObject(new Tank("red"),100,100);
        addObject(new Tank("green"),900,100);
        addObject(new Tank("blue"),100,700);
        addObject(new Tank("yellow"),900,700);
    }

    public void act() {
        if(getObjects(Tank.class).size() == 1) {
            Tank winningTank = (Tank)getObjects(Tank.class).get(0);
            String winningColor = winningTank.color;
            if(winningColor.equals("red"))   {Score.scoreR++;}
            if(winningColor.equals("green")) {Score.scoreG++;}
            if(winningColor.equals("blue"))  {Score.scoreB++;}
            if(winningColor.equals("yellow")){Score.scoreY++;}
            removeObjects(getObjects(Missile.class));
            removeObjects(getObjects(Tank.class));
            startMatch();
        }
        else if(getObjects(Tank.class).size() == 0) {
            removeObjects(getObjects(Missile.class));
            removeObjects(getObjects(Tank.class));
            startMatch();
        }
    }
}