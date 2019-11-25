package org.piwel.linknet;

import org.piwel.linknet.mlp.NeuralSystem;
import org.piwel.linknet.util.DatasetFileProvider;
import org.piwel.linknet.util.RequestPOST;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.DocFlavor.STRING;

import org.piwel.linknet.data.SimpleData;
import org.piwel.linknet.data.image.BMPFormatter;
import org.piwel.linknet.graphic.Setting;
import org.piwel.linknet.graphic.UIManager;
import org.piwel.linknet.mlp.IHM;

public class LinkNet{

	private final String version = "alpha-3.0";
	private static String filename;
	
	//L'appel du programme
	
	private LinkNet() {
		IHM.info("Init Stratos " + version);
	}
	
	public static void main(String[] args) {
		
		filename = args[0];
		
		LinkNet linkNet = new LinkNet();
        linkNet.run();
       
	}


    private void run()
    {
    	DatasetFileProvider dataset = new DatasetFileProvider(filename);
    	SimpleData datas = dataset.getDatas();
    	
    	
        NeuralSystem system = new NeuralSystem(datas.nbNeuronIn,datas.nbMiddleHiddenNeuron,datas.nbNeuronOut , datas.getDatapoints(), 1);

        Setting s = Setting.readSettings();
        
        UIManager windows = new UIManager(datas.json, s);
   
        //system.run();

    }

}
