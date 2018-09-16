package com.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.controller.GameController;
import com.infrastruture.Constants;
import com.infrastruture.Element;


@SuppressWarnings("serial")
public class GUI extends JFrame implements Element{
	protected Logger log = Logger.getLogger(GUI.class);
	private JTextField filename = new JTextField(), dir = new JTextField();	
	private GamePanel boardPanel;
	private JLabel exitLabel;
	private GameController driver;
	private JPanel mainPanel;
	private StaticPanel timerPanel;
	private ArrayList<Element> elementList;
	private JSONObject jsonObject;
	private JSONParser parser;
	
	public GUI() {
		boardPanel = new GamePanel();
		timerPanel = new StaticPanel();
		elementList = new ArrayList<>();
		initializeUI();
	}

	public GUI(GamePanel boardPanel,StaticPanel timerPanel)
	{
		super("Breakout Game");
		this.boardPanel = boardPanel;
		this.timerPanel = timerPanel;
		elementList = new ArrayList<>();
		initializeUI();
	}
	
	public StaticPanel getStaticPanel() {
		return (timerPanel);
	}

	public void changeUI()
	{
		//boardPanel.repaint();
		boardPanel.paintImmediately(0, 0, Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT);
		timerPanel.repaint();
	}


	private void createBoardPanel() {

		boardPanel.setLayout(new GridBagLayout());
        boardPanel.setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT));
	    boardPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    boardPanel.setMaximumSize(new Dimension(Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT));
		boardPanel.setBackground(Color.black);
		
		exitLabel = new JLabel();
		exitLabel.setForeground(Color.WHITE);
		exitLabel.setAlignmentX(SwingConstants.CENTER);
		exitLabel.setAlignmentY(SwingConstants.CENTER);
		Font font = new Font("Helvetica", Font.BOLD,50);
		
		exitLabel.setFont(font);
		boardPanel.add(exitLabel);
		boardPanel.setMaximumSize(new Dimension(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT));
		mainPanel.add(boardPanel);
		elementList.add(boardPanel);
	}
	private void initializeUI() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
       
        mainPanel.add(timerPanel);
        elementList.add(timerPanel);
        createBoardPanel();
        
		add(mainPanel);
		
		mainPanel.setPreferredSize(new Dimension(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT));
		mainPanel.setFocusable(true);
		mainPanel.requestFocusInWindow();
		setSize(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);	
	}
	public void removeKeyListner() {
		mainPanel.removeKeyListener(driver);
	}
	public void addDriver(GameController driver){
		this.driver = driver;
		mainPanel.addKeyListener(driver);
        timerPanel.createButtons(driver);
	
	}
	public void changeFocus()
	{
		mainPanel.requestFocus();
	}
	public void addGameOverPane() {
		exitLabel.setText("Game Over");
		boardPanel.repaint();
	}
	public GamePanel getBoardPanel() {
		return boardPanel;
	}

	public void setBoardPanel(GamePanel boardPanel) {
		this.boardPanel = boardPanel;
	}

	public StaticPanel getTimerPanel() {
		return timerPanel;
	}

	public void setTimerPanel(StaticPanel timerPanel) {
		this.timerPanel = timerPanel;
	}

	@Override
	public JSONObject save() {
		// TODO Auto-generated method stub
		jsonObject = new JSONObject();
		try {
			JFileChooser c = new JFileChooser();
			int rVal = c.showSaveDialog(boardPanel);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        filename.setText(c.getSelectedFile().getName());
		        dir.setText(c.getCurrentDirectory().toString());
		        for (Element element : elementList) {
					jsonObject.put(element.getClass().toString(), element.save());
				}
				FileWriter file = new FileWriter(dir.getText() + "\\" + filename.getText());
				file.write(jsonObject.toJSONString());
				file.flush();
		      }
		      if (rVal == JFileChooser.CANCEL_OPTION) {
		        filename.setText("You pressed cancel");
		        dir.setText("");
		      }
		} catch (Exception e) {
			log.error(e.getMessage());
		} 
		
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		int brickCount = 0;
		try {
			JFileChooser c = new JFileChooser();
			int rVal = c.showOpenDialog(boardPanel);
		      if (rVal == JFileChooser.APPROVE_OPTION) {
		        filename.setText(c.getSelectedFile().getName());
		        dir.setText(c.getCurrentDirectory().toString());
		        parser = new JSONParser();
				Object obj = parser.parse(new FileReader(dir.getText() + "\\" + filename.getText()));
				jsonObject = (JSONObject) obj;
				
				for (Element element : elementList) {
					if(element.getClass().toString().contains("GamePanel")) {
						brickCount = element.load(jsonObject.get(element.getClass().toString()));
					}else {
						element.load(jsonObject.get(element.getClass().toString()));
					}
				}
		      }
		      if (rVal == JFileChooser.CANCEL_OPTION) {
		        filename.setText("You pressed cancel");
		        dir.setText("");
		      }
			
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return brickCount;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addElement(Element e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeElement(Element e) {
		// TODO Auto-generated method stub
		
	}	
}
