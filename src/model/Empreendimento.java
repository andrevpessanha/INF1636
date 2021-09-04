package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import controller.Game;

public abstract class Empreendimento extends Position {
	protected String nome;
	protected ImageIcon img;
	protected int donoID; // PlayerID
	protected int preco, valorHipoteca;
	protected boolean temDono, hipotecada;
	
	public Empreendimento(int positionID) {
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/empreendimentos/" + positionID +".jpg"))
					.getScaledInstance(140,190, Image.SCALE_SMOOTH));	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public abstract int calculaTaxa();
	
	public boolean temDono() {
		return temDono;
	}
	
	public void setTemDono(boolean state) {
		temDono = state;
	}
	
	public ImageIcon getImageIcon() {
		return img;
	}
	
	public void setDonoID(int playerID) {
		donoID = playerID;
		temDono = true;
	}
	
	public int getDonoID() {
		return donoID;
	}
	
	public int getPreco() {
		return preco;
	}
	
	public int getValorHipoteca() {
		return valorHipoteca;
	}
	
	public int getValorDesfazerHipoteca() {
		return (int) (valorHipoteca * 1.2);
	}
	
	public void hipotecar() {
		Game.getInstance().getPlayer(donoID).recebeMoney(valorHipoteca);
		hipotecada = true;
	}
	
	public void desfazerHipoteca() {
		Game.getInstance().getPlayer(donoID).payBank(getValorDesfazerHipoteca() ); 
		hipotecada = false;
	}
	
	public boolean getHipotecada() {
		return hipotecada;
	}
	
	public void setHipotecada(boolean state) {
		hipotecada = state;
	}
}
