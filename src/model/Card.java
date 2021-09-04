package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import tools.CardType;

public class Card {
	private ImageIcon img;
	private CardType tipo;
	private int valor;
	private Integer goToPositionID;
	
	public Card(int cardID, CardType tipo, int valor, Integer goToPositionID) {
		this.tipo = tipo;
		this.valor = valor;
		this.goToPositionID = goToPositionID;
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/cards/card" + cardID +".jpg"))
					.getScaledInstance(140,190, Image.SCALE_SMOOTH));	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public CardType getType() {
		return tipo;
	}
	
	public int getValor() {
		return valor;
	}
	
	public int getGotoID() {
		if(goToPositionID == null) return -1;
		return goToPositionID.intValue();
	}
	
	public ImageIcon getImageIcon() {
		return img;
	}
}
