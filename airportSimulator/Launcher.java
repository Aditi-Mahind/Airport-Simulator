

public class Launcher {
	
	
	
	public static void main(String[] args)
	{
		int numberOfPlanes = 500;
		int numberOfYears=1;
		Simulator simulator=new Simulator();
		simulator.simulationForDay(numberOfPlanes);
		
		simulator.simulationForNYears(numberOfYears,numberOfPlanes);
		System.out.println(simulator.getRangeOfGates());
	}

}
