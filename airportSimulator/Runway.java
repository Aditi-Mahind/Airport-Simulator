import java.util.*;

class Runway {
  private List<RunwayOperation> operationList;
  private static final int LANDING_TIME = 2;
  private static final int DEPARTURE_TIME = 2;

  Runway()
  {
    this.operationList = new LinkedList<>();
  }

  List<ScheduleRow> getLandedPlanes(int currentTime)
  {
    List<ScheduleRow> landedAircrafts = new LinkedList<>();

    while(this.operationList.size() > 0) {
      RunwayOperation operation = this.operationList.get(0);
      // System.out.println(operation);
      if(currentTime < operation.time) {
        return landedAircrafts;
      }

      if(operation.operation == "LAND" && currentTime >= operation.time + this.LANDING_TIME) {
        landedAircrafts.add(new ScheduleRow(operation.time, operation.aircraft));
        this.operationList.remove(operation);
      }
      else if(operation.operation == "TAKEOFF" && currentTime >= operation.time + this.DEPARTURE_TIME) {
        this.operationList.remove(operation);
      }
      else {
        break;
      }
    }
    return landedAircrafts;
  }

  void addForDeparture(int time, Aircraft aircraft) {
    this.operationList.add(new RunwayOperation(time, aircraft, "TAKEOFF"));
  }

  void addForLanding(int time, Aircraft aircraft) {
    this.operationList.add(new RunwayOperation(time, aircraft, "LAND"));
  }
}
