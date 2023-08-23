import greenfoot.*;
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import javax.swing.JOptionPane;
public class Client extends World {
    private DatagramSocket socket;
    private InetAddress IP;
    private int port;
    public Thread st;
    public Thread rt;
    public World world;
    public Client() {
        super(1000,800,1,false);
    }

    public void started() {
        try {
            // ASSIGN CLASS VARIABLES
            String ipname = JOptionPane.showInputDialog("Enter the IP Address to connect to. Default is \"127.0.0.1\".");
            if(ipname == null || ipname.equals("")) {
                ipname = "127.0.0.1";
            }
            IP = InetAddress.getByName(ipname);
            socket = new DatagramSocket(52000);
            //System.out.println("ASSIGNED SOCKET AS " + socket);
            // GET A LIST OF KEYS HELD
            st = new Thread(new SendThread());
            rt = new Thread(new ReceiveThread());
            st.start();
            rt.start();
            //System.out.println("Started");
            // SEND KEYS TO SERVER
        }
        catch(Exception e) {
            //System.out.println("CLIENT CRASHED");
        }
    }

    public void stopped() {
        st.interrupt();
        rt.interrupt();
        socket.close();
    }

    public class SendThread implements Runnable{
        int packetNumber;
        //InetAddress IP;
        int port;
        public void run() {
            try {
                packetNumber = 0;
                //IP = InetAddress.getByName("127.0.0.1");
                port = 50000;
                while(!socket.isClosed()) {
                    packetNumber++;
                    // GET A LIST OF KEYS HELD
                    String keysHeld = ""+packetNumber+"_";
                    if(Greenfoot.isKeyDown("W")) {
                        keysHeld+="W_";
                    }
                    if(Greenfoot.isKeyDown("A")) {
                        keysHeld+="A_";
                    }
                    if(Greenfoot.isKeyDown("S")) {
                        keysHeld+="S_";
                    }
                    if(Greenfoot.isKeyDown("D")) {
                        keysHeld+="D_";
                    }
                    if(Greenfoot.isKeyDown("SPACE")) {
                        keysHeld+="F_";
                    }
                    // SEND KEYS TO SERVER
                    byte[] buf = keysHeld.getBytes();
                    DatagramPacket p = new DatagramPacket(buf, buf.length, IP, port);
                    //System.out.println("PACKET " + p );
                    //System.out.println("SOCKET " + socket );
                    socket.send(p);
                    Thread.sleep(20);
                }
            }
            catch(Exception e) {
                //socket.close();
                //System.out.println("CLIENT SEND CRASHED");
            }
        }
    }
    public class ReceiveThread implements Runnable {
        public void run() {
            //System.out.println("CLIENT RECEIVE THREAD ON");
            try {
                while(!socket.isClosed()) {
                    byte[] buf = new byte[2048];
                    DatagramPacket p = new DatagramPacket(buf, buf.length);
                    socket.receive(p);
                    String msg = new String(p.getData());
                    msg = msg.trim();
                    System.out.println("CLIENT RECEIVED: "+msg);
                    String[] arr = msg.split("_");
                    if(arr[1].equals("start")) {
                        if(world == null || !(world instanceof Start)) {
                            world = new Start() {
                                public void started() {}

                                public void stopped() {}

                                public void act() {}
                            };
                            Greenfoot.setWorld(world);
                        }
                    }
                    else if(arr[1].equals("arena")) {
                        if(world == null || !(world instanceof Arena)) {
                            world = new Arena() {
                                public void act() {}
                            };
                            Greenfoot.setWorld(world);
                        }
                        else if(world instanceof Arena) {
                            world.removeObjects(world.getObjects(Tank.class));
                            world.removeObjects(world.getObjects(Missile.class));
                            for(int i=2; i<arr.length-8;i+=4) {
                                Actor a = new Wall(); // Placeholder
                                if(arr[i].equals("red")) {
                                    a = new Tank("red") {
                                        public void act() {}
                                    };
                                    a.setRotation(Integer.parseInt(arr[i+3]));
                                }
                                else if(arr[i].equals("blue")) {
                                    a = new Tank("blue") {
                                        public void act() {}
                                    };
                                    a.setRotation(Integer.parseInt(arr[i+3]));
                                }
                                else if(arr[i].equals("yellow")) {
                                    a = new Tank("yellow") {
                                        public void act() {}
                                    };
                                    a.setRotation(Integer.parseInt(arr[i+3]));
                                }
                                else if(arr[i].equals("green")) {
                                    a = new Tank("green") {
                                        public void act() {}
                                    };
                                    a.setRotation(Integer.parseInt(arr[i+3]));
                                }
                                else if(arr[i].equals("missile")) {
                                    a = new Missile(Integer.parseInt(arr[i+3])) {
                                        public void addedToWorld(World world) {}

                                        public void act() {}
                                    };
                                }
                                world.addObject(a,Integer.parseInt(arr[i+1]),
                                    Integer.parseInt(arr[i+2]));
                            }
                            Score.scoreR = Integer.parseInt(arr[arr.length-7]);
                            Score.scoreG = Integer.parseInt(arr[arr.length-5]);
                            Score.scoreB = Integer.parseInt(arr[arr.length-3]);
                            Score.scoreY = Integer.parseInt(arr[arr.length-1]);
                        }
                    }
                    else if(arr[1].contains("winner")) {
                        if(world == null || !(world instanceof Winner)) {
                            world = new Winner(arr[2])  {
                                public void act() {}
                            };
                            Greenfoot.setWorld(world);
                        }
                    }
                }
            }
            catch(Exception e) {
                //socket.close();
                //System.out.println("CLIENT RECEIVE CRASHED");
            }
        }
    }
}