package model;

import tools.PositionType;

public class Position {
	protected int positionID;
	protected PositionType tipo;
	
	public Position() {};
	
	public Position(int positionID, PositionType tipo) {
		this.positionID = positionID;
		this.tipo = tipo;
	}
	
	public int getPositionID() {
		return positionID;
	}
	
	public PositionType getPositionType() {
		return tipo;
	}
}
