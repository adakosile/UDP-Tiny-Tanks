import greenfoot.*;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
public class Server {
    public DatagramSocket socket;
    public static Set<ClientData> clients;
    public Thread rt;
    public Thread st;
    public World world;
    public Server(World world) {
        //super(1000,800,1,false);
        this.world = world;
        clients = new HashSet<ClientData>();
        try {
            socket = new DatagramSocket(50000);
            // START RECEIVE THREAD
            rt = new Thread(new ReceiveThread());
            st = new Thread(new SendThread());
            rt.start();
            st.start();
            // START SEND THREAD
            // ...to be done later
        }

        catch(Exception err) {
            //System.out.println("SERVER CRASHED");
        }
    }

    public void started() {

    }

    public void stopped() {
        //rt.interrupt();
        //st.interrupt();
    }

    public class SendThread implements Runnable {

        public void run() {
            int packetNumber;
            int port;
            try {
                packetNumber = 0;
                port = 52000;
                // Incomplete
                while(!socket.isClosed()) {
                    packetNumber++;
                    // GET A LIST OF TANK POSITIONS
                    // SEND KEYS TO SERVER
                    String str = ""+packetNumber+"_";
                    // if world is start, send nothing
                    if(world instanceof Start) {
                        str += "start_";
                    }
                    else if(world instanceof Arena) {
                        // SEND TANK POSITIONS AND MISSILE POSITIONS
                        str += "arena_";
                        List<Tank> tanks = world.getObjects(Tank.class);
                        List<Missile> missiles = world.getObjects(Missile.class);
                        for(Tank t : tanks) {
                            str+=t.color+"_"+t.getX()+"_"+t.getY()+"_"+t.getRotation()+"_";
                        }
                        for(Missile m : missiles) {
                            str+="missile"+"_"+m.getX()+"_"+m.getY()+"_"+m.getRotation()+"_";
                        }
                        str+="scoreR_"+Score.scoreR+"_scoreG_"+
                        Score.scoreG+"_scoreB_"+Score.scoreB+"_scoreY_"+Score.scoreY+"_";
                    }
                    // if world is winner, send nothing
                    else if(world instanceof Winner) {
                        // SEND TANK POSITIONS AND MISSILE POSITIONS
                        str += "winner_"+((Winner)world).color;
                    }
                    byte[] buf = str.getBytes();
                    for(ClientData client: clients) {
                        DatagramPacket p = new DatagramPacket(buf, buf.length, client.IP, port);
                        socket.send(p);
                    }
                    //System.out.println("PACKET " + p );
                    //System.out.println("SOCKET " + socket );
                    Thread.sleep(20);
                }
            }
            catch(Exception e) {
                //socket.close();
                System.out.println("SERVER SEND CRASHED");
            }

        }
    }

    public class ReceiveThread implements Runnable {

        public void run() {

            while(!socket.isClosed()) {

                try {

                    byte[] buf = new byte[2048];

                    DatagramPacket p = new DatagramPacket(buf, buf.length);

                    socket.receive(p);

                    InetAddress IP = p.getAddress();

                    int port = p.getPort();

                    String msg = new String(p.getData());

                    msg = msg.trim();

                    //System.out.println("SERVER RECEIVED: "+msg);

                    boolean unique = true;

                    for(ClientData client : clients) {
                        if(client.IP.equals(IP) && client.port == port) {
                            unique = false;
                            client.keys = msg;
                            break;
                        }
                    }
                    if(unique) {
                        String color = "";
                        if(clients.size() == 0) {color = "red";}
                        if(clients.size() == 1) {color = "green";}
                        if(clients.size() == 2) {color = "blue";}
                        if(clients.size() == 3) {color = "yellow";}
                        if(clients.size() > 3) {color = "spectator";}
                        ClientData client = new ClientData(IP,port,color);
                        clients.add(client);
                    }
                    /*for(ClientData client : clients) {
                    if(client.IP.equals(IP) && client.port == port) {
                    System.out.println("SERVER RECEIVED FROM "+client.color+" :"+msg);
                    }
                    }*/
                }
                catch(Exception err) {
                    //socket.close();
                    //System.out.println("SERVER RECEIVE CRASHED");
                }
            }
        }
    }
}