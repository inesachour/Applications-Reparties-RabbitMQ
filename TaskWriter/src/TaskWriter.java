import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class TaskWriter {
	private final static String QUEUE_Name = "task";
	static Random rd = new Random();
	static String id = String.valueOf(rd.nextInt(9999));
	
	
	public static void main (String[] args) {
        JFrame frame = new JFrame ("Real-Time text Editor");
        frame.setResizable(false);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new Document());
        frame.pack();
        frame.setVisible (true);
        TaskWriter.initialiseConnection();
        exitingEvent(frame);
    }
	
	public static void sendMessage(String message) throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
				
			Channel channel = connection.createChannel()){
			channel.queueDeclare(QUEUE_Name+id,false,false,false,null);
			channel.basicPublish("", QUEUE_Name+id, null, message.getBytes());
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void initialiseConnection() {
		
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		try (Connection connection = factory.newConnection();
				Channel channel = connection.createChannel()){
				channel.queueDeclare("users",false,false,false,null);
				channel.basicPublish("", "users", null,("+"+id).getBytes());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	public static void exitingEvent(JFrame frame) {
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	ConnectionFactory factory = new ConnectionFactory();
        		factory.setHost("localhost");
        		try (Connection connection = factory.newConnection();
        				Channel channel = connection.createChannel()){
        				channel.queueDeclare("users",false,false,false,null);
        				channel.basicPublish("", "users", null,("-"+id).getBytes());
        				
        			} catch (Exception ex) {
        				ex.printStackTrace();
        			}
                System.exit(0);
            }
        });
	}
		
}
