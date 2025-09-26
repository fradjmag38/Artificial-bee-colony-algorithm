package projmod;

import java.lang.Math;

public  class BeeColony {

	/* Paramètres de contrôle de l'algorithme ABC */
	int NP=20; /* Nombre de membres de la colonie */
	int FoodNumber = NP/2; /* Le nombre de sources de nourriture est égal à la moitié de la taille de la colonie */
	int limit = 100;  /* nombre de fois que la solution ne s'améliore pas */
	int maxCycle = 2500; /* Le nombre de cycles de recherche de nourriture */

	/* Variables spécifiques au problème */
	int D = 100; /* Le nombre de paramètres du problème à optimiser */
	double lb = -5.12; /* Limite inférieure des paramètres */
	double ub = 5.12; /* Limite supérieure des paramètres. lb et ub peuvent être définis comme des tableaux pour les problèmes dont les paramètres ont des limites différentes */

	int runtime = 30; /* L'algorithme peut être exécuté plusieurs fois pour évaluer sa robustesse */

	int dizi1[] = new int[10];
	double Foods[][] = new double[FoodNumber][D]; /* contient la population de la nouriture */
	double f[] = new double[FoodNumber]; /* f est un vecteur contenant les valeurs de la fonction objective associées aux sources de nourriture */
	double fitness[] = new double[FoodNumber]; /* fitness est un vecteur contenant les valeurs de fitness (qualité) associées aux sources de nourriture */
	double trial[] = new double[FoodNumber]; /* trial est un vecteur contenant les nombres d'essais pour lesquels les solutions ne peuvent pas être améliorées */
	double prob[] = new double[FoodNumber]; /* prob est un vecteur contenant les probabilités des sources de nourriture (solutions) à choisir */
	double solution[]=new double[D];           /* Nouvelle solution (voisin) */
	
                   
	double ObjValSol; /* Valeur de la fonction objective de la nouvelle solution */
	double FitnessSol; /* Valeur de fitness de la nouvelle solution */
	int neighbour, param2change; /* param2change correspond  a l'indice de parametre a changé, neighbour correspond au voisin*/

	double GlobalMin; /* Solution optimale obtenue par l'algorithme ABC */
	double GlobalParams[] = new double[D]; /* Paramètres de la solution optimale */
	double GlobalMins[] = new double[runtime]; /* GlobalMins contient le GlobalMin de chaque exécution sur plusieurs exécutions */
	double r; /* Un nombre aléatoire dans la plage [0,1) */
	
	//fonction  fitness
	double CalculateFitness(double fun) {
		 double result=0;
		 if(fun>=0){
			 result=1/(fun+1);
		 }
		 else{ 
			 result=1+Math.abs(fun);
		 }
		 return result;
	 }

	//memoriser les solutions
	void MemorizeBestSource() { 
		for( int i=0;i<FoodNumber;i++){
		if (f[i]<GlobalMin){
	           GlobalMin=f[i];
	           for(int j=0;j<D;j++)
	           GlobalParams[j]=Foods[i][j];
	        }
		}
	 }
	void init(int index){
	   int j;
	   for (j=0;j<D;j++){
	        r = (   (double)Math.random()*32767 / ((double)32767+(double)(1)) );
	        Foods[index][j]=r*(ub-lb)+lb;
			solution[j]=Foods[index][j];
			}
		f[index]=calculateFunction(solution);
		fitness[index]=CalculateFitness(f[index]);
		trial[index]=0;
	}
	void initial(){
		int i;
		for(i=0;i<FoodNumber;i++){
		init(i);
		}
		GlobalMin=f[0];
	    for(i=0;i<D;i++)
	    GlobalParams[i]=Foods[0][i];


	}
	void SendEmployedBees(){
	    int i, j;
	    for (i = 0; i < FoodNumber; i++) {
	        r = ((double) Math.random() * 32767 / ((double)(32767)+(double)(1)));
	        param2change = (int)(r * D);

	        // Une solution choisie au hasard est utilisée pour produire une  nouvelle solution 
	        r = ((double) Math.random() * 32767 / ((double)(32767)+(double)(1)));
	        neighbour = (int)(r * FoodNumber);

	        for(j = 0; j < D; j++)
	            solution[j] = Foods[i][j];
	        r = ((double) Math.random() * 32767 / ((double)(32767)+(double)(1)));
	        solution[param2change] = Foods[i][param2change] + (Foods[i][param2change] - Foods[neighbour][param2change]) * (r - 0.5) * 2;

	        // Si la valeur du paramètre généré est hors des limites, elle est ramenée sur les limites 
	        if (solution[param2change] < lb)
	            solution[param2change] = lb;
	        if (solution[param2change] > ub)
	            solution[param2change] = ub;

	        ObjValSol = calculateFunction(solution);
	        FitnessSol = CalculateFitness(ObjValSol);

	        // Une sélection cupide est appliquée entre la solution courante i et sa solution mutante 
	        if (FitnessSol > fitness[i]) {
	            // Si la solution mutante est meilleure que la solution courante i, remplacez la solution par la mutante et réinitialisez le compteur d'essais de la solution i 
	            trial[i] = 0;
	            for (j = 0; j < D; j++)
	                Foods[i][j] = solution[j];
	            f[i] = ObjValSol;
	            fitness[i] = FitnessSol;
	        } else {
	            // Si la solution i ne peut pas être améliorée, augmentez son compteur d'essais 
	            trial[i] = trial[i] + 1;
	        }
	    }
	}

	void CalculateProbabilities(){
	     int i;
	     double maxfit;
	     maxfit=fitness[0];
	  for (i=1;i<FoodNumber;i++)
	        {
	           if (fitness[i]>maxfit)
	           maxfit=fitness[i];
	        }

	 for (i=0;i<FoodNumber;i++)
	        {
	         prob[i]=(0.9*(fitness[i]/maxfit))+0.1;
	        }

	}
	// Phase des abeilles observatrices 
	void SendOnlookerBees(){

	  int i,j,t;
	  i=0;
	  t=0;
	  while(t<FoodNumber)
	        {

	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        if(r<prob[i]) 
	        {        
	        t++;
	        
	        // Le paramètre à changer est déterminé de manière aléatoire 
	        r = ((double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        param2change=(int)(r*D);
	        
	        // Une solution choisie au hasard est utilisée pour produire une solution mutante de la solution i 
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);
	        
	        while(neighbour == i)
	        {
	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        neighbour=(int)(r*FoodNumber);
	        }
	        for(j=0;j<D;j++)
	        solution[j]=Foods[i][j];

	        r = (   (double)Math.random()*32767 / ((double)(32767)+(double)(1)) );
	        solution[param2change]=Foods[i][param2change]+(Foods[i][param2change]-Foods[neighbour][param2change])*(r-0.5)*2;

	        if (solution[param2change]<lb)
	           solution[param2change]=lb;
	        if (solution[param2change]>ub)
	           solution[param2change]=ub;
	        ObjValSol=calculateFunction(solution);
	        FitnessSol=CalculateFitness(ObjValSol);
	        
	        //selection glotone
	        if (FitnessSol>fitness[i])
	        {
	        trial[i]=0;
	        for(j=0;j<D;j++)
	        Foods[i][j]=solution[j];
	        f[i]=ObjValSol;
	        fitness[i]=FitnessSol;
	        }
	        else
	        {  
	            trial[i]=trial[i]+1;
	        }
	        } 
               
	        i++;
	        if (i==FoodNumber)
	        i=0;
	        }
	}

	void SendScoutBees()
	{
	int maxtrialindex,i;
	maxtrialindex=0;
	for (i=1;i<FoodNumber;i++)
	        {
	         if (trial[i]>trial[maxtrialindex])
	         maxtrialindex=i;
	        }
	if(trial[maxtrialindex]>=limit)
	{
		init(maxtrialindex);
	}
	}


double calculateFunction(double sol[])
{
return Rastrigin (sol);	
}
	 double Rastrigin(double sol[])
	 {
		 int j;
		 double top=0;

		 for(j=0;j<D;j++)
		 {
			 top=top+(Math.pow(sol[j],(double)2)-10*Math.cos(2*Math.PI*sol[j])+10);
		 }
		 return top;
	 }
        
}

