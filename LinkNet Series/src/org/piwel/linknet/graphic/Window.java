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


//All this file will be reviewed in the next versions
public class Window extends JFrame{

	private DrawPanel panel;
	private NeuralSystem neuralSystem; 
	private int width, height;
	private String model;
	private JButton buttonContinue;
	private JTextField[] label;
	public boolean autoMode=true;
	public String type;
	
	public Window(String model, Setting s) {
		this.model = model;
		this.width = s.width;
		this.height = s.height;
		this.type = "bit";
		setTitle(model);
		setSize(width, height);
		panel = new DrawPanel();
		panel.setBackground(Color.BLACK);
		panel.setLayout(null);
		
		
		this.addComponentListener(new ComponentListener() {
		
			
			@Override
			public void componentResized(ComponentEvent e) {
				// TODO Auto-generated method stub
				//repositionize();
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
		
		/*
		buttonContinue = new JButton("Continue !");

		buttonContinue.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				double[] value = new double[label.length+neuralSystem.getNeuralNetwork().getNbOutputs()];
				for(int i = 0; i < label.length; i++) {
				
					if(label[i].getText() != "") {
					
						
						value[i] = Double.parseDouble(label[i].getText());
						
					}
					else {
						System.err.println("Can't use this value. Only 0 or 1.");
						return;
					}
				}
				for(int i = 0; i < neuralSystem.getNeuralNetwork().getNbOutputs(); i++) {
					value[i+label.length] = 0.0d;
				}
				neuralSystem.testInput(value);
				autoMode = false;
				
				reDraw();
				
			}
		});
		
		
		buttonContinue.setLocation(0,0);
		buttonContinue.setSize(100, 20);
		panel.add(buttonContinue);
		*/
		add(panel);
	}
	public void reDraw() {
		try {
			SwingUtilities.updateComponentTreeUI(this);
			invalidate();
			validate();
			repaint();
		}catch(NullPointerException ex) {
			ex.printStackTrace();
		}
		
		
		
		
		
		//repositionize();
		
	}
	
	
	
	public void linkToMLP(NeuralSystem neuralSystem) {
		this.neuralSystem = neuralSystem;
		//addLabel(neuralSystem);
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
			int yI = (int) (height/(net.getNbHidden()[0]+1));
			int yH = (int) (height/(net.getNbHidden()[1]+1));
			int yO = (int) (height/(net.getNbOutputs()+1));
			
			if(net.getNbInputs() >=8) {
				//return;
			}
			
			for(int i = 1; i <= net.getNbHidden()[0]; i++) {

				double output = net.getHiddenNeurons()[0][i-1].getOutput();
				
				int isPositive = output >= 0 ? 1 : 0;
				int isNegative = output <= 0 ? 1 : 0;
				
				g.setColor(new Color((int)(255*output * isPositive), (int)(255*output * isPositive), (int)(255*output * isPositive)));

				g.fillOval(x, yI*i, 20, 20);
				
				String value = new DecimalFormat("#.##").format(output);
				g.drawString(value,x, yI*i -20);
			}
			
			for(int i = 1; i <= net.getNbHidden()[1]; i++) {
				double output = net.getHiddenNeurons()[1][i-1].getOutput();
				
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
			
			
			for(int i = 1; i <= net.getNbHidden()[0];i++) {
				for(int j = 1; j <=net.getNbHidden()[1]; j++) {
					
					g.setColor(getColorLink(net.getHiddenNeurons()[1][j-1].weight(i-1) * net.getHiddenNeurons()[0][i-1].getOutput()));
					
					g.drawLine(x+10, yI*i+10, x*2+10, yH*j+10);
					
				}
			}
			
			for(int i = 1; i <= net.getNbHidden()[1];i++) {
				for(int j = 1; j <=net.getNbOutputs(); j++) {
					
					
					
					g.setColor(getColorLink(net.getOutputNeurons()[j-1].weight(i-1) * net.getHiddenNeurons()[1][i-1].getOutput()));
					
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
