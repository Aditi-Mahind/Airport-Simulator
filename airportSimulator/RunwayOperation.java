class RunwayOperation {
  int time;
  Aircraft aircraft;
  String operation;

  RunwayOperation(int time, Aircraft aircraft, String operation) {
    this.time = time;
    this.aircraft = aircraft;
    this.operation = operation;
  }

  public String toString() {
    return this.time + " " + this.aircraft.toString() + " " + this.operation;
  }
};
