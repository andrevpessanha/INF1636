package model;

import tools.PositionType;

public class Empresa extends Empreendimento {
	private int taxa;
	
	public Empresa(int positionID, String nome, int taxa, int valorHipoteca) {
		super(positionID);
		this.positionID = positionID;
		this.tipo = PositionType.EMPRESA;
		this.nome = nome;
		this.donoID = -1;
		this.taxa = taxa;
		this.valorHipoteca = valorHipoteca;
		preco = valorHipoteca * 2;
		
	}

	@Override
	public int calculaTaxa() {
		return Dices.getRollTotal() * taxa;
	}

}
