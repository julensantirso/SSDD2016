package ssdd.tracker.gestor;



import ssdd.trackerTracker.Tracker;

public class Manager {
	private Tracker tracker;
	private static Manager instance;
	private TopicManager topic;
	
	public Tracker getTracker() {
		return tracker;
	}
	public void setTracker(Tracker tracker) {
		this.tracker = tracker;
	}
	
	public static Manager getInstance() {
		if (instance == null) {
			instance = new Manager();
		}
		return instance;
	}
	
}
