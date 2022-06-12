import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class AccessManager {

	private final static String QUEUE_NAME = "AcessArea";
	
	public static boolean getAccess(String n, String user){
		
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			int msgs = channel.queueDeclare(QUEUE_NAME+n,false,false,false,null).getMessageCount();
			
			if(msgs >0) {
				return false;
			}
			else {
				String message = user;
				channel.basicPublish("", QUEUE_NAME+n, null, message.getBytes());
				UsersManager.sendUserEditing(n,user);
				return true;
			}
			
		}
		catch(Exception e){
			System.out.println("Exception occured");
			return false;
		}
		
	}
	
	public static void releaseAccess(String n) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			Connection connection = factory.newConnection();
			Channel channel = connection.createChannel();
			channel.queueDeclare(QUEUE_NAME+n,false,false,false,null);
			
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), "UTF-8");
				UsersManager.sendUserEditing(n, "Nobody");
			};
			
			channel.basicConsume(QUEUE_NAME+n, true, deliverCallback, consumerTag -> {});
			channel.close();
			connection.close();
		}
		catch(Exception e){
			System.out.println("Exception occured");
		}
	}
}
