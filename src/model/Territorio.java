package model;

import controller.Game;
import tools.PositionType;
import tools.UIColors;

public class Territorio extends Empreendimento {
	private UIColors cor;
	private int[] taxaSedes;
	private int qtdSedes, taxaComite, precoSede, precoComite;
	private boolean temComite;
	
	public Territorio(int positionID, String nome, UIColors cor, int precoPonto, int precoSede, int precoComite, 
					int[] taxaSedesSrc, int taxaComite) {
		super(positionID);
		this.positionID = positionID;
		this.tipo = PositionType.TERRITORIO;
		this.nome = nome;
		this.donoID = -1;
		this.cor = cor;
		preco = precoPonto * 2;
		this.valorHipoteca = precoPonto;
		this.precoSede = precoSede;
		this.precoComite = precoComite;
		this.taxaComite = taxaComite;
		taxaSedes = new int[5];
		System.arraycopy( taxaSedesSrc, 0, taxaSedes, 0, taxaSedesSrc.length );
		
	}
	
	public void construirCasa() {
		Game.getInstance().getCurrentPlayer().payBank(precoSede);
		qtdSedes += 1;
	}
	
	public void venderCasa() {
		Game.getInstance().getCurrentPlayer().recebeMoney( getPrecoVendaSede() );
		qtdSedes -= 1;
	}
	
	public void construirComite() {
		Game.getInstance().getCurrentPlayer().payBank(precoComite);
		temComite = true;
	}
	
	public void venderComite() {
		Game.getInstance().getCurrentPlayer().recebeMoney(getPrecoVendaComite());
		temComite = false;
	}
	
	public boolean getTemComite() {
		return temComite;
	}
	
	public void setTemComite(boolean state) {
		temComite = state;
	}
	
	public boolean temConstrucoes() {
		return (qtdSedes > 0 || temComite);
	}
	
	public UIColors getColor() {
		return cor;
	}
	
	public int getQtdSedes() {
		return qtdSedes;
	}
	
	public void setQtdSedes(int qtd) {
		qtdSedes = qtd;
	}
	
	public int getPrecoCompraSede() {
		return precoSede;
	}
	
	public int getPrecoVendaSede() {
		return precoSede/2;
	}
	
	public int getPrecoCompraComite() {
		return precoComite;
	}
	
	public int getPrecoVendaComite() {
		return precoComite / 2;
	}

	 
	@Override
	public int calculaTaxa() {
		if(temComite) return taxaComite;
		return taxaSedes[qtdSedes];
	}

}
