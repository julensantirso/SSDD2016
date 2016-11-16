package ssdd.trackerTracker;

import java.util.Calendar;
import java.util.Enumeration;

import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;

import org.apache.activemq.command.ActiveMQMapMessage;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Keepalive implements MessageListener {

	private int idMessage;

	public Keepalive() {

		this.idMessage = 0;
	}

	public Keepalive(int idMessage) {

		this.idMessage = idMessage;
	}

	public static void main(String[] args) {
		Keepalive keep = new Keepalive();
		keep.sendKeepAlive(0, true);
		keep.EscucharKeepAlive();
	}

	public void EscucharKeepAlive() {
		/*
		 * Se quedara escuchando la señal keepalive, no escucha ninguna en 2seg.
		 * se declarara maestro, en caso contrario, mirara si la señal que
		 * recibe es de algun maestro y si es maestro se queda igual, sino llama
		 * a la Funcion Log de turing.
		 */
		String connectionFactoryName = "TopicConnectionFactory";
		String topicJNDIName = "jndi.ssdd.topic";
		String subscriberID = "SubscriberID";

		TopicConnection topicConnection = null;
		TopicSession topicSession = null;
		TopicSubscriber topicSubscriber = null;

		try {
			Context ctx = new InitialContext();

			// Connection Factories
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx.lookup(connectionFactoryName);

			// Message Destinations
			Topic myTopic = (Topic) ctx.lookup(topicJNDIName);

			// Connections
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicConnection.setClientID("SSDD_TopicSubscriber");
			System.out.println("- Topic Connection created!");

			// Sessions
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("- Topic Session created!");

			// Topic Listener
			topicSubscriber = topicSession.createDurableSubscriber(myTopic, subscriberID, "Filter = '1'", false);
			// TopicSubscriber topicSubscriber2 =
			// topicSession.createSubscriber(myTopic, "Filter = '2'", false);

			// TopicListener topicListener = new TopicListener();
			// topicSubscriber.setMessageListener(this);
			// topicSubscriber2.setMessageListener(topicListener);

			// Begin message delivery
			topicConnection.start();

			// Wait for messages
			System.out.println("- Waiting 10 seconds for messages...");
			Thread.sleep(10000);
		} catch (Exception e) {
			System.err.println("# TopicSubscriberTest Error: " + e.getMessage());
		} finally {
			try {
				// Close resources
				topicSubscriber.close();
				topicSession.unsubscribe(subscriberID);
				topicSession.close();
				topicConnection.close();
				System.out.println("- Topic resources closed!");
			} catch (Exception ex) {
				System.err.println("# TopicSubscriberTest Error: " + ex.getMessage());
			}
		}

		// this.onMessage(message);

		//

	}

	public void sendKeepAlive(int id, boolean esMaster) {

		String connectionFactoryName = "TopicConnectionFactory";
		String topicJNDIName = "jndi.ssdd.topic";

		TopicConnection topicConnection = null;
		TopicSession topicSession = null;
		TopicPublisher topicPublisher = null;

		try {
			// JNDI Initial Context
			Context ctx = new InitialContext();

			// Connection Factory
			TopicConnectionFactory topicConnectionFactory = (TopicConnectionFactory) ctx.lookup(connectionFactoryName);

			// Message Destination
			Topic myTopic = (Topic) ctx.lookup(topicJNDIName);

			// Connection
			topicConnection = topicConnectionFactory.createTopicConnection();
			System.out.println("- Topic Connection created!");

			// Session
			topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
			System.out.println("- Topic Session created!");

			// Message Publisher
			topicPublisher = topicSession.createPublisher(myTopic);
			System.out.println("- TopicPublisher created!");

			// Text Message
			TextMessage textMessage = topicSession.createTextMessage();
			// Message Headers
			// textMessage.setJMSType("TextMessage");
			// textMessage.setJMSMessageID("ID-" + this.idMessage);
			// textMessage.setJMSPriority(1);
			// Message Properties
			textMessage.setStringProperty("Filter", "1");
			// Message Body
			textMessage.setText("<tipo>KA</tipo><ID>" + id + "</ID><master>" + esMaster + "</master>");

			// // Map Message
			// MapMessage mapMessage = topicSession.createMapMessage();
			// // Message Headers
			// mapMessage.setJMSType("MapMessage");
			// mapMessage.setJMSMessageID("ID-"+this.idMessage);
			// mapMessage.setJMSPriority(2);
			// // Message Properties
			// mapMessage.setStringProperty("Filter", "2");
			// // Message Body
			// mapMessage.setString("Text", "Hello World!");
			// mapMessage.setLong("Timestamp",
			// Calendar.getInstance().getTimeInMillis());
			// mapMessage.setBoolean("ACK_required", true);

			// Publish the Messages
			topicPublisher.publish(textMessage);
			System.out.println("- TextMessage published in the Topic!");
			// topicPublisher.publish(mapMessage);
			// System.out.println("- MapMessage sent to the Topic!");
		} catch (Exception e) {
			System.err.println("# TopicPublisherTest Error: " + e.getMessage());
		} finally {
			try {
				// Close resources
				topicPublisher.close();
				topicSession.close();
				topicConnection.close();
				System.out.println("- Topic resources closed!");
			} catch (Exception ex) {
				System.err.println("# TopicPublisherTest Error: " + ex.getMessage());
			}
		}
	}

	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub

		if (message != null) {
			try {
				System.out.println("   - TopicListener: " + message.getClass().getSimpleName() + " received!");

				if (message.getClass().getCanonicalName().equals(ActiveMQTextMessage.class.getCanonicalName())) {
					System.out.println("     - TopicListener: TextMessage '" + ((TextMessage) message).getText());
				} else if (message.getClass().getCanonicalName().equals(ActiveMQMapMessage.class.getCanonicalName())) {
					System.out.println("     - TopicListener: MapMessage");
					MapMessage mapMsg = ((MapMessage) message);

					@SuppressWarnings("unchecked")
					Enumeration<String> mapKeys = (Enumeration<String>) mapMsg.getMapNames();
					String key = null;

					while (mapKeys.hasMoreElements()) {
						key = mapKeys.nextElement();
						System.out.println("       + " + key + ": " + mapMsg.getObject(key));
					}
				}

			} catch (Exception ex) {
				System.err.println("# TopicListener error: " + ex.getMessage());
			}
		}

	}

}
