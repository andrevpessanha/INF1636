package view;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import view.Screen;

public class ScreenPins {
	private static ArrayList<JLabel> pins = new ArrayList<JLabel>();
	
	public static void createPin(int playerID) {
		ImageIcon img;
		JLabel pin = null;
		
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/pins/pin" + playerID + ".png"))
					.getScaledInstance(20,30, Image.SCALE_SMOOTH));
			pin = new JLabel("", img, JLabel.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		PinPosition pos = PinsPosition.getPosition(playerID, 0);
		
		pin.setBounds(pos.getX(), pos.getY(), 40,40);
		
		Screen.getInstance().getTabuleiroImg().add(pin);
		pins.add(pin);
	}
	
	public static JLabel getPlayerPin(int playerID) {
		return pins.get(playerID);
	}
	
	public static void updatePinLocation(JLabel pin, int playerID, int positionID) {
		PinPosition pos = PinsPosition.getPosition(playerID, positionID);
		pin.setLocation(pos.getX(), pos.getY());
	}
	
	public static void clearPins() {
		for(JLabel pin : pins) {
			Screen.getInstance().getTabuleiroImg().remove(pin);
			
		}
		pins.clear();
	}
	
}
