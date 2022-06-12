
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class TaskReader {

	private final static String QUEUE_NAME = "task";
	private final static ArrayList<String> users = new ArrayList<>();
	
	public static void main(String[] args) throws Exception{
		
		Document doc = new Document();
		JFrame frame = new JFrame ("Real-Time text Editor");
		//frame.setResizable(false);
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add (doc);
		frame.pack();
		frame.setVisible (true);
	    getUsers(doc);
	}
	
	
	public static void taskConnection(Document doc,String id) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		//factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare(QUEUE_NAME+id,false,false,false,null);
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), "UTF-8");
			doc.setTextAreaText(id,message);
		};
		
		channel.basicConsume(QUEUE_NAME+id, true, deliverCallback, consumerTag -> {});
	}

	public static void getUsers(Document doc) throws Exception{
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();
		channel.queueDeclare("users",false,false,false,null);
		
		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String id = new String(delivery.getBody(), "UTF-8");
			try {
				if(id.charAt(0) == '+' ) {
					taskConnection(doc, removeFirst(id));
					doc.addTextArea(removeFirst(id));
				}
				else if(id.charAt(0) == '-'){
					removeTextArea(doc,removeFirst(id));
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
		
		channel.basicConsume("users", true, deliverCallback, consumerTag -> {});
	}
	
	public static void removeTextArea(Document doc,String id) {
		Component[] comps = doc.getComponents();
		for(Component comp : comps) {
			
			if(comp.getName() != null) {
				System.out.println(comp.getName()+ " "+ id);
				if(comp.getName().toString().equals(id)) {
					comp.setBackground(Color.RED);
					doc.setTextAreaText(id,"User Disconnected");
				}
				
			}
		}
	}
	
	public static String removeFirst(String input)
	{
	    return input.substring(1);
	}
	
	
}
