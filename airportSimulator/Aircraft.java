import java.util.Random;

class Aircraft {
  private String name;
  private int landingLatency;
  private int takeoffLatency;
  private int refuelingTime;
  private int gateOccupancyTime;
  private int fuelCapacity;
 
  
  Aircraft() 
  {
	  this.name = "";
	  this.landingLatency = 0;
	  this.takeoffLatency = 0;
	  this.refuelingTime =0;
	  this.gateOccupancyTime = 0;
  }
  
  private Aircraft(String name, int landingLatency, int takeoffLatency, int refuleingTime, int gateOccupancyTime)
  {
    this.name = name;
    this.landingLatency = landingLatency;
    this.takeoffLatency = takeoffLatency;
    this.refuelingTime = refuleingTime;
    this.gateOccupancyTime = gateOccupancyTime;
   
  }

   Aircraft createJet() {
	   
	this.refuelingTime=checkRefuelingTime();
    return new Aircraft("Jet", 10, 12, this.refuelingTime, 30);
  }

   Aircraft createPrivatePlane() {
	 this.refuelingTime=checkRefuelingTime();
    return new Aircraft("Private", 10, 20, this.refuelingTime, 50);
  }

   Aircraft createJumboJet() {
	
	this.refuelingTime=checkRefuelingTime();
	return new Aircraft("Jumbo Jet", 10, 18, this.refuelingTime, 45);
  }

   private int checkRefuelingTime()
   {
	   Random random=new Random();
	   this.fuelCapacity=20+random.nextInt(90);
		  if(this.fuelCapacity<=50)
			  this.refuelingTime=10;
		  else
			  this.refuelingTime=0;
		return this.refuelingTime; 
   }
   
  int getLandingLatency() {return this.landingLatency;}

  int getOccupancyTime() {return this.gateOccupancyTime;}

  int getTakeoffLatency() {return this.takeoffLatency;}
  
  int getRefuelingTime() {return this.refuelingTime;}
  
  int getFuelCapacity() {return this.fuelCapacity;}

  public String toString() {return this.name;}
  
  

  

}
