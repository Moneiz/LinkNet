package org.piwel.linknet.mlp;

import java.io.Serializable;

/**
 * 
 * @author Atomix
 * 
 * 
 * Cette classe encapsule les valeurs d'entrée et de sortie des exemples/tests
 * 
 *
 */
public class DataPoint implements Serializable {

	private static final long serialVersionUID = -7567571565157L;
	
	private double[] inputs;
    private double[] outputs;
    
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
    public DataPoint(double[] content, int outputNb)
    {
        inputs = new double[content.length - outputNb];
        for(int i = 0; i < inputs.length; i++)
        {
            inputs[i] = content[i];
        }
        outputs = new double[outputNb];
        for(int i = 0; i < outputNb; i++)
        {
            outputs[i] = content[content.length - outputNb + i];
        }
    }
   
}
