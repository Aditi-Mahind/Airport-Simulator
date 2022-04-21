class AirportSimulator 
{
  private Airport airport;
  private ScheduleRow[] schedule;

  AirportSimulator(Airport airport, ScheduleRow[] schedule) 
  {
    this.airport = airport;
    this.schedule = schedule;
  }
  
  ScheduleRow[] getSchedule()
  {
	  return schedule;
  }

  void start() 
  {
    for(ScheduleRow row: this.schedule) {
      Aircraft aircraft = row.getAircraft();
      int currentTime = row.getTime();
      this.airport.arriveAircraft(aircraft, currentTime);
    }
    this.airport.closeAllConnections();
  }
  
  int getMaxGates(Airport airport)
  {
	  int gates=airport.maxGates();
	  return gates;
  }
  
}
