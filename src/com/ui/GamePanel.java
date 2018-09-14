package com.ui;

import java.awt.Graphics;

import com.behavior.FlowLayoutBehavior;
import com.image.*;
import java.util.ArrayList;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.apache.log4j.Logger;
import org.json.simple.JsonObject;

import com.infrastruture.AbstractPanel;
import com.infrastruture.Constants;
import com.infrastruture.Element;


@SuppressWarnings("serial")
public class GamePanel extends AbstractPanel implements Element {
	protected Logger log = Logger.getLogger(GamePanel.class);
	private BufferedImage image;
	private ArrayList<Element> elements;
	private JsonObject jsonObject;
	
	public GamePanel()
	{
	    elements = new ArrayList<Element>();
        try {
            image = ImageIO.read(new File("./src/com/image/nature.jpg"));
            image = resize(image, Constants.BOARD_PANEL_HEIGHT, Constants.BOARD_PANEL_WIDTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	log.error(e.getMessage());
        }
        setLayout();
	}

	/*
	@Override
	public void performUpdateLayout(AbstractPanel abstractPanel, int width, int height) {
		super.performUpdateLayout(abstractPanel, Constants.BOARD_PANEL_WIDTH, Constants.BOARD_PANEL_HEIGHT);
	    setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    setMaximumSize(new Dimension(Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT));
		setBackground(Color.black);
		setMaximumSize(new Dimension(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT));
	}
	*/
	
	public void setLayout() {
//		setLayout(new GridBagLayout());
//        setPreferredSize(new Dimension(Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT));
		setLayoutBehavior(new FlowLayoutBehavior());
		performUpdateLayout(this, Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT);

		/*
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
	    setMaximumSize(new Dimension(Constants.BOARD_PANEL_WIDTH,Constants.BOARD_PANEL_HEIGHT));
		setBackground(Color.black);
		setMaximumSize(new Dimension(Constants.FRAME_WIDTH,Constants.FRAME_HEIGHT));
		*/
	}

	
	public void performUpdateLayout(AbstractPanel abstractPanel, int width, int height) {
		super.performUpdateLayout(abstractPanel, width, height);
        try {
            image = ImageIO.read(new File("./src/com/image/nature.jpg"));
            image = resize(image, width, height);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }		
	}
	
	
	private BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	@Override
	public void paintComponent(Graphics g){
//		System.out.println("GamePanel::paintComponent");
		super.paintComponent(g);
		if (image != null) {
	        g.drawImage(image, 0, 0, this);
	    }
		for(Element element : elements)
		{
			element.draw(g);
		}
	}

	

	@Override
	public void draw(Graphics g) {
//		System.out.println("GamePanel::draw");
		repaint();
	}

	@Override
	public void reset() {
		for(Element element : elements) {
			element.reset();
		}
	}

	public void addComponent(Element e) {
//		System.out.println("Add component in GamePanel (boardPanel)");
		this.add((Component)e);
		elements.add(e);
	}
	

	@Override
	public void removeComponent(Element e) {
		elements.remove(e);
	}

	@Override
	public JsonObject save() {
		jsonObject = new JsonObject();
		String className= "";
		int count = 1;
		try {
			for (Element element : elements) {
				if(element.getClass().toString().contains("Brick")) {
					jsonObject.put(element.getClass().toString()+"_"+count, element.save());
					count++;
				}else {
					jsonObject.put(element.getClass().toString(), element.save());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return jsonObject;
	}

	@Override
	public int load(Object object) {
		jsonObject = (JsonObject) object;
		int count = 1;
		int visibilityFlag=0;
		int brickCount = 0;
		for (Element element : elements) {
			if(element.getClass().toString().contains("Brick")) {
				visibilityFlag = element.load(jsonObject.get(element.getClass().toString()+"_"+count));
				count++;
				if(visibilityFlag == 1) {
					brickCount++;
				}
			}else {
				element.load(jsonObject.get(element.getClass().toString()));
			}
		}	
		return brickCount;
	}

}
