
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;
import javax.swing.event.*;

public class Document extends JPanel {
 
    public HashMap<String,JTextArea> textAreas = new HashMap<String,JTextArea>();
   

    public Document() {
        setPreferredSize (new Dimension (500, 700));
    }
       
    void setTextAreaText(String id,String newText){
    	//textArea.setText(newText);
    	textAreas.get(id).setText(newText);
    	
    }
    
    void addTextArea(String id) {
    	JTextArea textArea = new JTextArea (5, 5);
    	 textArea.setDisabledTextColor(Color.DARK_GRAY);
         textArea.setBackground(Color.LIGHT_GRAY);
         textArea.setEnabled(false);
         textArea.setLineWrap(true);
         textArea.setWrapStyleWord(true);
         textAreas.put(id, textArea);
         textArea.setName(id);
         	
         
         JLabel label = new JLabel("TextArea of user "+id);
         this.add(label);
         this.add(textArea);
         
         textArea.setBounds (150, 50, 450, 150);
         label.setBounds(150, 45, 450, 25);
    }

}
