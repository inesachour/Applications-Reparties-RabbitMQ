import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Envelope;

public class UsersManager {
	
	private final static String Exchange_Name = "ActiveUserArea";

	public static void sendUserEditing(String n, String user) {
		try {
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()){
				channel.exchangeDeclare(Exchange_Name+n, BuiltinExchangeType.FANOUT);
				channel.basicPublish(Exchange_Name+n, "", null, user.getBytes());
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void receiveUserEditing(JLabel label,String n) throws Exception{
		
		ConnectionFactory factory = new ConnectionFactory();
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		
		channel.exchangeDeclare(Exchange_Name+n, BuiltinExchangeType.FANOUT);
		
		String queueName = channel.queueDeclare().getQueue();
		channel.queueBind(queueName, Exchange_Name+n, "");
		
		DeliverCallback deliverCallback = (consumerTag,delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			label.setText(message+" is editing this field");
		};
		
		
		channel.basicConsume(queueName, true ,deliverCallback,consumerTag -> {});
		
	
	}
}
