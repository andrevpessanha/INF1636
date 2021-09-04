package view;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;

import tools.UIColors;

public class PaintComponent {
	
	public static void setButtonColor(JButton button, UIColors cor) {
		if(cor == UIColors.RED) {
			button.setBackground(Color.decode("#f44141"));
		}
		else if(cor == UIColors.BLUE) {
			button.setBackground(Color.decode("#4286f4"));
		}
		else if(cor == UIColors.ORANGE) {
			button.setBackground(Color.decode("#e5a419"));	
		}
		else if(cor == UIColors.YELLOW) {
			button.setBackground(Color.decode("#dbc84e"));
		}
		else if(cor == UIColors.PURPLE) {
			button.setBackground(Color.decode("#9d6ee5"));
		}
		else if(cor == UIColors.BLACK) {
			button.setBackground(Color.decode("#726f6f"));
		}
	}
	
	public static void setLabelColor(JLabel label, UIColors cor) {
		if(cor == UIColors.RED) {
			label.setForeground(Color.decode("#f44141"));
		}
		else if(cor == UIColors.BLUE) {
			label.setForeground(Color.decode("#4286f4"));
		}
		else if(cor == UIColors.ORANGE) {
			label.setForeground(Color.decode("#e5a419"));	
		}
		else if(cor == UIColors.YELLOW) {
			label.setForeground(Color.decode("#dbc84e"));
		}
		else if(cor == UIColors.PURPLE) {
			label.setForeground(Color.decode("#9d6ee5"));
		}
		else if(cor == UIColors.BLACK) {
			label.setForeground(Color.decode("#726f6f"));
		}
	}
}
