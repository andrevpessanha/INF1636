package view;

import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ScreenPreview {
	private static JLabel preview;
	
	public static void criaPositionPreview() {
		preview = new JLabel("POSIÇÃO ATUAL: INICIO", null, JLabel.CENTER);
		preview.setFont(new Font("", Font.BOLD, 14));
		preview.setBounds(110, 420, 250, 200);
		Screen.getInstance().getTabuleiroImg().add(preview);
	}
	
	public static void previewSetVisible(boolean state) {
		preview.setVisible(state);
	}
	
	public static void inicializaPreviewDefault() {
		preview.setText(null);
		preview.setIcon(null);
		preview.setBounds(110, 420, 250, 200);
	}
	
	public static void updatePreviewLocation(int x, int y, int w, int h) {
		preview.setBounds(x, y, w, h);
	}
	
	public static void updatePreviewIcon(ImageIcon icon) {
		preview.setIcon(icon);
	}
	
	public static void updatePreviewText(String text) {
		preview.setText(text);
	}
	
}
