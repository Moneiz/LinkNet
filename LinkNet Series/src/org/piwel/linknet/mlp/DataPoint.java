package org.piwel.linknet.mlp;

import org.piwel.linknet.parser.CharParser;

/**
 * 
 * @author Atomix
 * 
 * 
 * Cette classe encapsule les valeurs d'entrée et de sortie des exemples/tests
 * 
 *
 */
public class DataPoint {

	private double[] inputs;
    private double[] outputs;
    private String[] inputsOriginal;
    private String[] outputsOriginal;
    private String type;
    
    /**
     * 
     * @return Tableau des valeurs d'entrée
     */
    public double[] getInputs() {
    	return inputs;
    }
    /**
     * 
     * @return Tableau des valeurs de sortie
     */
    public double[] getOutputs() {
    	return outputs;
    }
    /**
     * 
     * Constructeur de la classe. Il permet de séparer les valeurs de str
     * et ensuite de les encapsuler et ranger dans les tableaux I/O selon
     * le masque outputNb saisi.
     * 
     * @param str Ligne en chaine de caractère des valeurs de l'exemple
     * @param outputNb Nombre de valeurs de sortie (masque de séparation I/O)
     */
    public DataPoint(String str, int outputNb)
    {
        String[] content = str.split("\t");
        inputsOriginal = new String[content.length - outputNb];
        for(int i = 0; i < inputsOriginal.length; i++)
        {
            inputsOriginal[i] = content[i];
        }
        outputsOriginal = new String[outputNb];
        for(int i = 0; i < outputNb; i++)
        {
            outputsOriginal[i] = content[content.length - outputNb + i];
        }
    }
    
    /**
     * 
     * @param type Type d'information à traiter
     */
    public void setAndApplyType(String type) {
    	this.type = type;
    	
    	if(type.equals("char")) {
    		int lengthInput = 0;
    		for(int i = 0; i < inputsOriginal.length;i++){
    			lengthInput += 1;
    		}
    		int lengthOutput = 0;
    		for(int i = 0; i < outputsOriginal.length;i++){
    			lengthOutput+= 1;
    		}
    		
    		inputs = new double[lengthInput * 8];
    		outputs = new double[lengthOutput * 8];
    		
    		for(int i = 0; i < inputsOriginal.length;i++){
    			CharParser charParser = new CharParser(inputsOriginal[i], null);
    			for(int k = 0; k < 8; k++) {
    				inputs[k + 8 * i] = charParser.getDatas(k);
    			}
    		}
    		for(int i = 0; i < outputsOriginal.length;i++){
    			CharParser charParser = new CharParser(outputsOriginal[i],null);
    			for(int k = 0; k < 8; k++) {
    				outputs[k + 8 * i] = charParser.getDatas(k);
    			}
    		}

    	}
    	if(type.equals("bit")) {
    		int lengthInput = 0;
    		for(int i = 0; i < inputsOriginal.length;i++){
    			lengthInput += 1;
    		}
    		int lengthOutput = 0;
    		for(int i = 0; i < outputsOriginal.length;i++){
    			lengthOutput+= 1;
    		}
    		
    		inputs = new double[lengthInput];
    		outputs = new double[lengthOutput];
    		
    		for(int i = 0; i < inputsOriginal.length;i++){
    			inputs[i] = Double.parseDouble(inputsOriginal[i]);
    		}
    		for(int i = 0; i < outputsOriginal.length;i++){
    			outputs[i] = Double.parseDouble(outputsOriginal[i]);
    		}
    	}
    }
    	
    	 /**
         * 
         * @param type 
         */
        public void unsetAndUnapplyType() {
        	if(type.equals("char")) {
        		int lengthInput = 0;
        		for(int i = 0; i < inputsOriginal.length;i++){
        			lengthInput += 1;
        		}
        		int lengthOutput = 0;
        		for(int i = 0; i < outputsOriginal.length;i++){
        			lengthOutput+= 1;
        		}
        		
        		inputs = new double[lengthInput * 8];
        		outputs = new double[lengthOutput * 8];
        		
        		for(int i = 0; i < inputsOriginal.length;i++){
        			CharParser charParser = new CharParser(null, inputs);
        			inputsOriginal[i] = charParser.unconvert();
        		}
        		for(int i = 0; i < outputsOriginal.length;i++){
        			CharParser charParser = new CharParser(null,outputs);
        			outputsOriginal[i] = charParser.unconvert();
        		}

        	}
        	if(type.equals("bit")) {
        		int lengthInput = 0;
        		for(int i = 0; i < inputsOriginal.length;i++){
        			lengthInput += 1;
        		}
        		int lengthOutput = 0;
        		for(int i = 0; i < outputsOriginal.length;i++){
        			lengthOutput+= 1;
        		}
        		
        		inputs = new double[lengthInput];
        		outputs = new double[lengthOutput];
        		
        		for(int i = 0; i < inputsOriginal.length;i++){
        			inputsOriginal[i] = Double.toString(inputs[i]);
        		}
        		for(int i = 0; i < outputsOriginal.length;i++){
        			outputsOriginal[i] = Double.toString(outputs[i]);
        		}
        	}
        	
    	
    	
    }
   
}
