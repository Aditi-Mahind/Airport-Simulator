import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;

public class Simulator {
	private IO consoleIO = new ConsoleIO();
	private int maxGate, minGate; 
	private FileIO flightWriter = null;

	Simulator()
	{
		try {
			FileWriter fileWriter=new FileWriter("/home/kanchan/Desktop/flightDistribution.csv");
	        this.flightWriter = new FileIO(fileWriter, new String[] {"day", "jet", "private","jumbo"});
	    }
	    catch(Exception e) {
	    	System.out.println(e);
	      this.flightWriter = null;
	    }
		this.minGate=9999;
		this.maxGate=0;
		
	}
	
	int simulationForYear(int numberOfPlanes)
	{
		int i=0;
		try (PrintWriter writer = new PrintWriter(new File("/home/kanchan/Desktop/1Year.csv"))) {

			StringBuilder builder = new StringBuilder();
			builder.append("Day");
			builder.append(',');
			builder.append("Gates");
			builder.append('\n');
						
			for (i=0;i<365;i++)
			{
				Schedule schedule = new Schedule(numberOfPlanes);
				ScheduleRow[] scheduleArray = schedule.generateRandomSchedule();
				schedule.printSchedule();
				System.out.println(i+" "+ schedule.getNumOfJets()+" "+ schedule.getNumOfPrivatePlane()+" "+schedule.getNumOfJumboJet()+"");
			    this.flightWriter.write(new String[]{i+"", schedule.getNumOfJets()+"", schedule.getNumOfPrivatePlane()+"",schedule.getNumOfJumboJet()+""});
			    
				Airport airport = new Airport(consoleIO);
				AirportSimulator airportSimulator = new AirportSimulator(airport, scheduleArray);
				airportSimulator.start();
				int gates=airportSimulator.getMaxGates(airport);
				if(gates<this.minGate)
				{
					this.minGate=gates;
					saveBestSchedule(airportSimulator);

				}
				if(gates>this.maxGate)
				{
					this.maxGate=gates;
					saveWorstSchedule(airportSimulator);
				}

				builder.append(i);
				builder.append(',');
				builder.append(gates);
				builder.append('\n');

				writer.write(builder.toString());
				builder=new StringBuilder();
			} 
			this.flightWriter.close();
		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}
		return this.maxGate;
	}
	
	
	void simulationForNYears(int numOfYears,int numberOfPlanes)
	{
		
		try (PrintWriter writer = new PrintWriter(new File("/home/kanchan/Desktop/NYears.csv"))) {

			StringBuilder builder = new StringBuilder();
			builder.append("Year");
			builder.append(',');
			builder.append("Gates");
			builder.append('\n');
			
			for(int i=0;i<numOfYears;i++)
			{
				int gates=simulationForYear(numberOfPlanes);
				builder.append(i);
				builder.append(',');
				builder.append(gates);
				builder.append('\n');

				writer.write(builder.toString());
				builder=new StringBuilder();
			}

		}
		catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}
	
	void  simulationForDay(int numberOfPlanes)
	{
		    Schedule schedule = new Schedule(numberOfPlanes);
		    ScheduleRow[] scheduleArray = schedule.generateRandomSchedule();
		    schedule.printSchedule();

		    Airport airport = new Airport(consoleIO);
		    AirportSimulator airportSimulator = new AirportSimulator(airport, scheduleArray);
		    airportSimulator.start();
	}
	
	private void saveWorstSchedule(AirportSimulator airportSimulator)
	{
		StringBuilder sbuilder=new StringBuilder();
		
		try (PrintWriter filewriter = new PrintWriter(new File("/home/kanchan/Desktop/WorstSchedule.csv"))) {
			sbuilder.append("Time");
			sbuilder.append(',');
			sbuilder.append("Aircraft");
			sbuilder.append('\n');
			ScheduleRow[] input=airportSimulator.getSchedule();
			for(int i=0;i<input.length;i++)
			{
				sbuilder.append(convertMinstoHours(input[i].getTime()));
				sbuilder.append(',');
				sbuilder.append(input[i].getAircraft());
				sbuilder.append('\n');
			}
			filewriter.write(sbuilder.toString());
			
		}catch(Exception e) {}
	}
	
	private void saveBestSchedule(AirportSimulator airportSimulator)
	{
		StringBuilder sbuilder=new StringBuilder();
		
		try (PrintWriter filewriter = new PrintWriter(new File("/home/kanchan/Desktop/BestSchedule.csv"))) {
			sbuilder.append("Time");
			sbuilder.append(',');
			sbuilder.append("Aircraft");
			sbuilder.append('\n');
			ScheduleRow[] input=airportSimulator.getSchedule();
			for(int i=0;i<input.length;i++)
			{
				sbuilder.append(convertMinstoHours(input[i].getTime()));
				sbuilder.append(',');
				sbuilder.append(input[i].getAircraft());
				sbuilder.append('\n');
			}
			filewriter.write(sbuilder.toString());
			
		}catch(Exception e) {}
	}
	
	private String convertMinstoHours(int minutes)
	{
		int hours = minutes / 60; 
		int min = minutes % 60;
		return hours+":"+min ;
	}

	String getRangeOfGates()
	{
		return Integer.toString(this.minGate)+"-"+Integer.toString(this.maxGate);
	}
	
	
	
}
