package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import model.Empreendimento;
import model.Player;
import model.Territorio;
import tools.PositionType;
import tools.UIColors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import controller.Game;


public class PlayerEmpsFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 8418984940835470582L;
	private static PlayerEmpsFrame currentInstance;
	private UIColors[] colors = new UIColors[] {UIColors.RED, UIColors.BLUE, UIColors.ORANGE, UIColors.YELLOW, UIColors.PURPLE, UIColors.BLACK};
	private String buttonText;
	private JPanel panel;
	private ArrayList<JButton> negociarBtns, hipotecarBtns, construirCasaBtns, venderCasaBtns, comiteBtns;
	private Player player;
	private ArrayList<String> venderOptions;
	private ArrayList<Empreendimento> empsList;
	private ArrayList<Territorio> territoriosList;
	private ArrayList<Territorio> territoriosGreen;
	private ArrayList<Territorio> territoriosRed;
	private ArrayList<Territorio> territoriosBlue;
	private ArrayList<Territorio> territoriosYellow;
	private ArrayList<Territorio> territoriosOrange;
	private ArrayList<Territorio> territoriosPurple;
	
	public PlayerEmpsFrame(Player p){
		currentInstance = this;
		player = p;
		empsList = player.getEmpsList();
		preencheNegociarOptions();
		setTitle("Empreendimentos do Jogador " + (p.getID() + 1));
		setResizable(false);
		setBounds(500, 100, 500, 700);
		panel = new JPanel();
		JScrollPane scrollPane = new JScrollPane(panel);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		panel.setPreferredSize(new Dimension(400, 5000));
		panel.setLayout(null);
		
		preencheTerritoriosColorsLists();
		criaEmpsListUI();
		
		getContentPane().add(scrollPane);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public static PlayerEmpsFrame getCurrentInstance() {
		return currentInstance;
	}
	
	public void updateEmpsList() {
		panel.removeAll();
		criaEmpsListUI();
		panel.updateUI();
	}
	
	private void preencheTerritoriosColorsLists() {
		territoriosList = new ArrayList<Territorio>();
		territoriosGreen = new ArrayList<Territorio>();
		territoriosRed = new ArrayList<Territorio>();
		territoriosBlue = new ArrayList<Territorio>();
		territoriosYellow = new ArrayList<Territorio>();
		territoriosOrange = new ArrayList<Territorio>();
		territoriosPurple = new ArrayList<Territorio>();
		
		for(Empreendimento e : empsList) {
			if(e.getPositionType() == PositionType.TERRITORIO) {
				Territorio t = ((Territorio)e);
				territoriosList.add(t);
				if(t.getColor() == UIColors.GREEN) territoriosGreen.add(t);
				else if(t.getColor() == UIColors.RED) territoriosRed.add(t);
				else if(t.getColor() == UIColors.BLUE) territoriosBlue.add(t);
				else if(t.getColor() == UIColors.YELLOW) territoriosYellow.add(t);
				else if(t.getColor() == UIColors.ORANGE) territoriosOrange.add(t);
				else if(t.getColor() == UIColors.PURPLE) territoriosPurple.add(t);
			}
		}
	}
	
	private void criaEmpsListUI() {
		negociarBtns = new ArrayList<JButton>();  // Vender ou Comprar
		if(Game.getInstance().getCurrentPlayerID() == player.getID()) { // É o dono
			hipotecarBtns = new ArrayList<JButton>();
			construirCasaBtns = new ArrayList<JButton>();
			venderCasaBtns = new ArrayList<JButton>();
			comiteBtns = new ArrayList<JButton>();
		}
		
		int y = 20;
		for(Empreendimento e : empsList) {
			JLabel j = new JLabel("", e.getImageIcon(), JLabel.CENTER);
			j.setBounds(30, y, 140, 190);
			panel.add(j);
			criaButtonsNegociar(y, e); 
			if(Game.getInstance().getCurrentPlayerID() == player.getID()) {  // Botões exclusivos pro Dono
				criaButtonsHipotecar(y + 50, e, e.getHipotecada());
				if(e.getPositionType() == PositionType.TERRITORIO) {
					criaButtonsConstruirCasa(y + 100, ((Territorio)e));
					criaButtonsVenderCasa(y + 100, ((Territorio)e));
					criaButtonsComite(y + 150, ((Territorio)e));
				}	
			}
			y += 210;
		}
	}

	private void criaButtonsNegociar(int y, Empreendimento e) {
		buttonText = (Game.getInstance().getCurrentPlayerID() == player.getID()) ? "Vender" : "Comprar";
		JButton negociarBtn = new JButton(buttonText);
		negociarBtn.setBounds(190, y, 120, 40);
		PaintComponent.setButtonColor(negociarBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		negociarBtn.addActionListener(this);
		negociarBtns.add(negociarBtn);
		panel.add(negociarBtn);
		if(e.getPositionType() == PositionType.TERRITORIO) {
			negociarBtn.setEnabled( !((Territorio)e).temConstrucoes() );
		}
	}

	private void criaButtonsHipotecar(int y, Empreendimento e, boolean hipotecada) {
		buttonText = !hipotecada ? "Hipotecar" : "Desfazer Hipoteca";
		JButton hipotecarBtn = new JButton(buttonText);
		if(buttonText == "Desfazer Hipoteca") hipotecarBtn.setFont(new Font("", Font.BOLD, 9));
		hipotecarBtn.setBounds(190, y, 120, 40);
		PaintComponent.setButtonColor(hipotecarBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		hipotecarBtn.addActionListener(this);
		hipotecarBtns.add(hipotecarBtn);
		panel.add(hipotecarBtn);
		if(e.getPositionType() == PositionType.TERRITORIO) {
			hipotecarBtn.setEnabled( !((Territorio)e).temConstrucoes() );
		}
	}
	
	private void criaButtonsConstruirCasa(int y, Territorio t) {
		JButton casaBtn = new JButton("Construir Casa (" + t.getQtdSedes() + ")");
		casaBtn.setFont(new Font("", Font.BOLD, 9));
		casaBtn.setBounds(190, y, 120, 40);
		PaintComponent.setButtonColor(casaBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		casaBtn.addActionListener(this);
		construirCasaBtns.add(casaBtn);
		panel.add(casaBtn);
		casaBtn.setEnabled( (t.getQtdSedes() < 4) && validaConstrucaoCasa(t) );
	}
	
	private boolean validaConstrucaoCasa(Territorio t) {
		if(t.getColor() == UIColors.GREEN && territoriosGreen.size() == 3) return verificaTerritoriosConstruirCasa(t, territoriosGreen);
		else if(t.getColor() == UIColors.RED && territoriosRed.size() == 3) return verificaTerritoriosConstruirCasa(t, territoriosRed);
		else if(t.getColor() == UIColors.BLUE && territoriosBlue.size() == 3) return verificaTerritoriosConstruirCasa(t, territoriosBlue);
		else if(t.getColor() == UIColors.YELLOW && territoriosYellow.size() == 3) return verificaTerritoriosConstruirCasa(t, territoriosYellow);
		else if(t.getColor() == UIColors.ORANGE && territoriosOrange.size() == 3) return verificaTerritoriosConstruirCasa(t, territoriosOrange);
		else if(t.getColor() == UIColors.PURPLE && territoriosPurple.size() == 3) return verificaTerritoriosConstruirCasa(t, territoriosPurple);
		return false;
	}
	
	private boolean verificaTerritoriosConstruirCasa(Territorio t, ArrayList<Territorio> territorios) {
		for(Territorio g : territorios) {
			if(!g.equals(t)) { // Outros territorios
				if( g.getQtdSedes() < t.getQtdSedes() ) {
					return false;
				}
			}
		}
		return true;
	}
	
	private void criaButtonsVenderCasa(int y, Territorio t) {
		JButton casaBtn = new JButton("Vender Casa");
		casaBtn.setFont(new Font("", Font.BOLD, 9));
		casaBtn.setBounds(320, y, 120, 40);
		PaintComponent.setButtonColor(casaBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		casaBtn.addActionListener(this);
		venderCasaBtns.add(casaBtn);
		panel.add(casaBtn);
		casaBtn.setEnabled( (t.getQtdSedes() > 0) && validaVendaCasa(t) );
	}
	
	private boolean validaVendaCasa(Territorio t) {
		if(t.getColor() == UIColors.GREEN) return verificaTerritoriosVenderCasa(t, territoriosGreen);
		else if(t.getColor() == UIColors.RED) return verificaTerritoriosVenderCasa(t, territoriosRed);
		else if(t.getColor() == UIColors.BLUE) return verificaTerritoriosVenderCasa(t, territoriosBlue);
		else if(t.getColor() == UIColors.YELLOW) return verificaTerritoriosVenderCasa(t, territoriosYellow);
		else if(t.getColor() == UIColors.ORANGE) return verificaTerritoriosVenderCasa(t, territoriosOrange);
		else if(t.getColor() == UIColors.PURPLE) return verificaTerritoriosVenderCasa(t, territoriosPurple);
		return false;
	}
	
	private boolean verificaTerritoriosVenderCasa(Territorio t, ArrayList<Territorio> territorios) {
		if(territorios.size() > 1) {
			for(Territorio g : territorios) {
				if(!g.equals(t)) { // Outros territorios
					if( g.getQtdSedes() > t.getQtdSedes() ) {
						return false;
					}
				}
			}
		}
		return true;
	}
	
	private void criaButtonsComite(int y, Territorio t) {
		buttonText = (!t.getTemComite()) ? "Construir Comitê" : "Vender Comitê";
		JButton comiteBtn = new JButton(buttonText);
		comiteBtn.setFont(new Font("", Font.BOLD, 9));
		comiteBtn.setBounds(190, y, 120, 40);
		PaintComponent.setButtonColor(comiteBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		comiteBtn.addActionListener(this);
		comiteBtns.add(comiteBtn);
		panel.add(comiteBtn);
		if(comiteBtn.getText() == "Construir Comitê") comiteBtn.setEnabled( (t.getQtdSedes() == 4) );
		
	}
	
	private void preencheNegociarOptions() {
		venderOptions = new ArrayList<String>();
		venderOptions.add("Banco");
		for(int i=0; i<Game.getInstance().getQtdPlayers(); i++) {
			if(i != Game.getInstance().getCurrentPlayerID()) {
				venderOptions.add("Jogador " + (i+1));
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		for(int i=0; i< negociarBtns.size(); i++) { // Verifica se clicou no botão Vender ou Comprar
			if(event.getSource() == negociarBtns.get(i)) {
				Empreendimento emp = empsList.get(i);
				if(negociarBtns.get(i).getText() == "Vender") Game.getInstance().venderEmpreendimento(emp, player, venderOptions);
				else Game.getInstance().comprarEmpreendimentoPlayer(emp, player);
			}
		}
		
		if(Game.getInstance().getCurrentPlayerID() == player.getID()) {  // Operações exclusivas do Dono
			
			for(int i=0; i< hipotecarBtns.size(); i++) { // Verifica se clicou no botão Hipotecar
				if(event.getSource() == hipotecarBtns.get(i)) {
					Empreendimento emp = empsList.get(i);
					if(hipotecarBtns.get(i).getText() == "Hipotecar") Game.getInstance().hipotecarEmpreendimento(emp);
					else Game.getInstance().desfazerHipoteca(emp);
					
				}
			}
			
			for(int i=0; i< construirCasaBtns.size(); i++) { // Verifica se clicou no botão Construir Casa
				if(event.getSource() == construirCasaBtns.get(i)) {
					Territorio t = territoriosList.get(i);
					Game.getInstance().construirCasa(t);
				}
			}
			
			for(int i=0; i< venderCasaBtns.size(); i++) { // Verifica se clicou no botão vender Casa
				if(event.getSource() == venderCasaBtns.get(i)) {
					Territorio t = territoriosList.get(i);
					Game.getInstance().venderCasa(t);
				}
			}
			
			for(int i=0; i< comiteBtns.size(); i++) { // Verifica se clicou no botão comitê
				if(event.getSource() == comiteBtns.get(i)) {
					Territorio t = territoriosList.get(i);
					if(comiteBtns.get(i).getText() == "Construir Comitê") Game.getInstance().construirComite(t);
					else Game.getInstance().venderComite(t);
				}
			}
		}
	}
}
