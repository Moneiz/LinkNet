package org.piwel.linknet;

import org.piwel.linknet.mlp.NeuralSystem;
import org.piwel.linknet.util.DatasetFileProvider;
import org.piwel.linknet.data.SimpleData;
import org.piwel.linknet.graphic.Window;
import org.piwel.linknet.mlp.IHM;

public class LinkNet{

	private final String version = "alpha-1.0";
	private static String filename;
	Window win;
	
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

        
        win = new Window("Stratos Graphic");
        win.linkToMLP(system);

        win.setVisible(true);
        
        system.addWindow(win);
        system.run();

    }

}
