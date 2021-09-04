package view;

import java.awt.Window;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class ScreenPopup {
	
	public static void showMessageDialog (String message, String title) {
	    JOptionPane.showMessageDialog(null, message, title, JOptionPane.PLAIN_MESSAGE);
	}
	
	public static int showNumIntervalDialog(Window window, String message, String title, int min, int max) {
		List<String> optionList = new ArrayList<String>();
	    for(int i = min; i <= max; i++) {
	      optionList.add(Integer.toString(i));
	    }
	    Object[] options = optionList.toArray();
	    String resp = (String) JOptionPane.showInputDialog(window, message, title, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    
	    return resp != null ? Integer.parseInt(resp) : -1;
	 }
	
	public static String showItemIntervalDialog(Window window, String message, String title, ArrayList<String> itens) {
		List<String> optionList = new ArrayList<String>();
	    for(String item : itens) {
	      optionList.add(item);
	    }
	    Object[] options = optionList.toArray();
	    String resp = (String) JOptionPane.showInputDialog(window, message, title, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
	    
	    return resp != null ? resp == "Banco" ? "Banco" : resp.substring(resp.length() - 1) : "cancel";
	 }
	
	public static String showInputDialog(Window screen, String message, String title) {
		return (String)JOptionPane.showInputDialog(screen, message, title, JOptionPane.QUESTION_MESSAGE, null, null, null);
	}
	
	public static int showConfirmDialog(Window window, String message, String title) {
	    UIManager.put("OptionPane.yesButtonText", "Sim");
	    UIManager.put("OptionPane.noButtonText", "Não");
	    
	    return JOptionPane.showConfirmDialog(window, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
	}
}
