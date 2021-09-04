package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import controller.Game;
import tools.UIColors;

public class ScreenButtons implements ActionListener{
	private static UIColors[] colors = new UIColors[] {UIColors.RED, UIColors.BLUE, UIColors.ORANGE, UIColors.YELLOW, UIColors.PURPLE, UIColors.BLACK};
	private static JButton comprarBtn, jogarDadosBtn, passarVezBtn, sairPrisaoBtn, falirBtn;
	
	public ScreenButtons() {
		jogarDadosBtn = new JButton("Jogar Dados");
		passarVezBtn = new JButton("Passar Vez");
		sairPrisaoBtn = new JButton("Sair Prisão");
		comprarBtn = new JButton("Comprar");
		falirBtn = new JButton("Falir");
		jogarDadosBtn.setBounds(400, 500, 120, 40);
		passarVezBtn.setBounds(400, 500, 120, 40);
		sairPrisaoBtn.setBounds(100, 110, 120, 40);
		comprarBtn.setBounds(300, 450, 120, 40);
		falirBtn.setBounds(180, 500, 120, 40);
		PaintComponent.setButtonColor(jogarDadosBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(passarVezBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(sairPrisaoBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(comprarBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(falirBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		jogarDadosBtn.addActionListener(this);
		passarVezBtn.addActionListener(this);
		sairPrisaoBtn.addActionListener(this);
		comprarBtn.addActionListener(this);
		falirBtn.addActionListener(this);
		Screen.getInstance().getTabuleiroImg().add(jogarDadosBtn);
		Screen.getInstance().getTabuleiroImg().add(passarVezBtn);
		Screen.getInstance().getTabuleiroImg().add(sairPrisaoBtn);
		Screen.getInstance().getTabuleiroImg().add(comprarBtn);
		Screen.getInstance().getTabuleiroImg().add(falirBtn);
		sairPrisaoBtn.setVisible(false);
		comprarBtn.setVisible(false);
		falirBtn.setVisible(false);
	}
	
	public static void updateButtonsColor() {
		PaintComponent.setButtonColor(jogarDadosBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(passarVezBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(sairPrisaoBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(comprarBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PaintComponent.setButtonColor(falirBtn, colors[Game.getInstance().getCurrentPlayerID()]);
		PlayersPainel.getInstance().updateSairButtonColor();
	}
	
	public static void falirBtnSetVisible(boolean status) {
		falirBtn.setVisible(status);
	}
	
	public static boolean falirBtnIsVisible() {
		return falirBtn.isVisible();
	}
	
	public static void comprarBtnSetVisible(boolean status) {
		comprarBtn.setVisible(status);
	}
	
	public static boolean comprarBtnIsVisible() {
		return comprarBtn.isVisible();
	}
	
	public static void jogarDadosBtnSetVisible(boolean status) {
		jogarDadosBtn.setVisible(status);
	}
	
	public static boolean jogarDadosBtnIsVisible() {
		return jogarDadosBtn.isVisible();
	}
	
	public static void passarVezBtnSetVisible(boolean status) {
		passarVezBtn.setVisible(status);
	}
	
	public static boolean passarVezBtnIsVisible() {
		return passarVezBtn.isVisible();
	}
	
	public static void sairPrisaoBtnSetVisible(boolean status) {
		sairPrisaoBtn.setVisible(status);
	}
	
	public static boolean sairPrisaoBtnIsVisible() {
		return sairPrisaoBtn.isVisible();
	}
	
	public static void allBtnSetVisible(boolean status) {
		jogarDadosBtn.setVisible(status);
		passarVezBtn.setVisible(status);
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == jogarDadosBtn){  // Clicou no botão Jogar Dados
			Game.getInstance().currentPlayerRollDices();
		}
		else if (event.getSource() == passarVezBtn){ // Clicou no botão Passar Vez
			Game.getInstance().currentPlayerPassTurn();
		}
		else if (event.getSource() == sairPrisaoBtn){  // Clicou no botão Sair Prisão
			int res = ScreenPopup.showConfirmDialog(Screen.getInstance(), "Deseja usar sua carta de saída livre?" , "Alerta");
			if(res == 0) { // Sim
				Game.getInstance().currentPlayerUsarCardSairPrisao();
			}
		}
		else if(event.getSource() == falirBtn){  // Clicou no botão Falir
			int res = ScreenPopup.showConfirmDialog(Screen.getInstance(), "Você será retirado do jogo, tem certeza?" , "Alerta");
			if(res == 0) { // Sim
				Game.getInstance().currentPlayerFaliu();
			}
		}
		else if (event.getSource() == comprarBtn){  // Clicou no botão Comprar
			Game.getInstance().comprarEmpreendimentoBanco();
		}
	}

}
