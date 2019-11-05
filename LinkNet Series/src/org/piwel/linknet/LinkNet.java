package org.piwel.linknet;

import org.piwel.linknet.mlp.NeuralSystem;

import java.lang.Thread;
import org.piwel.linknet.parser.header.HeaderEvaluationException;
import org.piwel.linknet.parser.header.HeaderHandler;
import org.piwel.linknet.util.DatasetFileProvider;
import org.piwel.linknet.data.SimpleData;
import org.piwel.linknet.graphic.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.piwel.linknet.mlp.DataPoint;
import org.piwel.linknet.mlp.IHM;

public class LinkNet implements IHM{

	private final String version = "indev-051119";
	private static String filename;
	Window win;
	
	//L'appel du programme
	
	private LinkNet() {
		printMsg("Init Stratos " + version);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		filename = args[0];
		LinkNet linkNet = new LinkNet();
        linkNet.run();
       
	}


    private void run()
    {
    	DatasetFileProvider dataset = new DatasetFileProvider(filename);
    	SimpleData datas = dataset.getDatas();
    	
    	/*
        NeuralSystem system = new NeuralSystem(datas.nbNeuronIn,datas.nbMiddleHiddenNeuron,datas.nbNeuronOut , datas.datapoints, 1, this);
        
        HeaderHandler headerHandler = null;
        
        try {
        	headerHandler = new HeaderHandler(header);
			
		} catch (HeaderEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		}
        
        win = new Window(model, headerHandler.getType());
        win.linkToMLP(system);
        
        if(headerHandler.getType().equals("bit")) {
        	win.setVisible(true);
        }        
        system.addWindow(win);
        system.run();
        
        system.testInput("a");

        //system.testInput();

        printMsg("End of learning !");

*/
    }


	@Override
	public void printMsg(String msg) {
		System.out.println("["+(new Date()).toGMTString()+"] [INFO] - " + msg);
	}
}
