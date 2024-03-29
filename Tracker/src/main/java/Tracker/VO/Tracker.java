package Tracker.VO;

public class Tracker {
    private String id;
    private String ip;
    private int portPeers;
    private boolean master;

    public Tracker() {
    }
    public Tracker(String id, String ip, int portPeers) {
        this.id = id;
        this.ip = ip;
        this.portPeers = portPeers;
    }
    public Tracker(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setIpAddress(String ip) {
        this.ip = ip;
    }
    public void setPortForPeers(int portForPeers) {
        this.portPeers = portForPeers;
    }
    public boolean isMaster() {
        return master;
    }
    public void setMaster(boolean master) {
        this.master = master;
    }
    public int getPortPeers() {
        return portPeers;
    }
    public String getIp() {
        return ip;
    }

}

