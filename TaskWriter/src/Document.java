//Generated by GuiGenie - Copyright (c) 2004 Mario Awad.
//Home Page http://guigenie.cjb.net - Check often for new versions!

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Document extends JPanel {
    private JTextArea textArea1;
    private JLabel label1;

    public Document() {
        
        textArea1 = new JTextArea (5, 5);

        textArea1.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				trt();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				trt();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				trt();
			}
			
			public void trt() {
				try {
					TaskWriter.sendMessage(textArea1.getText());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
        
       
        textArea1.setLineWrap(true);
        textArea1.setWrapStyleWord(true);
        
        
        label1 = new JLabel ("Welcome User "+ TaskWriter.id);

        //adjust size and set layout
        setPreferredSize (new Dimension (752, 330));
        setLayout (null);

        //add components
        add (textArea1);
        add (label1);

        //set component bounds (only needed by Absolute Positioning)
        textArea1.setBounds (150, 60, 450, 150);
        
        label1.setBounds (105, 25, 250, 25);
        
        
    }


    
    
    
    
}
