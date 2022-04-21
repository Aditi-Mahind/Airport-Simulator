class ScheduleRow implements Comparable<ScheduleRow> {
  private int time;
  private Aircraft aircraft;

  ScheduleRow(int time, Aircraft aircraft) {
    this.time = time;
    this.aircraft = aircraft;
  }

  int getTime() {
    return this.time;
  }

  Aircraft getAircraft() {
    return this.aircraft;
  }

  public String toString() {
    return this.time + " " + this.aircraft.toString();
  }

  public int compareTo(ScheduleRow row) {
    return this.time - row.time;
  }
}
