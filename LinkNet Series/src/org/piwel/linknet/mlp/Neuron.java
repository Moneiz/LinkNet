package org.piwel.linknet.mlp;

import java.util.Random;

/**
 * 
 * Cette classe est une modélisation d'un neurone. Il est à la base du réseau neural et du fonctionnement de ce Deep Learning
 * 
 * @author Atomix
 *
 */
public class Neuron {
	 private double[] weights;
     private int nbInputs;

     private double output;

     /**
      * 
      * @return Valeur de la sortie du neurone
      */
     public double getOutput() {
    	 return output;
     }
     /**
      * 
      * @param index Index du poids de la liaison concernée
      * @return Poids de la liaison concernée
      */
     public double weight(int index)
     {
         return weights[index];
     }
     /**
      * 
      * @param index Index du poids de la liaison à changer
      * @param value Valeur à attribuer au poids de la liaison
      */
     public void adjustWeight(int index, double value)
     {
         weights[index] = value;
     }
     /**
      * Constructeur de la classe. Il attribut des poids aléatoires aux liaisons
      * @param nbInputs Nombre de neurone d'entrée dans le réseau
      */
     public Neuron(int nbInputs)
     {
         this.nbInputs = nbInputs;
         output = Double.NaN;

         Random generator = new Random();

         weights = new double[(this.nbInputs + 1)];
         for(int i = 0; i < (this.nbInputs + 1); i++)
         {
             weights[i] = generator.nextDouble() * 2.0 - 1.0;
         }
     }
     /**
      * Calcule la somme pondérée des poids avec les sorties des neurones précédents pour renvoyer la valeur
      * en appliquant une fonction sigmoide
      * 
      * @param inputs Les valeurs de sortie des neurones du calque précédent
      * @return La valeur de sortie du neurone
      */
     public double evaluate(double[] inputs)
     {
         if (Double.isNaN(output))
         {
             double x = 0.0;

             for(int i = 0; i < nbInputs; i++)
             {
                 x += inputs[i] * weights[i];
             }
             x += weights[nbInputs];

             output = 1.0 / (1.0 + Math.exp(-1.0 * x));
            
         }
         return output;
     }
     /**
      * 
      * Transforme d'abord point en valeur d'entrée et applique la fonction <i>evaluate(double[] inputs)</i>
      * 
      * @param point Exemple à évaluer
      * @return La valeur de sortie du neurone
      */
     double evaluate(DataPoint point)
     {
         double[] inputs = point.getInputs();
         return evaluate(inputs);
     }
     /**
      * Nettoie la sortie du neurone
      */
     void clear()
     {
         output = Double.NaN;
     }
}
