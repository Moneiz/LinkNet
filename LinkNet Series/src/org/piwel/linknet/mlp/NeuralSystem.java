package org.piwel.linknet.mlp;

import java.text.DecimalFormat;

import org.piwel.linknet.graphic.Window;
import org.piwel.linknet.parser.header.HeaderHandler;
import org.piwel.linknet.parser.header.HeaderEvaluationException;

public class NeuralSystem implements Runnable {

	DataCollection data;
	DataPoint currentDataPoint;

	NeuralNetwork network;
	String[] header;
	HeaderHandler headerHandler;
	IHM ihm;

	public boolean isRunning = true;
	public int i;

	Window win;

	int nbOutputs;

	double learningRate = 1f;
	double maxError = 0.005;
	int maxIterations = 10001;
	double errorRate = Double.POSITIVE_INFINITY;

	/**
	 * 
	 * @return L'exemple en train d'être testé par le programme
	 */
	public DataPoint getCurrentDataPoint() {
		return currentDataPoint;
	}
	/**
	 * 
	 * @return Le taux d'erreur actuel
	 */
	public double getErrorRate() {
		return errorRate;
	}
	
	/**
	 * 
	 * @return Le réseau de neurone
	 */
	
	public NeuralNetwork getNeuralNetwork() {
		return network;
	}

	/**
	 * 
	 * @return L'ensemble des exemples
	 */
	
	public DataCollection getDataCollection() {
		return data;
	}
	/**
	 *
	 * 
	 * @param nbInputs Nombre de neurones d'entrée
	 * @param nbHidden Nombre de neurones dans le calque caché
	 * @param nbOutputs Nombre de neurones de sortie
	 * @param data Les lignes de l'exemple à utiliser
	 * @param trainingRatio Le ratio d'entrainement 
	 * @param ihm IHM à utiliser
	 */
	public NeuralSystem(int nbInputs, int[] nbHidden, int nbOutputs, DataCollection datapoints,  double trainingRatio)
	{
        IHM.info("Making Neural System...");
		
        long time = System.currentTimeMillis();
        
		this.nbOutputs = nbOutputs;
		
		network = new NeuralNetwork(nbInputs, nbHidden, nbOutputs);

		this.data = datapoints;
		
        IHM.info("Neural System done in "+ (int) (System.currentTimeMillis() - time) +" ms !");
	}

	/**
	 * Change le taux d'apprentissage du système
	 * 
	 * @param rate Le nouveau taux d'apprentissage
	 */
	
	public void learningRate(double rate)
	{
		learningRate = rate;
	}
	
	/**
	 * 
	 * Change le taux d'erreur maximal où le programme s'arrêtera par défaut
	 * 
	 * @param error Nouveau taux d'erreur maximal
	 */
	
	public void maximumError(double error)
	{
		maxError = error;
	}
	
	/**
	 * 
	 * Change le nombre d'itération maximal où le programme pourra s'arrêter 
	 *
	 * @param iterations Nouveau nombre d'itération maximal
	 */
	
	public void maximumIterations(int iterations)
	{
		maxIterations = iterations;
	}

	/**
	 * 
	 * Ajoute au système un header pour indiquer les paramètres et le nom des neurones d'entrée/sortie
	 * 
	 * @param head Ligne du header
	 */
	
	public void addHeaderFile(String[] head)
	{
		header = head;
		try {
			headerHandler = new HeaderHandler(head);
		} catch (HeaderEvaluationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * Lie une interface Window au système neural (facultatif)
	 * 
	 * @param win Interface Window
	 */
	
	public void addWindow(Window win) {
		this.win = win;
	}
	
	/**
	 * 
	 * Change toutes les secondes l'exemple à tester. Tout le système neural sera actualisé mais l'apprentissage 
	 * est empêché pour éviter la généralisation.
	 * 
	 */
	
	public void testInput() {
		try {
			while(true) {
				for (DataPoint point : data.points())
				{
					
					if(win != null && !win.autoMode) {
						return;
					}
					currentDataPoint = point;
					if(win != null) {
						win.reDraw();
					}
					
					Thread.sleep(1000);
					
				}
			}
		}catch(InterruptedException ex) {}
	}
	
	/**
	 * 
	 * Actualise tout le sytème neural selon l'exemple donné en paramètre
	 * 
	 * @param value Ligne contenant les entrées (et sorties) de l'exemple
	 */
	
	public void testInput(double[] value) {


		DataPoint point = new DataPoint(value, this.nbOutputs);


		double[] outputs = network.evaluate(point);
		currentDataPoint = point;
		
		String msg = "";
		for(int i = 0; i < point.getInputs().length; i++)
		{
			msg += "Input " + (i+1) + "="+  point.getInputs()[i] + "\t"; //Deprecated
		}
		
		msg += "\n";
		for(int i = 0; i < outputs.length; i++)
		{
			msg += "Output " + (i+1) + "="+ outputs[i] + "\t"; //Deprecated
		}
		
			
		//msg += "Output " + header[header.length - nbOutputs/8 + outNb] + "=" + heaviside(error); //Deprecated



		IHM.info(msg);
		


	}
	/**
	 * 
	 * Permet d'obtenir les valeurs de sortie pour chaque exemple donné selon le système neural formé
	 * 
	 * @param isApplyHeavyside Est-ce que la valeur de sortie est arrondie selon heavyside
	 */
	@Deprecated
	public void test(boolean isApplyHeavyside)
	{

		for (DataPoint point : data.points())
		{
			double[] outputs = network.evaluate(point);
			for (int outNb = 0; outNb < outputs.length; outNb++)
			{
				double error = outputs[outNb];
				String msg = "";
				for(int i = 0; i < point.getInputs().length; i++)
				{
					msg += "Input " + (i+1) + "="+  point.getInputs()[i] + "\t";
				}
				if (isApplyHeavyside)
				{
					msg += "Output " + header[header.length - nbOutputs + outNb] + "=" + heaviside(error);
				}
				else
				{
					msg += "Output " + header[header.length - nbOutputs + outNb] + "=" + error;
				}
				IHM.info(msg);
			}

		}
	}
	/**
	 * 
	 * Arrondie la valeur en paramètre
	 * 
	 * @param value Valeur à tester 
	 * @return Valeur entière retourné
	 */
	@Deprecated
	private int heaviside(double value)
	{
		if(value >= 0.5)
		{
			return 1;
		}
		else
		{
			return 0;
		}
	}
	/**
	 * 
	 * Permet au système neural d'apprendre selon les exemples fournis. Les poids des neurones seront ainsi adapter pour
	 * diminuer les erreurs au minimum
	 * 
	 */
	@SuppressWarnings("deprecation")
	public void run()
	{
		try {
			i = 0;
			double totalError = Double.POSITIVE_INFINITY;
			double oldError = Double.POSITIVE_INFINITY;
			double totalGeneralisationError = Double.POSITIVE_INFINITY;
			double oldGeneratlisationError = Double.POSITIVE_INFINITY;
			boolean betterGeneralisation = true;

			while(i < this.maxIterations && totalError > maxError && betterGeneralisation)
			{
				long time = System.nanoTime();
				oldError = totalError;
				totalError = 0;
				oldGeneratlisationError = totalGeneralisationError;
				totalGeneralisationError = 0;

				currentDataPoint = data.points()[data.points().length-1];

				for(DataPoint point : data.points())
				{
					
					double[] outputs = network.evaluate(point);
					for(int outNb = 0; outNb < outputs.length; outNb++)
					{
						double error = point.getOutputs()[outNb] - outputs[outNb];
						totalError += (error * error);
					}
					network.adjustWeight(point, learningRate);
				}

				for(DataPoint point : data.generalisationPoints())
				{
					double[] outputs = network.evaluate(point);
					for(int outNb = 0; outNb < outputs.length; outNb++)
					{
						double error = point.getOutputs()[outNb] - outputs[outNb];
						totalGeneralisationError += (error * error);
					}
				}
				if(totalGeneralisationError > oldGeneratlisationError)
				{
					betterGeneralisation = false;
				}

				if(totalError >= oldError)
				{
					learningRate = learningRate / 2;
				}
				errorRate = Math.sqrt(totalError / data.points().length) * 100;
				IHM.info("Iteration n°" + i + " - In "+(int)(System.nanoTime()-time)+" ms - Rate " + learningRate + " - Mean : " + new DecimalFormat("#.##").format(errorRate)  + " %");
				i++;

				if(i % 10 == 0) {
					if(win != null)
						win.reDraw();
				}
			}
			Thread.sleep(10);
			
			isRunning = false;
			
			IHM.info("Test image 1 " + network.getOutputNeurons()[0].getOutput() + " - " + network.getOutputNeurons()[1].getOutput());

		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}

