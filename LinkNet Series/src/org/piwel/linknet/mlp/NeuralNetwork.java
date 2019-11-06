package org.piwel.linknet.mlp;

/**
 * 
 * @author Atomix
 *
 */
public class NeuralNetwork {

	
	 private Neuron[] hiddenNeurons;
     private Neuron[] outputNeurons;
     private int nbInputs;
     private int nbHidden;
     private int nbOutputs;

     /**
      * Constructeur de la class. Il se charge de construire tous les neurones du réseau.
      * 
      * @param nbInputs Nombre de neurones d'entrée dans le réseau
      * @param nbHidden Nombre de neurones dans chaque calques cachés dans le réseau
      * @param nbOutputs Nombre de neurones de sortie dans le réseau
      * 
      */
     
     public NeuralNetwork(int nbInputs, int nbHidden, int nbOutputs)
     {
         this.nbHidden = nbHidden;

        
         this.nbInputs = nbInputs;
         this.nbOutputs = nbOutputs;

         hiddenNeurons = new Neuron[nbHidden];
         for(int i = 0; i < nbHidden; i++)
         {
             hiddenNeurons[i] = new Neuron(nbInputs);
         }
         outputNeurons = new Neuron[nbOutputs];
         for(int i = 0; i < nbOutputs; i++)
         {
             outputNeurons[i] = new Neuron(nbHidden);
         }

     }
     /**
      * 
      * Evaluation destinée à mettre à jour toutes les valeurs de sortie des neurones du réseau
      * 
      * @param point Exemple à évaluer
      * @return Tableau des valeurs obtenues pour les neurones de sortie
      */
     public double[] evaluate(DataPoint point)
     {
         for(Neuron n : hiddenNeurons)
         {
             n.clear();
         }
         for(Neuron n : outputNeurons)
         {
             n.clear();
         }
         double[] hiddenOutputs = new double[nbHidden];
         for(int i = 0; i < nbHidden; i++)
         {
             hiddenOutputs[i] = hiddenNeurons[i].evaluate(point);
         }

         double[] outputs = new double[nbOutputs];
         for(int outputNb = 0; outputNb < nbOutputs; outputNb++)
         {
             outputs[outputNb] = outputNeurons[outputNb].evaluate(hiddenOutputs);
         }
         return outputs;
     }
     /**
      * Ajuste les poids des neurones pour améliorer la précision et diminuer les erreurs selon le taux d'apprentissage et
      * la vraie valeur devant vérifier la propriété.
      * 
      * @param point Exemple vrai du réseau
      * @param learningRate Taux d'apprentissage du réseau (ex : 0.3 : lent mais précis ; 5.0 : rapide mais peut être imprécis)
      * 
      */
     public void adjustWeight(DataPoint point, double learningRate)
     {
         double[] outputDeltas = new double[nbOutputs];
         for(int i = 0; i < nbOutputs; i++)
         {
             double output = outputNeurons[i].getOutput();
             double expectedOutput = point.getOutputs()[i];
             outputDeltas[i] = output * (1 - output) * (expectedOutput - output);
         }

         double[] hiddenDeltas = new double[nbHidden];
         for(int i = 0; i <nbHidden; i++)
         {
             double hiddenOutput = hiddenNeurons[i].getOutput();
             double sum = 0.0;
             for(int j = 0; j < nbOutputs; j++)
             {
                 sum += outputDeltas[j] * outputNeurons[j].weight(i);
             }
             hiddenDeltas[i] = hiddenOutput * (1 - hiddenOutput) * sum;
         }
         double value;

         for(int i = 0; i < nbOutputs; i++)
         {
             Neuron outputNeuron = outputNeurons[i];
             for(int j = 0; j < nbHidden; j++)
             {
                 value = outputNeuron.weight(j) + learningRate * outputDeltas[i] * hiddenNeurons[j].getOutput();
                 outputNeuron.adjustWeight(j, value);
             }

             value = outputNeuron.weight(nbHidden) + learningRate * outputDeltas[i] * 1.0;
             outputNeuron.adjustWeight(nbHidden, value);

         }

         for(int i = 0; i < nbHidden; i++)
         {
             Neuron hiddenNeuron = hiddenNeurons[i];
             for(int j = 0; j < nbInputs; j++)
             {
                 value = hiddenNeuron.weight(j) + learningRate * hiddenDeltas[i] * point.getInputs()[j];
                 hiddenNeuron.adjustWeight(j, value);
             }

             value = hiddenNeuron.weight(nbInputs) + learningRate * hiddenDeltas[i] * 1.0;
             hiddenNeuron.adjustWeight(nbInputs, value);
         }
     }
    /**
     *  
     * @return Tableau de tous les neurones du calque caché
     */
    public Neuron[] getHiddenNeurons() {
 		return hiddenNeurons;
 	}
    /**
     * 
     * @return Tableau de tous les neurones de sortie
     */
 	public Neuron[] getOutputNeurons() {
 		return outputNeurons;
 	}
 	/**
 	 * 
 	 * @return Nombre de neurones d'entrée dans le réseau
 	 */
 	public int getNbInputs() {
 		return nbInputs;
 	}
 	/**
 	 * 
 	 * @return Nombre de neurones pour chaque calques cachés dans le réseau
 	 */
 	public int getNbHidden() {
 		return nbHidden;
 	}
 	/**
 	 * 
 	 * @return Nombre de neurones de sortie dans le réseau
 	 */
 	public int getNbOutputs() {
 		return nbOutputs;
 	}
}
