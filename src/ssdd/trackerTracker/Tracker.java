package ssdd.trackerTracker;

import java.util.Date;
import java.util.HashMap;

public class Tracker {

	private String IDTracker;
	private String IPTracker;
	private int PortTracker;
	private int PortPeer;
	private HashMap<String, Tracker> trackersActivos;
	private boolean isMaster;
	private Date lastKeepAlive;

	public Tracker() {
	}

	public Tracker(String iDTracker, String iPTracker, int portTracker, int portPeer, boolean isMaster,
			Date lastKeepAlive) {

		IDTracker = iDTracker;
		IPTracker = iPTracker;
		PortTracker = portTracker;
		PortPeer = portPeer;
		this.isMaster = isMaster;
		this.lastKeepAlive = lastKeepAlive;
	}

	public String getIDTracker() {
		return IDTracker;
	}

	public void setIDTracker(String iDTracker) {
		IDTracker = iDTracker;
	}

	public String getIPTracker() {
		return IPTracker;
	}

	public void setIPTracker(String iPTracker) {
		IPTracker = iPTracker;
	}

	public int getPortTracker() {
		return PortTracker;
	}

	public void setPortTracker(int portTracker) {
		PortTracker = portTracker;
	}

	public int getPortPeer() {
		return PortPeer;
	}

	public void setPortPeer(int portPeer) {
		PortPeer = portPeer;
	}

	public boolean isMaster() {
		return isMaster;
	}

	public void setMaster(boolean isMaster) {
		this.isMaster = isMaster;
	}

	public Date getLastKeepAlive() {
		return lastKeepAlive;
	}

	public void setLastKeepAlive(Date lastKeepAlive) {
		this.lastKeepAlive = lastKeepAlive;
	}

}
