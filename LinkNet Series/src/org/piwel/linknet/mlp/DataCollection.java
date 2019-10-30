package org.piwel.linknet.mlp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.piwel.linknet.parser.header.HeaderHandler;

/**
 * 
 * @author Atomix
 * 
 * Cette classe encapsule certains (ou tous les) exemples DataPoint depuis le taux d'entrainement
 * Ces valeurs seront ainsi rangées dans un ordre aléatoire
 * 
 *
 */
public class DataCollection {

	private DataPoint[] trainingPoints;
    private DataPoint[] generalisationPoints;

    /**
     * 
     * @return Tableau des exemples d'entrainement
     */
    public DataPoint[] points()
    {
        return trainingPoints;
    }
    /**
     * @deprecated
     * @return Tableau des exemples de généralisation (inutilisé depuis LN-2)
     */
    @Deprecated
    public DataPoint[] generalisationPoints()
    {
        return generalisationPoints;
    }

    /**
     * Constructeur de la classe. Il permet de sélectionner un taux d'exemples et de les répartir aléatoirement dans trainingPoint
     * 
     * @param content Contenu du fichier (header homis)
     * @param outputNb Nombre de valeurs de sortie (masque de séparation I/O)
     * @param trainingRatio Taux d'exemple à tester par génération (ex : 0.2 : 1/5 des exemples de contenu; 1 : tous les exemples de contenu)
     */
    
   	public DataCollection(String[] content, int outputNb, double trainingRatio, HeaderHandler hh)
    {
        int nbLines = content.length;
        List<DataPoint> points = new ArrayList<DataPoint>();
        for(int i = 0; i < nbLines; i++)
        {
        	DataPoint dp = new DataPoint(content[i], outputNb);
            points.add(dp);
            
        }
        int nbTrainingPoints = (int)(trainingRatio * nbLines);
        trainingPoints = new DataPoint[nbTrainingPoints];
        Random rand = new Random();
        for(int i = 0; i < nbTrainingPoints; i++)
        {
            int index = rand.nextInt(points.size());
            trainingPoints[i] = points.get(index);
            points.remove(index);
        }

        generalisationPoints = points.toArray(new DataPoint[0]);
    }
}
