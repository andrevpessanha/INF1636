package model;

import java.util.ArrayList;
import controller.Game;


public class Player{
	private int id, currentPositionID, money, dividaBanco;
	private ArrayList<Divida> dividaPlayers;
	private ArrayList<Empreendimento> empsList;
	private Card sairPrisao;
	private int qtdDicesIguais, qtdRollsInJail, lastRollTotal;
	private boolean playAgain, preso, faliu, temCardSairPrisao;
	
	public Player(int id, String color) {
		this.id = id;
		currentPositionID = Game.getInstance().getPlayerCurrentPosition(id);
		Game.getInstance().createPlayerPin(id);
		empsList = new ArrayList<Empreendimento>();
		movePin(currentPositionID);
		money = 2458;
		inicializaDividaPlayers();
	}
	
	public void setQtdDicesIguais(int qtd) {
		qtdDicesIguais = qtd;
	}
	
	public int getQtdDicesIguais() {
		return qtdDicesIguais;
	}
	
	public void setQtdRollsInJail(int qtd) {
		qtdRollsInJail = qtd;
	}
	
	public int getQtdRollsInJail() {
		return qtdRollsInJail;
	}
	
	private void inicializaDividaPlayers() {
		dividaPlayers = new ArrayList<Divida>();
		for(int i=0; i< Game.getInstance().getQtdPlayers() ; i++) {
			if(i != id) { // Somente outros players
				dividaPlayers.add(new Divida(i, 0));
			}
		}
	}
	
	public void rollDices() {
		Dices.roll();
		Game.getInstance().updateDices();
		
		if(!Dices.dicesIguais() && preso) {
			qtdRollsInJail += 1;
			if(qtdRollsInJail == 4) { // Paga taxa pra sair
				payBank(50);  
				qtdRollsInJail = 0;
				sairPrisao();  // (Passar vez)
			}
			// continua preso
			Game.getInstance().passarVezBtnSetVisible(true); // (Passar vez)
		}
		else if(Dices.dicesIguais() && preso) {
			sairPrisao();  // (Passar vez)
		}
		else if(!Dices.dicesIguais() && !preso){
			Game.getInstance().passarVezBtnSetVisible(true); // (Passar vez)
		}
		else if(Dices.dicesIguais() && !preso) {
			playAgain = true;
			qtdDicesIguais += 1;
			if(qtdDicesIguais == 3) {
				qtdDicesIguais = 0;
				playAgain = false;
				Game.getInstance().showMessageDialog("Dados iguais 3x seguidas!? Sabia que usar Hack é crime!? Vai preso!", "Hack Detected O_O");
				setInJail();
				PositionsEvent.verificaEvento(9);
				Game.getInstance().comprarBtnSetVisible(false);
				Game.getInstance().passarVezBtnSetVisible(true); // (Passar vez)
				return ;
			}
			Game.getInstance().jogarDadosBtnSetVisible(true);  // (jogar dados)
		}
		
		lastRollTotal = Dices.getRollTotal();
		currentPositionID += lastRollTotal;
		if(currentPositionID > 35) {
			Game.getInstance().showMessageDialog("Passou pelo início - Receba $200 como incentivo pelo seu treino Fit", "Bônus");
			money += 200;
			Game.getInstance().updatePlayersPainel();
			currentPositionID -= 36;
		}
		
		if(!preso) {
			movePin(currentPositionID);
			Game.getInstance().setPlayerCurrentPosition(id, currentPositionID);
			Game.getInstance().previewSetVisible(true);
			PositionsEvent.verificaEvento(currentPositionID);
		}
	}
	
	public int getID() {
		return id;
	}
	
	public int getCurrentPositionID() {
		return currentPositionID;
	}
	
	public void movePin(int positionID) {
		Game.getInstance().movePlayerPin(id, positionID);
	}
	
	public void setPlayAgain(boolean state) {
		playAgain = state;
	}
	
	public boolean getPlayAgain() {
		return playAgain;
	}
	
	public void passTurn() {
		qtdDicesIguais = 0;
		Game.getInstance().previewSetVisible(false);
		Game.getInstance().sairPrisaoBtnSetVisible(false); 
		Game.getInstance().passarVezBtnSetVisible(false);
		if(getTotalDividas() == 0) {
			if(Game.getInstance().falirBtnIsVisible()) Game.getInstance().falirBtnSetVisible(false);
			Game.getInstance().setNextPlayer();
			Game.getInstance().jogarDadosBtnSetVisible(true);
		}
		else { // Tem dívidas
			Game.getInstance().comprarBtnSetVisible(false);
			Game.getInstance().falirBtnSetVisible(true);
			Game.getInstance().showMessageDialog("Pague suas dívidas ou clique no botão Falir para desistir", "Alerta");
		}	
	}
	
	public void comprarEmpreendimentoBanco(Empreendimento e, int preco) {
		payBank(preco);
		e.setDonoID(id);
		addEmpreendimento(e);
	}
	
	public void venderEmpreendimentoBanco(Empreendimento e, int preco) {
		recebeMoney(preco);
		e.setDonoID(-1);
		removeEmpreendimento(e);
	}
	
	public void comprarEmpreendimentoPlayer(Empreendimento e, Player p, int preco) {
		payPlayer(preco, p);
		e.setDonoID(id);
		p.removeEmpreendimento(e);
		addEmpreendimento(e);
	}
	
	public void venderEmpreendimentoPlayer(Empreendimento e, Player p, int preco) {
		p.payPlayer(preco, this);
		e.setDonoID(p.getID());
		removeEmpreendimento(e);
		p.addEmpreendimento(e);
	}
	
	private void addEmpreendimento(Empreendimento e) {
		empsList.add(e);
	}
	
	private void removeEmpreendimento(Empreendimento e) {
		empsList.remove(e);
	}
	
	public void recebeMoney(int qtdMoney) {
		if(dividaBanco > 0) {
			qtdMoney = quitarDividaBanco(qtdMoney);
		}
		if(qtdMoney > 0) {
			qtdMoney = quitarDividasPlayers(qtdMoney);
		}

		money += qtdMoney;
		Game.getInstance().updatePlayersPainel();
		
		if(getTotalDividas() == 0 && Game.getInstance().falirBtnIsVisible()) {
			Game.getInstance().falirBtnSetVisible(false);
			Game.getInstance().passarVezBtnSetVisible(true);
		}
	}
	
	private int quitarDividaBanco(int qtdMoney) {
		dividaBanco -= qtdMoney;
		qtdMoney = 0;
		if(dividaBanco < 0){
			qtdMoney = Math.abs(dividaBanco);
			dividaBanco = 0;
		}
		return qtdMoney;
	}
	
	private int quitarDividasPlayers(int qtdMoney) {
		for(Divida d : dividaPlayers) {
			int valor = d.getValor();
			int valorAntigo = valor;
			if(valor > 0) {  // Tem dívida
				int playerID = d.getCredorID();
				Player credor = Game.getInstance().getPlayer(playerID);
				valor -= qtdMoney;
				qtdMoney = 0;
				if(valor < 0){
					qtdMoney = Math.abs(valor);
					valor = 0;
				}
				int valorPago = valorAntigo - valor;
				payPlayer(valorPago, credor);
				d.setValor(valor);
			}	
			if(qtdMoney == 0) return 0;
		}
		return qtdMoney;
	}
	
	public void payBank(int qtdMoney) {
		money -= qtdMoney;
		if(money < 0) {
			dividaBanco += Math.abs(money);
			money = 0;
		}
		Game.getInstance().updatePlayersPainel();
	}
	
	public void payPlayer(int qtdMoney, Player p) {
		int divida = 0;
		money -= qtdMoney;
		if(money < 0) {
			divida = Math.abs(money);
			money = 0;
		}
		if(divida > 0) {
			int outroPlayerID = p.getID() - 1;  // dividaPlayers size é qtdPlayers - 1
			Divida d = dividaPlayers.get(outroPlayerID);
			d.setValor( d.getValor() + divida );
		}
		p.recebeMoney(qtdMoney - divida);
		
	}
	
	public int getTotalDividas() {
		int total = dividaBanco;
		for(Divida d : dividaPlayers)
			total += d.getValor();
		return total;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getDividaBanco() {
		return dividaBanco;
	}
	
	public ArrayList<Divida> getDividaPlayers(){
		return dividaPlayers;
	}
	
	public ArrayList<Empreendimento> getEmpsList(){
		return empsList;
	}
	
	public void setInJail() {
		preso = true;
		qtdRollsInJail = 0;
		if(preso && currentPositionID != 9) {
			currentPositionID = 9;
			movePin(currentPositionID);
		}
		if(preso && temCardSairPrisao) {
			Game.getInstance().sairPrisaoBtnSetVisible(true);
		}
	}
	
	public void setPreso(boolean state) {
		preso = state;
	}
	
	public boolean getPreso() {
		return preso;
	}
	
	public void sairPrisao() {
		preso = false;
		Game.getInstance().sairPrisaoBtnSetVisible(false); 
		Game.getInstance().passarVezBtnSetVisible(true);
	}
	
	public boolean getTemCardSairPrisao() {
		return temCardSairPrisao;
	}
	
	public void setTemCardSairPrisao(boolean state) {
		temCardSairPrisao = state;
	}
	
	
	public void recebeCardSairPrisao(Card c) {
		temCardSairPrisao = true;
		sairPrisao = c;
	}
	
	public void usarCardSairPrisao() {
		preso = false;
		temCardSairPrisao = false;
		Game.getInstance().sairPrisaoBtnSetVisible(false);
		Cards.devolveCard(sairPrisao);
		sairPrisao = null;
	}
	
	public boolean inJail() {
		return preso;
	}
	
	public void setFaliu(boolean state) {
		faliu = state;
		if(faliu) {
			Game.getInstance().falirBtnSetVisible(false);
			Game.getInstance().verificaPlayersRestantes();
		}
	}
	
	public boolean getFaliu() {
		return faliu;
	}
	
	public void setCurrentPositionID(int positionID) {
		currentPositionID = positionID;
	}
	
	public void setMoney(int qtdMoney) {
		money = qtdMoney;
	}
	
	public void setDividaBanco(int divida) {
		dividaBanco = divida;
	}
	
	public void setDividaPlayers(int[] dividas) {
		for(int i=0; i<dividas.length; i++) {
			dividaPlayers.get(i).setValor(dividas[i]);
		}
	}
}
