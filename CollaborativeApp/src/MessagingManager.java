import javax.swing.JTextArea;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class MessagingManager {

private final static String Exchange_Name = "TextAreaExchange";
	
	public static void sendMessage(String message,String n) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
			Channel channel = connection.createChannel()){
			channel.exchangeDeclare(Exchange_Name+n, BuiltinExchangeType.FANOUT);
			channel.basicPublish(Exchange_Name+n, "", null, message.getBytes());
		}
		
	}
	
	
	public static void receiveMessage(JTextArea textArea,String n) throws Exception{
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(Exchange_Name+n, BuiltinExchangeType.FANOUT);
		
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, Exchange_Name+n, "");
		
		DeliverCallback deliverCallback = (consumerTag,delivery) -> {
			if(textArea.isEnabled()==false) {
				String message = new String(delivery.getBody(), "UTF-8");
				textArea.setText(message);
			}
			
			
		};

		channel.basicConsume(queueName, true ,deliverCallback,consumerTag -> {});
	}
	
}
