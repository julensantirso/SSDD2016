package ssdd.tracker.gestor;

import java.util.ArrayList;
import java.util.List;
import ssdd.tracker.gestor.*;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



public class TopicManager {

	private String connectionFactoryName = "TopicConnectionFactory";
	private String topicKeepAliveMessagesJNDIName = "jndi.ssdd.keepalivemessages";
//	private String topicReadyToStoreMessagesJNDIName = "jndi.ssdd.readytostoremessages";
//	private String topicConfirmToStoreMessagesJNDIName = "jndi.ssdd.confirmtostoremessages";
//	private String topicIncorrectIdMessagesJNDIName = "jndi.ssdd.incorrectidmessages";
//	private String topicCorrectIdMessagesJNDIName = "jndi.ssdd.correctidmessages";

	private TopicConnection topicConnection = null;
	private TopicSession topicSession = null;
	private TopicConnectionFactory topicConnectionFactory = null;

	private Manager manager;
	private Context ctx = null;
	private List<TopicPublisher> topicPublishers = null;
	private List<TopicSubscriber> topicSubscribers = null;

	private static TopicManager instance = null;

	private TopicManager() {
//		globalManager = GlobalManager.getInstance();
		topicPublishers = new ArrayList<TopicPublisher>();
		topicSubscribers = new ArrayList<TopicSubscriber>();

		try {
			ctx = new InitialContext();
			topicConnectionFactory = (TopicConnectionFactory) ctx
					.lookup(connectionFactoryName);
			topicConnection = topicConnectionFactory.createTopicConnection();
			topicSession = topicConnection.createTopicSession(false,
					Session.AUTO_ACKNOWLEDGE);

		} catch (NamingException e) {
			System.err
					.println("# Name Exception Error (constructor TopicManager) "
							+ e.getMessage());
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (constructor TopicManager) "
							+ e.getMessage());
		}

	}
/*
 * 
 * Funci�n que devuelve la instancia actual de TopicManager en caso de que exista. Sino genera una nueva
 * 
 * */
	public static TopicManager getInstance() {
		if (instance == null) {
			instance = new TopicManager();
		}
		return instance;
	}

	
/*
 * 
 * Funci�n para el env�o del mensaje KeepAlive
 * 
 * */
	public void sendKeepAliveMessage() {
		try {
			if (instance != null) {
				Topic topicKeepAliveMessages = (Topic) ctx
						.lookup(topicKeepAliveMessagesJNDIName);
				
				// Connection
				topicConnection = topicConnectionFactory.createTopicConnection();
				System.out.println("- Topic Connection created!");

				// Session
				topicSession = topicConnection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
				System.out.println("- Topic Session created!");

				// Message Publisher
				TopicPublisher topicPublisher = topicSession.createPublisher(topicKeepAliveMessages);
				System.out.println("- TopicPublisher created!");

				topicPublishers.add(topicPublisher);
				
				manager = new Manager();
				// Map Message
				MapMessage mapMessage = topicSession.createMapMessage();

				// Message Properties
				mapMessage.setStringProperty("TypeMessage", "KeepAlive");

				// Message Body
				mapMessage.setString("Id",manager.getTracker().getIDTracker());
				mapMessage.setBoolean("Master", manager.getTracker().isMaster());

				topicPublisher.publish(mapMessage);
				System.out.println("- MapMessage sent to the Topic!");
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (publishKeepAliveMessage) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Name Exception Error (publishKeepAliveMessage) "
							+ e.getMessage());
		}

	}

	public void publishReadyToStoreMessage( long connectionId ) {
		try {
			if (instance != null) {
				TopicManager topicReadyToStoreMessages = (TopicManager) ctx
						.lookup(topicReadyToStoreMessagesJNDIName);

				TopicPublisher topicPublisher = topicSession
						.createPublisher(topicReadyToStoreMessages);
				topicPublishers.add(topicPublisher);
				// Map Message
				MapMessage mapMessage = topicSession.createMapMessage();

				// Message Properties
				mapMessage.setStringProperty("TypeMessage",
						Constants.TYPE_READY_TO_STORE_MESSAGE);

				// Message Body
				mapMessage.setString("Id", getTracker().getId());
				mapMessage.setLong("ConnectionId", connectionId );
				topicPublisher.publish(mapMessage);
				System.out.println("- MapMessage sent to the Topic!");
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (publishReadyToStoreMessage) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Name Exception Error (publishReadyToStoreMessage) "
							+ e.getMessage());
		}
	}

	public void publishConfirmToStoreMessage( long connectionId ) {
		try {
			if (instance != null) {
				TopicManager topicConfirmToStoreMessages = (TopicManager) ctx
						.lookup(topicConfirmToStoreMessagesJNDIName);

				TopicPublisher topicPublisher = topicSession
						.createPublisher(topicConfirmToStoreMessages);
				topicPublishers.add(topicPublisher);
				// Map Message
				MapMessage mapMessage = topicSession.createMapMessage();

				// Message Properties
				mapMessage.setStringProperty("TypeMessage",
						Constants.TYPE_CONFIRM_TO_STORE_MESSAGE);

				// Message Body
				mapMessage.setString("Id", getTracker().getId());
				mapMessage.setLong("ConnectionId", connectionId );
				topicPublisher.publish(mapMessage);
				System.out.println("- MapMessage sent to the Topic!");
				
			}

		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (publishConfirmToStoreMessage) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Name Exception Error (publishConfirmToStoreMessage) "
							+ e.getMessage());
		}
	}

	public void publishCorrectIdMessage(String destinationId) {
		try {
			if (instance != null) {
				TopicManager topicConfirmToStoreMessages = (TopicManager) ctx
						.lookup(topicCorrectIdMessagesJNDIName);

				TopicPublisher topicPublisher = topicSession
						.createPublisher(topicConfirmToStoreMessages);
				topicPublishers.add(topicPublisher);

				MapMessage mapMessage;

				mapMessage = topicSession.createMapMessage();
				// Message Properties
				mapMessage.setStringProperty("TypeMessage",
						Constants.TYPE_CORRECT_ID_MESSAGE);
				mapMessage.setStringProperty("DestinationId", destinationId);
				// Message Body
				mapMessage.setString("Id", destinationId);

				// Send the Messages
				topicPublisher.publish(mapMessage);
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (publishConfirmToStoreMessage) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Name Exception Error (publishConfirmToStoreMessage) "
							+ e.getMessage());
		}

	}

	public void subscribeTopicKeepAliveMessages(
			RedundancyManager redundancyManager) {
		try {
			if (instance != null) {
				TopicManager topicKeepAliveMessages = (TopicManager) ctx
						.lookup(topicKeepAliveMessagesJNDIName);

				TopicSubscriber topicSubscriber = topicSession
						.createSubscriber(topicKeepAliveMessages);
				topicSubscribers.add(topicSubscriber);
				topicSubscriber.setMessageListener(redundancyManager);
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (subscribeTopicKeepAliveMessages) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Naming Exception Error (subscribeTopicKeepAliveMessages) "
							+ e.getMessage());
		}
	}

	public void subscribeTopicReadyToStoreMessages(
			RedundancyManager redundancyManager) {
		try {
			if (instance != null) {
				TopicManager topicReadyToStoreMessages = (TopicManager) ctx
						.lookup(topicReadyToStoreMessagesJNDIName);
				TopicSubscriber topicSubscriber = topicSession
						.createSubscriber(topicReadyToStoreMessages);
				topicSubscribers.add(topicSubscriber);
				topicSubscriber.setMessageListener(redundancyManager);
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (subscribeTopicReadyToStoreMessages) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Naming Exception Error (subscribeTopicReadyToStoreMessages) "
							+ e.getMessage());
		}
	}

	public void subscribeTopicConfirmToStoreMessages(
			RedundancyManager redundancyManager) {
		try {
			if (instance != null) {
				TopicManager topicConfirmToStoreMessages = (TopicManager) ctx
						.lookup(topicConfirmToStoreMessagesJNDIName);
				TopicSubscriber topicSubscriber = topicSession
						.createSubscriber(topicConfirmToStoreMessages);
				topicSubscribers.add(topicSubscriber);
				topicSubscriber.setMessageListener(redundancyManager);
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (subscribeTopicConfirmToStoreMessages) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Naming Exception Error (subscribeTopicConfirmToStoreMessages) "
							+ e.getMessage());
		}
	}

	public void subscribeTopicIncorrectIdMessages(
			RedundancyManager redundancyManager) {
		try {
			if (instance != null) {
				TopicManager topicIncorrectIdMessages = (TopicManager) ctx
						.lookup(topicIncorrectIdMessagesJNDIName);

				TopicSubscriber topicSubscriber = topicSession
						.createSubscriber(topicIncorrectIdMessages);
				topicSubscribers.add(topicSubscriber);
				topicSubscriber.setMessageListener(redundancyManager);
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (subscribeTopicIncorrectIdMessages) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Naming Exception Error (subscribeTopicIncorrectIdMessages) "
							+ e.getMessage());
		}
	}

	public void subscribeTopicCorrectIdMessages(
			RedundancyManager redundancyManager) {
		try {
			if (instance != null) {
				TopicManager topicCorrectIdMessages = (TopicManager) ctx
						.lookup(topicCorrectIdMessagesJNDIName);

				TopicSubscriber topicSubscriber = topicSession
						.createSubscriber(topicCorrectIdMessages);
				topicSubscribers.add(topicSubscriber);
				topicSubscriber.setMessageListener(redundancyManager);
			}
		} catch (JMSException e) {
			System.err
					.println("# JMS Exception Error (subscribeTopicIncorrectIdMessages) "
							+ e.getMessage());
		} catch (NamingException e) {
			System.err
					.println("# Naming Exception Error (subscribeTopicIncorrectIdMessages) "
							+ e.getMessage());
		}
	}

	public void start() throws JMSException {
		topicConnection.start();
	}

	public void close() {
		try {
			for (TopicSubscriber topicSubscriber : topicSubscribers) {
				topicSubscriber.close();
			}
			for (TopicPublisher topicPublisher : topicPublishers) {
				topicPublisher.close();
			}

			if (topicSession != null)
				topicSession.close();
			if (topicConnection != null)
				topicConnection.close();
			instance = null;
		} catch (JMSException e) {
			System.err.println("* JMS Exception Error (close): "
					+ e.getMessage());
		}
	}

	private Tracker getTracker() {
		return globalManager.getTracker();
	}
}
