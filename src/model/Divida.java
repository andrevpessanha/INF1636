package model;

public class Divida {
	private int credorID;
	private int valor;
	
	public Divida(int credorID, int valor) {
		this.credorID = credorID;
		this.valor = valor;
	}
	
	public int getCredorID() {
		return credorID;
	}
	
	public int getValor() {
		return valor;
	}
	
	public void setValor(int valor) {
		this.valor = valor;
	}
}
