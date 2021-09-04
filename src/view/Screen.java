package view;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import controller.Game;

//Essa classe utiliza o Design Pattern Singleton

public class Screen extends JFrame implements ActionListener {
	private static final long serialVersionUID = 5544833518263193022L;
	private final int WIDTH_DEFAULT = 1000;
	private final int HEIGHT_DEFAULT = 800;
	private JMenuBar menuBar; 
	private JMenu menu; 
	private JMenuItem novo, load, save, quit;
	private JLabel tabuleiroImg = null;
	private JLabel dice1, dice2 = null;
	private static Screen screen;
	private ScreenButtons screenButtons = null;
	
	private Screen() {
		
	}
	
	public static Screen getInstance(){
		if(screen == null){
			screen = new Screen();
		}
		return screen;
	}
	
	public void initializeScreen() {
		setTitle("Banco Imobiliário");
		setLayout(null);
		setResizable(false);
		setSize(WIDTH_DEFAULT,HEIGHT_DEFAULT);
		Screen.getInstance().criaMenu();
		Screen.getInstance().criaTabuleiro();
		Screen.getInstance().criaDices();
		ScreenPreview.criaPositionPreview();
		screenButtons = new ScreenButtons();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void criaDices() {
		ImageIcon img;
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/dices/dice6.png"))
					.getScaledInstance(40,40, Image.SCALE_SMOOTH));
			dice1 = new JLabel("", img, JLabel.CENTER);
			dice2 = new JLabel("", img, JLabel.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		dice1.setBounds(415, 560, 40, 40);
		dice2.setBounds(465, 560, 40, 40);
		tabuleiroImg.add(dice1);
		tabuleiroImg.add(dice2);
	}
	
	public void updateDices(int num1, int num2) {
		ImageIcon img;
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/dices/dice" + num1 +".png"))
					.getScaledInstance(40,40, Image.SCALE_SMOOTH));
			dice1.setIcon(img);
			img = new ImageIcon(ImageIO.read(new File("assets/dices/dice" + num2 +".png"))
					.getScaledInstance(40,40, Image.SCALE_SMOOTH));
			dice2.setIcon(img);
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	private void criaTabuleiro() {
		ImageIcon img;
		try {
			img = new ImageIcon(ImageIO.read(new File("assets/tabuleiroRJ.jpg"))
					.getScaledInstance(700, 750, Image.SCALE_SMOOTH));
			tabuleiroImg = new JLabel("", img, JLabel.CENTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		tabuleiroImg.setBounds(0,0, 700, 750);
		add(tabuleiroImg);
	}
	
	public JLabel getTabuleiroImg() {
		return tabuleiroImg;
	}
	
	public ScreenButtons getScreenButtonsInstance() {
		return screenButtons;
	}
	
	public void tabuleiroSetVisible(boolean status) {
		tabuleiroImg.setVisible(status);
	}
	
	private void criaMenu() {
	
		novo = new JMenuItem ("Novo Jogo");
		novo.addActionListener(this);
		load = new JMenuItem ("Carregar");
		load.addActionListener(this);
		save = new JMenuItem ("Salvar");
		save.addActionListener(this);
		quit = new JMenuItem ("Sair");
		quit.addActionListener(this);

		menu = new JMenu ("Menu");
		menu.addActionListener(this);
		
		menu.add(novo);
		menu.add(load);
		menu.add(save);
		menu.addSeparator();
		menu.add(quit);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(menu);
	}
	
	public void actionPerformed(ActionEvent event) {
		try {
			verificaEventosMenu(event);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void verificaEventosMenu (ActionEvent event) throws IOException {
		if (event.getSource() == quit){
			Game.getInstance().quitGame();
		}
		else if(event.getSource() == save) {
			Game.getInstance().saveGame();
		}
		else if(event.getSource() == load) {
			Game.getInstance().loadGame();
		}
		else if(event.getSource() == novo) {
			Screen.getInstance().tabuleiroSetVisible(false);
			Game.getInstance().newGame();
		}
	}
}
