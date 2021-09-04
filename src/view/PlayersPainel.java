package view;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Game;
import tools.UIColors;


//Essa classe utiliza o Design Pattern Singleton e Observer 

public class PlayersPainel extends JPanel implements ActionListener, Observer {
	private static final long serialVersionUID = -3248505398122201070L;
	private UIColors[] colors = new UIColors[] {UIColors.RED, UIColors.BLUE, UIColors.ORANGE, UIColors.YELLOW, UIColors.PURPLE, UIColors.BLACK};
	private ArrayList<JButton> detalhes;
	private ArrayList<JLabel> playersNomes, playersMoney, playersDividas;
	private JButton detalhesBtn, sairBtn, debugBtn;
	private JLabel nomeLabel, moneyLabel, dividaLabel;
	private static PlayersPainel painel;
	
	private PlayersPainel() {
		Game.getInstance().registra(this);
	}
	
	public static PlayersPainel getInstance() {
		if(painel == null) {
			painel = new PlayersPainel();
		}
		return painel;
	}
	
	public void criaPainel(int qtdPlayers) {
		ImageIcon img = null;
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/home.png"))
					.getScaledInstance(60,50, Image.SCALE_SMOOTH));
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		if(nomeLabel == null) criaHeader();
		if(detalhes != null) clearPainel(); // Caso seja Load Game
		
		sairBtn = new JButton("Sair do Jogo");
		sairBtn.setBounds(780, 670, 120, 40);
		sairBtn.addActionListener(this);
		PaintComponent.setButtonColor(sairBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		Screen.getInstance().getContentPane().add(sairBtn);
		sairBtn.setVisible(true);
		
		debugBtn = new JButton("Modo DEBUG OFF");
		debugBtn.setFont(new Font("", Font.BOLD, 9));
		debugBtn.setBounds(780, 620, 120, 40);
		debugBtn.addActionListener(this);
		Screen.getInstance().getContentPane().add(debugBtn);
		debugBtn.setVisible(true);
		
		playersNomes = new ArrayList<JLabel>();
		detalhes = new ArrayList<JButton>();
		playersMoney = new ArrayList<JLabel>();
		playersDividas = new ArrayList<JLabel>();
			
		int y = 100;
		for(int i=0; i<qtdPlayers; i++) {
			nomeLabel = new JLabel("Jogador " + (i+1));
			moneyLabel = new JLabel("$" + Game.getInstance().getPlayer(i).getMoney());
			dividaLabel = new JLabel("$" + Game.getInstance().getPlayer(i).getTotalDividas());
			nomeLabel.setFont(new Font("", Font.BOLD, 14));
			moneyLabel.setFont(new Font("", Font.BOLD, 14));
			dividaLabel.setFont(new Font("", Font.BOLD, 14));
			PaintComponent.setLabelColor(nomeLabel, colors[i]);
			detalhesBtn = new JButton(img);
			PaintComponent.setButtonColor(detalhesBtn, colors[i]);
			nomeLabel.setBounds(720, y, 70, 40);
			moneyLabel.setBounds(805, y, 60, 40);
			dividaLabel.setBounds(860, y, 60, 40);
			detalhesBtn.setBounds(920, y, 40, 40);
			detalhesBtn.addActionListener(this);
			Screen.getInstance().getContentPane().add(detalhesBtn);
			Screen.getInstance().getContentPane().add(sairBtn);
			Screen.getInstance().getContentPane().add(nomeLabel);
			Screen.getInstance().getContentPane().add(moneyLabel);
			Screen.getInstance().getContentPane().add(dividaLabel);
			playersMoney.add(moneyLabel);
			playersDividas.add(dividaLabel);
			detalhes.add(detalhesBtn);
			playersNomes.add(nomeLabel);
			y += 60;
		}
	}
	
	private void clearPainel(){
		for(int i = 0; i<detalhes.size(); i++) {
			Screen.getInstance().getContentPane().remove(playersMoney.get(i));
			Screen.getInstance().getContentPane().remove(playersDividas.get(i));
			Screen.getInstance().getContentPane().remove(detalhes.get(i));
			Screen.getInstance().getContentPane().remove(playersNomes.get(i));
		}
		playersNomes.clear();
		detalhes.clear();
		playersMoney.clear();
		playersDividas.clear();
	}
	
	public void sairBtnSetVisible(boolean state) {
		sairBtn.setVisible(state);
	}
	
	public void painelSetVisible(boolean state) {
		for(int i = 0; i<detalhes.size(); i++) {
			playersMoney.get(i).setVisible(state);
			playersDividas.get(i).setVisible(state);
			detalhes.get(i).setVisible(state);
			playersNomes.get(i).setVisible(state);
		}
		sairBtn.setVisible(state);
		debugBtn.setVisible(state);
	}
	
	private void criaHeader() {
		JLabel header;
		int[] pos = new int[] {720, 805, 860, 920};
		String[] titles = new String[] {"Nome", "Saldo", "Dívida", "Home"};
		
		for(int i=0; i<4; i++) {
			header = new JLabel(titles[i]);
			header.setFont(new Font("", Font.BOLD, 14));
			header.setBounds(pos[i], 60, 70, 40);
			Screen.getInstance().getContentPane().add(header);
		}
	}
	
	public void updatePainel() {
		for(int i=0; i<playersMoney.size(); i++) {
			playersMoney.get(i).setText("$" + Game.getInstance().getPlayerMoney(i));
			playersDividas.get(i).setText("$" + Game.getInstance().getPlayerTotalDividas(i));
		}
	}
	
	public void updateSairButtonColor() {
		PaintComponent.setButtonColor(sairBtn, colors[Game.getInstance().getCurrentPlayerID()]);
	}
	
	public void refreshPainel() {
		for(int i=0; i<playersMoney.size(); i++) {
			playersMoney.get(i).setVisible(false);
			playersMoney.get(i).setVisible(true);
			playersDividas.get(i).setVisible(false);
			playersDividas.get(i).setVisible(true);
			detalhes.get(i).setVisible(false);
			detalhes.get(i).setVisible(true);
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == sairBtn){
			int resp = ScreenPopup.showConfirmDialog(Screen.getInstance(), "Tem certeza que deseja sair do jogo?" , "Alerta");
			if(resp == 0) { // Sim
				Game.getInstance().currentPlayerFaliu();
			}
		}
		else if (event.getSource() == debugBtn){
			Game g = Game.getInstance();
			if(g.isInDebugMode()) {
				g.setDebugMode(false);
				debugBtn.setText("Modo DEBUG OFF");
			}
			else {
				g.setDebugMode(true);
				debugBtn.setText("Modo DEBUG ON");
			}
		}
		else {
			for(int i=0 ; i< detalhes.size(); i++) {
				if (event.getSource() == detalhes.get(i)){
					new PlayerEmpsFrame(Game.getInstance().getPlayer(i));
				}
			}		
		}
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		updatePainel();
	}

}
