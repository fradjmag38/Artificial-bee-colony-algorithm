package projmod;
import javafx.application.Application;
import java.util.*;
public class ProjetModule {

    static BeeColony bee=new BeeColony();
	
	public static void main(String[] args) throws Exception {
		int iter=0;
		int run=0;
		int j=0;
		double mean=0;
		List<Double> globalMinsList = new ArrayList<>();
		//srand(time(NULL));
		for(run=0;run<bee.runtime;run++)
		{
		bee.initial();
		bee.MemorizeBestSource();
		for (iter=0;iter<bee.maxCycle;iter++)
		    {
			bee.SendEmployedBees();
			bee.CalculateProbabilities();
			bee.SendOnlookerBees();
			bee.MemorizeBestSource();
			bee.SendScoutBees();
		    }
		for(j=0;j<bee.D;j++)
		{
			System.out.println("GlobalParam["+(j+1)+"]:"+bee.GlobalParams[j]);
		}
		System.out.println((run+1)+".run:"+bee.GlobalMin);
		bee.GlobalMins[run]=bee.GlobalMin;
		globalMinsList.add(bee.GlobalMin);
		mean=mean+bee.GlobalMin;
		}
		mean=mean/bee.runtime;
		
		
		System.out.println("Means  of "+bee.runtime+"runs: "+mean);
        System.out.println("La valeur du minimum globale  est : " + Collections.min(globalMinsList));
		Plot2D.setGlobalMins(globalMinsList);
		Application.launch(Plot2D.class);
	}
}

