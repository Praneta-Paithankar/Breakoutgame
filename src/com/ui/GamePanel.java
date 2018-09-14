package com.ui;

import java.awt.Graphics;
import com.image.*;
import java.util.ArrayList;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import com.infrastruture.Constants;
import com.infrastruture.Element;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Element{
	
	private BufferedImage image;
	private ArrayList<Element> elements;
	private JSONObject jsonObject;
	
	public GamePanel()
	{
	    elements = new ArrayList<Element>();
        try {
            image = ImageIO.read(new File("./src/com/image/nature.jpg"));
            image = resize(image, Constants.BOARD_PANEL_HEIGHT, Constants.BOARD_PANEL_WIDTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	}
	
	public ArrayList<Element> getElements(){
		return elements;
	}
	
	private BufferedImage resize(BufferedImage img, int width, int height) {
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
	
	public void addElement(Element element){
		elements.add(element);
		
	}
	public void removeElement(Element element)
	{
		elements.remove(element);
	}
	@Override
	public void paintComponent(Graphics g){
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
	public JSONObject save() {
		jsonObject = new JSONObject();
		System.out.println("Game Panel");
		System.out.println(elements.size());
		try {
			for (Element element : elements) {
					jsonObject.put(element.getClass().toString(), element.save());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Game Panel Save :"+jsonObject.toString());
		return jsonObject;
	}

	@Override
	public void load(Object object) {
		System.out.println("---------------Game Panel Load-----------------");
		JSONObject jsonObject1 = (JSONObject) object;
		for (Element element : elements) {
			element.load(jsonObject1.get(element.getClass().toString()));
		}
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
}
