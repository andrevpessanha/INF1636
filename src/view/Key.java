package view;

public class Key {
	private int playerID, positionID;
	
	public Key(int playerID, int positionID) {
		this.playerID = playerID;
		this.positionID = positionID;
	}
	
	 @Override
	 public boolean equals(Object o) {
		 if (this == o) return true;
		 if (!(o instanceof Key)) return false;
	     Key key = (Key) o;
	     return playerID == key.playerID && positionID == key.positionID;
	 }
	 
	  @Override
	  public int hashCode() {
		  int result = playerID;
	      result = 31 * result + positionID;
	      return result;
	  }
}
