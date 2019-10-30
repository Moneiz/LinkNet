package org.piwel.linknet.graphic;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.lang.invoke.CallSite;
import java.text.DecimalFormat;
import java.util.jar.JarInputStream;

import javax.print.attribute.standard.OutputDeviceAssigned;
import javax.swing.*;

import org.piwel.linknet.mlp.DataCollection;
import org.piwel.linknet.mlp.DataPoint;
import org.piwel.linknet.mlp.NeuralNetwork;
import org.piwel.linknet.mlp.NeuralSystem;
import org.piwel.linknet.mlp.Neuron;
import org.piwel.linknet.parser.CharParser;

public class Window extends JFrame{

	private DrawPanel panel;
	private NeuralSystem neuralSystem; 
	private int width=1280, height=720;
	private String model;
	private JButton buttonContinue;
	private JTextField[] label;
	public boolean autoMode=true;
	public String type;
	
	public Window(String model, String type) {
		this.model = model;
		this.type = type;
		setTitle(model);
		setSize(width, height);
		panel = new DrawPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		
		
		this.addComponentListener(new ComponentListener() {
		
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				repositionize();
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		buttonContinue = new JButton("Continue !");

		buttonContinue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String value = "";
				for(int i = 0; i < label.length; i++) {
				
					if(label[i].getText() != "") {
					
						
						value += label[i].getText() + "\t";
						
					}
					else {
						System.err.println("Can't use this value. Only 0 or 1.");
						return;
					}
				}
				for(int i = 0; i < neuralSystem.getNeuralNetwork().getNbOutputs(); i++) {
					value += "0";
					
					if(i != neuralSystem.getNeuralNetwork().getNbOutputs() -1) {
						value += "\t";
					}
				}
				neuralSystem.testInput(value);
				autoMode = false;
				
				reDraw();
				
			}
		});
		
		add(panel);
		buttonContinue.setLocation(0,0);
		buttonContinue.setSize(100, 20);
		panel.add(buttonContinue);

	}
	public void reDraw() {
		try {
			SwingUtilities.updateComponentTreeUI(this);
			invalidate();
			validate();
			repaint();
		}catch(NullPointerException ex) {
			
		}
		
		
		
		
		
		repositionize();
		
	}
	
	
	
	public void linkToMLP(NeuralSystem neuralSystem) {
		this.neuralSystem = neuralSystem;
		addLabel(neuralSystem);
	}
	
	private void repositionize() {
		for(int i = 0; i < label.length; i++) {
			int x = (int) (width/(4));
			int yI = (int) (height/(label.length+1));
			
			label[i].setLocation(x-75, yI*(i+1));
			if(i == label.length -1) {
				buttonContinue.setLocation(x-75, yI*(i+1)+30);
			}
		}
		
	}
	
	private void addLabel(NeuralSystem neuralSystem) {
		label = new JTextField[neuralSystem.getNeuralNetwork().getNbInputs()];
		for(int i = 0; i < label.length; i++) {
			label[i] = new JTextField("0",1);
			//panel.add(label[i]);
			int x = (int) (width/(4));
			int yI = (int) (height/(label.length+1));
			
			label[i].setLocation(x-75, yI*(i+1));
			label[i].setSize(50, 20);
			panel.add(label[i]);
			
			if(i == label.length -1) {
				buttonContinue.setLocation(x-75, yI*(i+1)+30);
			}
			
			
			//remove(panel);
			//add(panel);
		}
	}
	
	private class DrawPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			height = (int) getSize().getHeight();
			width = (int) getSize().getWidth();
			

			
			NeuralNetwork net = neuralSystem.getNeuralNetwork();	
			
			int x = (int) (width/(4));
			int yI = (int) (height/(net.getNbInputs()+1));
			int yH = (int) (height/(net.getNbHidden()+1));
			int yO = (int) (height/(net.getNbOutputs()+1));
			
			if(net.getNbInputs() >=8) {
				//return;
			}
			
			for(int i = 1; i <= net.getNbInputs(); i++) {
				
				DataPoint c = neuralSystem.getCurrentDataPoint();
				double output;
				//try {
					output = c.getInputs()[i-1];
				//}catch(NullPointerException ex) {
				//	DataPoint[] points = neuralSystem.getDataCollection().points();
				//	output = points[points.length -1 ].getInputs()[i-1];
				//}
				
				int isPositive = output >= 0 ? 1 : 0;
				int isNegative = output <= 0 ? 1 : 0;
				
				g.setColor(new Color((int)(255*output * isPositive), (int)(255*output * isPositive), (int)(255*output * isPositive)));

				g.fillOval(x, yI*i, 20, 20);
				
				String value = new DecimalFormat("#.##").format(output);
				g.drawString(value,x, yI*i -20);
			}
			
			for(int i = 1; i <= net.getNbHidden(); i++) {
				double output = net.getHiddenNeurons()[i-1].getOutput();
				
				int isPositive = output >= 0 ? 1 : 0;
				int isNegative = output <= 0 ? 1 : 0;
				
				g.setColor(new Color((int)(255*output * isPositive), (int)(255*output * isPositive), (int)(255*output * isPositive)));
				
				g.fillOval(x*2, yH*i, 20, 20);
				
				String value = new DecimalFormat("#.##").format(output);
				g.drawString(value,x*2, yH*i -20);
			}
			if(type.equals("bit")) {
				for(int i = 1; i <= net.getNbOutputs(); i++) {
					
					double output = net.getOutputNeurons()[i-1].getOutput();
					
					int isPositive = output >= 0 ? 1 : 0;
					int isNegative = output <= 0 ? 1 : 0;
					
					g.setColor(new Color((int)(255*output * isPositive), (int)(255*output * isPositive), (int)(255*output * isPositive)));
					
					
					g.fillOval(x*3, yO*i, 20, 20);
					String value = new DecimalFormat("#.##").format(output);
					g.drawString(value,x*3, yO*i -20);
				}
			}
			else if(type.equals("char")){
				double[] bits = new double[8];
				String output;
				for(int i = 7; i>= 0; i++) {
					bits[i] = (int)net.getOutputNeurons()[i].getOutput();
				}
				output = new CharParser(null, bits).unconvert();
					
				g.setColor(new Color(255,255,255));
					
					
				g.fillOval(x*3, yO, 20, 20);
				String value = output;
				g.drawString(value,x*3, yO -20);
				
			}
			
			
			for(int i = 1; i <= net.getNbInputs();i++) {
				for(int j = 1; j <=net.getNbHidden(); j++) {
					
					
					
					g.setColor(getColorLink(net.getHiddenNeurons()[j-1].weight(i-1) * neuralSystem.getCurrentDataPoint().getInputs()[i-1]));
					
					
					g.drawLine(x+10, yI*i+10, x*2+10, yH*j+10);
					
				}
			}
			
			for(int i = 1; i <= net.getNbHidden();i++) {
				for(int j = 1; j <=net.getNbOutputs(); j++) {
					
					
					
					g.setColor(getColorLink(net.getOutputNeurons()[j-1].weight(i-1) * net.getHiddenNeurons()[i-1].getOutput()));
					
					g.drawLine((x*2)+10, yH*i+10, x*3+10, yO*j+10);
				}
			}
			g.setColor(Color.WHITE);
			try {
				String c = "Error rate : "+ new DecimalFormat("#.#").format(neuralSystem.getErrorRate())  + " %";
				g.drawString(c, 20,20 );
			}catch(ArrayIndexOutOfBoundsException ex) {}
			g.setColor(Color.WHITE);
			if(neuralSystem.isRunning == true) {
				setTitle(model + " (learning...)");
				g.drawString("Process : Learning...Please wait", 20, height - 20);
			}else {
				setTitle(model + " (ready !)");
				g.drawString("Process : Ready !", 20, height - 20);
			}
			
			for(int i = -50; i <= 50; i+=10) {
				
				int isPos = i <= 0 ? 1 : 0;
				
				if(i <= 0) {
					g.setColor(new Color(0,(int)(255*-i/100), 50));
				}
				else {
					g.setColor(new Color((int)(255*i/100),0, 50));
				}
				
				
				
				g.fillRect(width-150+i, +50, 10, 10);
			}
			g.setColor(Color.WHITE);
			g.drawString("-1", width-200, 30);
			g.drawString("1", width-100, 30);
			
			
		}
		private Color getColorLink(double value) {
			double intensity;
			try {
				intensity = value;
				intensity = intensity > 1 ? 1 : intensity;
				intensity = intensity < -1 ? -1 : intensity;
			}
			catch(Exception ex) {
				intensity = 0.5;
				ex.printStackTrace();
			}
			
			
			int isPositive = intensity >= 0 ? 1 : 0;
			int isNegative = intensity <= 0 ? 1 : 0;
			
			return new Color((int)(255* intensity * isPositive), (int)(255* -intensity * isNegative), 50);
		}
	}	
	
}
