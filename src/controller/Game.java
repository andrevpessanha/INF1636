package controller;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import model.BoardPositions;
import model.Cards;
import model.Dices;
import model.Divida;
import model.Empreendimento;
import model.Player;
import model.Position;
import model.PositionsEvent;
import model.Territorio;
import tools.PositionType;
import view.PlayerEmpsFrame;
import view.PlayersPainel;
import view.ScreenPins;
import view.ScreenPopup;
import view.ScreenPreview;
import view.Screen;
import view.ScreenButtons;

// Essa classe utiliza o Design Pattern Facade, Singleton e Observer

public class Game extends Observable {
	private ArrayList<Player> players = new ArrayList<Player>();
	private int[] playersCurrentPositionID;
	private Player currentPlayer;
	private int currentPlayerID;
	private int currentPositionEmpID;
	private Empreendimento currentPositionEmp;
	private int qtdPlayers;
	private boolean playersPainelCreated;
	private boolean debugMode;
	private static Game game;
	
	private Game() {
	
	}
	
	public static Game getInstance() {
		if( game == null) {
			game = new Game();
		}
		return game;
	}
	
	public void registra(PlayersPainel p){
		addObserver(p);
	}
	
	public void updatePlayersPainel() {
		setChanged();
		notifyObservers();
	}
	
	public void initializeEverything() {
		BoardPositions.criaPositions();
		Screen.getInstance().initializeScreen();
	}
	
	public void newGame() {
		
		Game.getInstance().initializeEverything();
		
		Screen.getInstance().tabuleiroSetVisible(false);
		if(playersPainelCreated) PlayersPainel.getInstance().painelSetVisible(false);
		qtdPlayers = ScreenPopup.showNumIntervalDialog(Screen.getInstance(), "Quantos Jogadores?", "Novo Jogo", 2, 6);
    	if (qtdPlayers == -1) qtdPlayers = 2;
    	Game.getInstance().reiniciaGame();
    	
		Player p = Game.getInstance().getPlayer(0);
		Game.getInstance().setCurrentPlayer(p);
		PositionsEvent.verificaEvento(0);
		ScreenButtons.updateButtonsColor();
		
		PlayersPainel.getInstance().updatePainel();
		PlayersPainel.getInstance().refreshPainel();
		Screen.getInstance().tabuleiroSetVisible(true); 
		PlayersPainel.getInstance().painelSetVisible(true);
	}
	
	private void reiniciaGame() {
		ScreenPins.clearPins();
		Cards.criaCards();
		playersCurrentPositionID = new int[qtdPlayers];
		players.clear();
		Game.getInstance().criaPlayers(qtdPlayers);
		
		PlayersPainel.getInstance().criaPainel(qtdPlayers);
		playersPainelCreated = true;
		PlayersPainel.getInstance().painelSetVisible(false);
		
		ScreenButtons.comprarBtnSetVisible(false);
		ScreenButtons.falirBtnSetVisible(false);
		ScreenButtons.jogarDadosBtnSetVisible(true);
		ScreenButtons.passarVezBtnSetVisible(false);
		ScreenButtons.sairPrisaoBtnSetVisible(false);
	}
	
	private void criaPlayers(int qtdPlayers) {
		String[] colors = new String[] {"red", "blue", "orange", "yellow", "purple", "black"};
		
		for(int i = 0; i < qtdPlayers; i++) {
			players.add(new Player(i, colors[i]));
		}
	}
	
	public Empreendimento getCurrentPositionEmp() {
		return currentPositionEmp;
	}
	
	public void setCurrentPositionEmp(Empreendimento e) {
		currentPositionEmp = e;
		currentPositionEmpID = e.getPositionID();
	}
	
	public int getCurrentPositionEmpID() {
		return currentPositionEmpID;
	}
	
	public int getCurrentPlayerID() {
		return currentPlayerID;
	}
	
	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public void setCurrentPlayer(Player p) {
		currentPlayer = p;
		currentPlayerID = p.getID();
		PositionsEvent.verificaEvento(getPlayerCurrentPosition(currentPlayerID));
	}
	
	public void setNextPlayer() {  // Pass Turn
		ScreenPreview.previewSetVisible(false);
		if(ScreenButtons.comprarBtnIsVisible()) 
			ScreenButtons.comprarBtnSetVisible(false);
		
		boolean achouNextPlayer = false;
		while(!achouNextPlayer) {
			currentPlayerID += 1;
			if(currentPlayerID >= players.size()) {
				currentPlayerID = 0;
			}
			if(!getPlayer(currentPlayerID).getFaliu()) achouNextPlayer = true;
		}
		
		currentPlayer = players.get(currentPlayerID);
		if(currentPlayer.getPreso() && currentPlayer.getTemCardSairPrisao()) sairPrisaoBtnSetVisible(true);
		ScreenButtons.updateButtonsColor();
	}
	
	public void verificaPlayersRestantes() {
		int qtdFaliu = 0;
		for(Player p : players) {
			if(p.getFaliu()) qtdFaliu += 1;
		}
		
		if(qtdFaliu == players.size() - 1) encerraGame();
		else{
			setNextPlayer();
			ScreenButtons.jogarDadosBtnSetVisible(true);
		}
	
	}
	
	private void encerraGame() {
		for(Player p : players) {
			if(!p.getFaliu()) {
				PlayersPainel.getInstance().sairBtnSetVisible(false);
				ScreenPopup.showMessageDialog("Parabéns! Jogador " + (p.getID() + 1) + " ganhou!", "Fim de Jogo");
			}
		}
	}
	
	public void setPlayerCurrentPosition(int playerID, int positionID) {
		playersCurrentPositionID[playerID] = positionID;
		players.get(playerID).setCurrentPositionID(positionID);
	}
	
	public int getPlayerCurrentPosition(int playerID) {
		return playersCurrentPositionID[playerID];
	}
	
	public Player getPlayer(int playerID) {
		return players.get(playerID);
	}
	
	public int getQtdPlayers() {
		return qtdPlayers;
	}
	
	public int getPlayerMoney(int playerID) {
		return getPlayer(playerID).getMoney();
	}
	
	public int getPlayerTotalDividas(int playerID) {
		return getPlayer(playerID).getTotalDividas();
	}
	
	public void setPreviewDefault() {
		ScreenPreview.inicializaPreviewDefault();
	}
	
	public void previewSetVisible(boolean state) {
		ScreenPreview.previewSetVisible(state);
	}
	
	public void updatePreviewLocation(int x, int y, int w, int h) {
		ScreenPreview.updatePreviewLocation(x, y, w, h);
	}
	
	public void updatePreviewIcon(ImageIcon icon) {
		ScreenPreview.updatePreviewIcon(icon);
	}
	
	public void updatePreviewText(String text) {
		ScreenPreview.updatePreviewText(text);
	}
	
	public void passarVezBtnSetVisible(boolean status) {
		ScreenButtons.passarVezBtnSetVisible(status);
	}
	
	public void jogarDadosBtnSetVisible(boolean status) {
		ScreenButtons.jogarDadosBtnSetVisible(status);
	}
	
	public void comprarBtnSetVisible(boolean status) {
		ScreenButtons.comprarBtnSetVisible(status);
	}
	
	public void falirBtnSetVisible(boolean status) {
		ScreenButtons.falirBtnSetVisible(status);
	}
	
	public boolean falirBtnIsVisible() {
		return ScreenButtons.falirBtnIsVisible();
	}
	
	public void sairPrisaoBtnSetVisible(boolean status) {
		ScreenButtons.sairPrisaoBtnSetVisible(status);
	}
	
	public void currentPlayerRollDices() {
		ScreenButtons.jogarDadosBtnSetVisible(false);
		currentPlayer.rollDices();
	}
	
	public void updateDices() {
		int[] res = Dices.getCurrentRoll();
		Screen.getInstance().updateDices(res[0], res[1]);
	}
	
	public void createPlayerPin(int playerID) {
		ScreenPins.createPin(playerID);
	}
	
	public void movePlayerPin(int playerID, int positionID) {
		ScreenPins.updatePinLocation(ScreenPins.getPlayerPin(playerID), playerID, positionID);
	}
	
	public void currentPlayerPassTurn() {
		currentPlayer.passTurn();
	}
	
	public void currentPlayerUsarCardSairPrisao() {
		currentPlayer.usarCardSairPrisao();
	}
	
	public void currentPlayerFaliu() {
		currentPlayer.setFaliu(true);
	}
	
	public void showMessageDialog(String message, String title) {
		ScreenPopup.showMessageDialog(message, title);
	}
	
	public int showNumIntervalDialog(String message, String title, int min, int max) {
		return ScreenPopup.showNumIntervalDialog(Screen.getInstance(), message, title, min, max);
	}
	
	public void venderEmpreendimento(Empreendimento emp, Player player, ArrayList<String> venderOptions) {
		String resp = ScreenPopup.showItemIntervalDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja vender para quem?", "Negócio", venderOptions);
		if(resp != "cancel") {
			if(resp == "Banco") {  // Vender para o Banco
				int preco = emp.getPreco() / 2;
				int resp2 = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja vender por $" + preco + " ?", "Negócio");
				if(resp2 == 0) { // Sim
					player.venderEmpreendimentoBanco(emp, preco);
					PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
				}
			}
			else { // Vender para Player
				int otherPlayerID = Integer.parseInt(resp) - 1;
				System.out.println("OtherPlayerID: " + otherPlayerID);
				Player otherPlayer = Game.getInstance().getPlayer(otherPlayerID);
				int preco = ScreenPopup.showNumIntervalDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja vender por quanto?", "Negócio", 0, 9999);
				if(preco != -1) { // Não cancelou
					int resp3 = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Jogador " + (otherPlayerID + 1) + ", o Jogador " + (player.getID() + 1) + " quer te vender o Empreendimento por $" + preco + ". Você aceita?" , "Negócio");
					if(resp3 == 0) { // Sim
						player.venderEmpreendimentoPlayer(emp, otherPlayer, preco);
						PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
					}
				}
			}
		}
	}
	
	public void comprarEmpreendimentoBanco() {
		Empreendimento e = currentPositionEmp;
		int preco = e.getPreco();
		int playerMoney = currentPlayer.getMoney();
		if(playerMoney >= preco) {
			int resp = ScreenPopup.showConfirmDialog(Screen.getInstance(), "O preço é $" + preco + ". Deseja comprar?" , "Negócio");
			if(resp == 0) { // Sim
				ScreenButtons.comprarBtnSetVisible(false);
				currentPlayer.comprarEmpreendimentoBanco(e, preco);
			}
		}
		else ScreenPopup.showMessageDialog("Saldo Insuficiente!", "Alerta");
	}
	
	public void comprarEmpreendimentoPlayer (Empreendimento emp, Player player) {
		Player playerComprador = Game.getInstance().getCurrentPlayer();
		int preco = ScreenPopup.showNumIntervalDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja comprar por quanto?", "Negócio", 0, 9999);
		if(preco != -1) { // Não cancelou
			int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Jogador " + (player.getID() + 1) + ", o Jogador " + (playerComprador.getID() + 1) + " quer comprar seu Empreendimento por $" + preco + ". Você aceita?" , "Negócio");
			if(resp == 0) { // Sim
				playerComprador.comprarEmpreendimentoPlayer(emp, player, preco);
				PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
			}
		}
	}
	
	public void hipotecarEmpreendimento(Empreendimento emp) {
		int preco = emp.getValorHipoteca();
		int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja Hipotecar esse Empreendimento por $" + preco + "?", "Negócio");
		if(resp == 0) { // Sim
			if(emp.getPositionType() == PositionType.EMPRESA) {
				emp.hipotecar();
				PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
			}
			else if(emp.getPositionType() == PositionType.TERRITORIO) {
				if( ((Territorio)emp).getQtdSedes() == 0 && !((Territorio)emp).getTemComite() ) {
					emp.hipotecar();
					PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
				}
				else {
					ScreenPopup.showMessageDialog("É necessário vender todas as construções antes de hipotecar!", "Alerta");
				}
			}
		}
	}
	
	public void desfazerHipoteca(Empreendimento emp) {
		int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja desfazer a Hipoteca? A taxa é de $" + emp.getValorDesfazerHipoteca() + ".", "Negócio");
		if(resp == 0) { // Sim
			emp.desfazerHipoteca();
			PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
		}
	}
	
	public void construirCasa(Territorio t) {
		int price = t.getPrecoCompraSede();
		int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja construir uma casa por $" + price + "?", "Negócio");
		if(resp == 0) { // Sim
			t.construirCasa();
			PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
		}	
	}
	
	public void venderCasa(Territorio t) {
		int price = t.getPrecoVendaSede();
		int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja vender uma casa por $" + price + "?", "Negócio");
		if(resp == 0) { // Sim
			t.venderCasa();
			PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
		}	
	}
	
	public void construirComite(Territorio t) {
		int price = t.getPrecoCompraComite();
		int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja construir um comitê por $" + price + "?", "Negócio");
		if(resp == 0) { // Sim
			t.construirComite();
			PlayerEmpsFrame.getCurrentInstance().updateEmpsList();
		}	
	}
	
	public void venderComite(Territorio t) {
		int price = t.getPrecoVendaComite();
		int resp = ScreenPopup.showConfirmDialog(PlayerEmpsFrame.getCurrentInstance(), "Deseja vender o comitê por $" + price + "?", "Negócio");
		if(resp == 0) { // Sim
			t.venderComite();
			PlayerEmpsFrame.getCurrentInstance().updateEmpsList();;
		}	
	}
	
	public void saveGame() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle("Escolha um diretório para salvar o jogo: ");
		int res = jfc.showSaveDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			PrintWriter arq = null;
			try {
				arq = new PrintWriter(new FileWriter(jfc.getSelectedFile()+".txt"));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			arq.println(qtdPlayers);  // Qtd Players
			
			for(int i = 0; i<qtdPlayers; i++) {  // GRAVA DADOS PLAYERS
				Player p = getPlayer(i);
				arq.println(p.getCurrentPositionID());  // Position ID
				arq.println(p.getMoney());
				arq.println(p.getDividaBanco());  // Divida Banco
				arq.println(p.getTemCardSairPrisao());
				
				arq.println(p.getQtdDicesIguais());
				arq.println(p.getPlayAgain());
				arq.println(p.getPreso());
				arq.println(p.getQtdRollsInJail());
				arq.println(p.getFaliu());
				
				ArrayList<Divida> dividaPlayers = p.getDividaPlayers();
				for(Divida d : dividaPlayers) {
					arq.println(d.getValor());
				}
				
				ArrayList<Empreendimento> empsList = p.getEmpsList();
				arq.println(empsList.size());
				for(Empreendimento e : empsList) {
					arq.println(e.getPositionID());
					arq.println(e.getDonoID());
					arq.println(e.getHipotecada());
					boolean ehUmTerritorio = e.getPositionType() == PositionType.TERRITORIO;
					arq.println(ehUmTerritorio);
					if(ehUmTerritorio) { 
						arq.println( ((Territorio)e).getQtdSedes());
						arq.println(((Territorio)e).getTemComite());
					}
				}
			}
			arq.println(currentPlayerID);  
			arq.println(currentPositionEmpID); 
			// Estado dos botões
			arq.println(ScreenButtons.comprarBtnIsVisible());
			arq.println(ScreenButtons.falirBtnIsVisible());
			arq.println(ScreenButtons.jogarDadosBtnIsVisible());
			arq.println(ScreenButtons.passarVezBtnIsVisible());
			arq.println(ScreenButtons.sairPrisaoBtnIsVisible());
			
			arq.flush();
			arq.close();
			ScreenPopup.showMessageDialog("Jogo salvo com sucesso!", "Alerta");
		}
		
	}
	
	public void loadGame() {
		JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		jfc.setDialogTitle("Escolha um arquivo para carregar o jogo: ");
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos Txt", "txt", "text");
		jfc.setFileFilter(filter);
		int res = jfc.showOpenDialog(null);
		if (res == JFileChooser.APPROVE_OPTION) {
			File file = jfc.getSelectedFile();
			Scanner arq = null;
			try {
				arq = new Scanner(new BufferedReader(new FileReader(file.getPath())));
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
		
			qtdPlayers = arq.nextInt();
			Screen.getInstance().tabuleiroSetVisible(false);
			PlayersPainel.getInstance().painelSetVisible(false);
			reiniciaGame();
			
			for(int i = 0; i<qtdPlayers; i++) { 
				Player p = getPlayer(i);
				int positionID = arq.nextInt();
				p.setCurrentPositionID(positionID);
				p.movePin(positionID);
				p.setMoney(arq.nextInt());
				p.setDividaBanco(arq.nextInt());
				boolean temCardSairPrisao = arq.nextBoolean();
				if(temCardSairPrisao) {
					p.recebeCardSairPrisao(Cards.retiraCardPrisao());
				}
				p.setQtdDicesIguais(arq.nextInt());
				p.setPlayAgain(arq.nextBoolean());
				p.setPreso(arq.nextBoolean());
				p.setQtdRollsInJail(arq.nextInt());
				p.setFaliu(arq.nextBoolean());
				
				// Divida Players
				ArrayList<Divida> dividaPlayers = p.getDividaPlayers();
				for(Divida d : dividaPlayers) {
					d.setValor(arq.nextInt());
				}
				
				ArrayList<Empreendimento> empsList = p.getEmpsList();
				int qtdEmps = arq.nextInt();
				for(int j=0; j<qtdEmps; j++) {
					int empPosID = arq.nextInt();
					Empreendimento e = (Empreendimento) BoardPositions.getPosition(empPosID);
					e.setTemDono(true);
					e.setDonoID(arq.nextInt());
					e.setHipotecada(arq.nextBoolean());
					boolean ehUmTerritorio = arq.nextBoolean();
					if(ehUmTerritorio) {
						((Territorio)e).setQtdSedes(arq.nextInt());
						((Territorio)e).setTemComite(arq.nextBoolean());
					}
					empsList.add(e);
				}
			}
			
			Player p = getPlayer(arq.nextInt());
			setCurrentPlayer(p);
			Position e = BoardPositions.getPosition(arq.nextInt());
			PositionType tipo = e.getPositionType();
			if(tipo == PositionType.TERRITORIO || tipo == PositionType.EMPRESA){
				setCurrentPositionEmp( (Empreendimento)e  );
				if(e.getPositionID() == p.getCurrentPositionID()) {
					updatePreviewLocation(150, 450, 140, 190);
					updatePreviewText(""); 
					updatePreviewIcon( ((Empreendimento)e).getImageIcon());
				}
			}
			
			ScreenButtons.comprarBtnSetVisible(arq.nextBoolean());
			ScreenButtons.falirBtnSetVisible(arq.nextBoolean());
			ScreenButtons.jogarDadosBtnSetVisible(arq.nextBoolean());
			ScreenButtons.passarVezBtnSetVisible(arq.nextBoolean());
			ScreenButtons.sairPrisaoBtnSetVisible(arq.nextBoolean());
			
			arq.close();
			previewSetVisible(false);
			updatePreviewText("");
			previewSetVisible(true);
			
			PlayersPainel.getInstance().updatePainel();
			ScreenButtons.updateButtonsColor();
			Screen.getInstance().tabuleiroSetVisible(true);
			PlayersPainel.getInstance().painelSetVisible(true);
			ScreenPopup.showMessageDialog("Loading concluído!", "Alerta");
		}
	}
	
	public void quitGame() {
		System.exit(0);
	}
	
	public void setDebugMode(boolean state) {
		debugMode = state;
	}
	
	public boolean isInDebugMode() {
		return debugMode;
	}

}
