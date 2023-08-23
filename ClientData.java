import java.net.InetAddress;
public class ClientData {
    public InetAddress IP;
    public int port;
    public String color;
    public String keys;
    public ClientData(InetAddress IP, int port, String color) {
        this.IP = IP;
        this.port = port;
        this.color = color;
        this.keys = "";
    }
}
