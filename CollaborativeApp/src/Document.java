import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Document extends JPanel {
	private static JLabel title;
    private static JTextArea textArea1;
    private static JTextArea textArea2;
    private static JTextArea textArea3;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JButton btn1;
    private JButton btn2;
    private JButton btn3;
    private JLabel editingLabel1;
    private JLabel editingLabel2;
    private JLabel editingLabel3;
    private User user = new User();
    private static EventHandler eventHandler = new EventHandler();
    
    public Document() {
       
    	textArea1 = createTextArea(100,100);
        
        textArea2 = createTextArea(100,250);
        
        textArea3 = createTextArea(100,400);
        
        
        title = new JLabel("Welcome " + user.getName(), SwingConstants.CENTER);
        title.setForeground(Color.red);
        
        label1 = new JLabel ("Zone de Texte 1");
        label2 = new JLabel ("Zone de Texte 2");
        label3 = new JLabel ("Zone de Texte 3");
        
        
        btn1 = createButton(textArea1,"1",575, 125);
        
        btn2 = createButton(textArea2,"2",575, 275);
        
        btn3 = createButton(textArea3,"3",575, 425);
        
        
        editingLabel1 = new JLabel ("Nobody is editing this field");
        editingLabel2 = new JLabel ("Nobody is editing this field");
        editingLabel3 = new JLabel ("Nobody is editing this field");

        //adjust size and set layout
        setPreferredSize (new Dimension (750, 550));
        setLayout (null);

        //add components
        add (title);
        add (textArea1);
        add (textArea2);
        add (textArea3);
        add (label1);
        add (label2);
        add (label3);
        add (btn1);
        add (btn2);
        add (btn3);
        add (editingLabel1);
        add (editingLabel2);
        add (editingLabel3);

        //set component bounds
        title.setBounds(0,20,750,25);
        
        label1.setBounds (100, 75, 100, 25);
        label2.setBounds (100, 225, 100, 25);
        label3.setBounds (100, 375, 100, 25);
        
        editingLabel1.setBounds (380, 75, 250, 25);
        editingLabel2.setBounds (380, 225, 250, 25);
        editingLabel3.setBounds (380, 375, 260, 25);
        
        try {
			MessagingManager.receiveMessage(textArea1, "1");
			MessagingManager.receiveMessage(textArea2, "2");
			MessagingManager.receiveMessage(textArea3, "3");
			
			UsersManager.receiveUserEditing(editingLabel1, "1");
			UsersManager.receiveUserEditing(editingLabel2, "2");
			UsersManager.receiveUserEditing(editingLabel3, "3");
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    public static void main (String[] args) {
        JFrame frame = new JFrame ("Collaborative App");
        frame.setResizable(false);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add (new Document());
        frame.pack();
        frame.setVisible (true);
        
        eventHandler.exitingEvent(frame, textArea1, textArea2, textArea3);
    }

    
    public JTextArea createTextArea(int x,int y) {
    	JTextArea textArea;
    	textArea = new JTextArea (5, 5);
        textArea.setEnabled(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setDisabledTextColor(Color.DARK_GRAY);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBounds (x, y, 450, 100);
        return textArea;
    }
    
    public JButton createButton(JTextArea textArea, String n,int x,int y) {
    	JButton btn;
    	btn = new JButton ("Edit");
        eventHandler.buttonEvent(btn, textArea,editingLabel1,user.getName(),n);
        btn.setBounds (x, y, 110, 25);
    	return btn;
    }
}