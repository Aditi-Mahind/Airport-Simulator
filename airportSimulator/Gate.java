class Gate {
  int time;
  Aircraft aircraft;

  Gate() {
    int time = 0;
  }

  void occupyGate(int time, Aircraft aircraft) {
    this.aircraft = aircraft;
    this.time = time + aircraft.getOccupancyTime()+aircraft.getRefuelingTime();
  }

  boolean isGateFree(int currentTime) {
    return this.time <= currentTime;
  }

  private void resetGate() {
    this.time = 0;
    this.aircraft = null;
  }

  Aircraft freeGate() {
    Aircraft aircraft = this.aircraft;
    this.resetGate();
    return aircraft;
  }

  public String toString()
  {
    if(aircraft == null) {
      return "Empty";
    }
    return this.time + " " + this.aircraft.toString();
  }
}
