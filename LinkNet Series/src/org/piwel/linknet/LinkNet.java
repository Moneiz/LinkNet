package org.piwel.linknet;

import org.piwel.linknet.mlp.NeuralSystem;

import java.lang.Thread;
import org.piwel.linknet.parser.header.HeaderEvaluationException;
import org.piwel.linknet.parser.header.HeaderHandler;
import org.piwel.linknet.graphic.Window;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.piwel.linknet.mlp.DataPoint;
import org.piwel.linknet.mlp.IHM;

public class LinkNet implements IHM{

	private final String model = "LN - 6";
	private static String filename = "simple.txt";
	private static int nbIn, nbOut, nbHidden;
	Window win;
	
	//L'appel du programme
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		filename = args[0];
		nbIn = Integer.parseInt(args[1]);
		nbHidden = Integer.parseInt(args[2]);
		nbOut = Integer.parseInt(args[3]);
		LinkNet linkNet = new LinkNet();
        linkNet.run();
       
	}


    private void run()
    {

        String[] content = readFile(filename, true);
        String[] header = readHeaderFile(filename);
        NeuralSystem system = new NeuralSystem(nbIn,nbHidden,nbOut , content, header, 1, this);
        
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


    }
    private String[] readFile(String filename, boolean removeFirstLine)
    {
    	File file = new File(filename);
    	BufferedReader reader = null;
    	
    	List<String> list = new ArrayList<String>();

    	try {
    	    reader = new BufferedReader(new FileReader(file));
    	    String text = null;

    	    while ((text = reader.readLine()) != null) {
    	    	if(!(removeFirstLine && text.charAt(0) == '#')) {
    	    		 list.add(text);
    	    	}
    	       
    	    }
    	} catch (FileNotFoundException e) {
    	    e.printStackTrace();
    	} catch (IOException e) {
    	    e.printStackTrace();
    	} finally {
    	    try {
    	        if (reader != null) {
    	            reader.close();
    	        }
    	    } catch (IOException e) {
    	    }
    	}
    	
    	return list.toArray(new String[0]);
    	
    }
    private String[] readHeaderFile(String filename)
    {
    	String[] content = readFile(filename, false);
    	List<String> list = new ArrayList<String>();
    	int i = 0;
    	for(String line : content) {
    		if(line.charAt(0) == '#') {
    			list.add(line);
    		}
    	}
        return list.toArray(new String[0]);
    }

    public void printMsg(String msg)
    {
        System.out.println(msg);
    }


}
