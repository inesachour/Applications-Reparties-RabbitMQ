import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class EventHandler {
	
	public void buttonEvent(JButton b, JTextArea textArea,JLabel label,String user,String n) {
		b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				if(b.getText() == "Edit") {
					boolean access = AccessManager.getAccess(n, user);
					if(access) {
						textArea.setEnabled(true);
						textArea.setBackground(Color.white);
						b.setText("Stop Editing");
						textAreaEvent(textArea,n);
					}
				}
				else {
					AccessManager.releaseAccess(n);
					textArea.setEnabled(false);
					textArea.setBackground(Color.LIGHT_GRAY);
					b.setText("Edit");
				}
			}
		});
	}
	
	public void textAreaEvent(JTextArea textArea,String n) {
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			public void removeUpdate(DocumentEvent e) {
				trt();
				
			}
			
			public void insertUpdate(DocumentEvent e) {
				trt();
			}
			
			public void changedUpdate(DocumentEvent e) {
				trt();
			}
			
			
			public void trt() {
				try {
					if(textArea.isEnabled()) {
						MessagingManager.sendMessage(textArea.getText(),n);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void exitingEvent(JFrame frame, JTextArea textArea1, JTextArea textArea2, JTextArea textArea3) {
		frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                if(textArea1.isEnabled()) {
                	AccessManager.releaseAccess("1");
                }
                if(textArea2.isEnabled()) {
                	AccessManager.releaseAccess("2");
                }
                if(textArea3.isEnabled()) {
                	AccessManager.releaseAccess("3");
                }
                System.exit(0);
            }
        });
	}

}
