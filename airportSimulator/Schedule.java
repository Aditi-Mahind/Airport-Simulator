import java.io.FileWriter;
import java.util.*;

class Schedule 
{
  private ScheduleRow[] aSchedule;
  static int jetCnt,privatePlaneCnt,jumboCnt;
  private FileIO timingWriter = null;


  Schedule(int numberOfPlanes) 
  {
    this.aSchedule = new ScheduleRow[numberOfPlanes];
    jetCnt=0;
    privatePlaneCnt=0;
    jumboCnt=0;
    try {
        this.timingWriter = new FileIO(new FileWriter("/home/kanchan/Desktop/aircraftTiming.csv"), new String[] {"timestamp", "aircraft", "landingtime","refuelingtime", "occupancytime","takeofftime"});
    }
    catch(Exception e) {
      this.timingWriter = null;
    }
  }

  private static int generateRandomTime()
	{
		Random random=new Random();
		return random.nextInt(1440);
	}

	private static Aircraft generateRandomAircraft()
	{
		Random random=new Random();
		Aircraft aircraft =new Aircraft();
		switch(1+ random.nextInt(3))
		{
			case 1:
				jetCnt++;
				return aircraft.createJet();
			case 2:
				 privatePlaneCnt++;
				return aircraft.createPrivatePlane();
			default:
				  jumboCnt++;;
				return aircraft.createJumboJet();
		}
	}

  ScheduleRow[] generateRandomSchedule()
  {
    for(int i = 0; i < this.aSchedule.length; i++) {
      int time = generateRandomTime();
      Aircraft aircraft = generateRandomAircraft();
      this.aSchedule[i] = new ScheduleRow(time, aircraft);
    }
    
    Arrays.sort(this.aSchedule);
    return this.aSchedule;
  }

  void printSchedule() {
    for(ScheduleRow row : this.aSchedule) {
      System.out.println(row);
      Aircraft aircraft=row.getAircraft();
      this.timingWriter.write(new String[]{row.getTime()+"", row.getAircraft()+"", aircraft.getLandingLatency()+"",aircraft.getRefuelingTime()+"",aircraft.getOccupancyTime()+"",aircraft.getTakeoffLatency()+""});
    }
    this.timingWriter.close();
  }
  
  int getNumOfJets() {return this.jetCnt;}
  int getNumOfPrivatePlane() {return this.privatePlaneCnt;}
  int getNumOfJumboJet() {return this.jumboCnt;}

}
