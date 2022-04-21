import java.util.*;

import java.io.*;

class Airport {
  private int currentTime;
  private List<ScheduleRow> waitingQueue;
  private List<Gate> gates;
  private Runway runway;
  private final IO consoleIO;
  private FileIO gateWriter = null;
  private FileIO perHourGateWriter = null;
  private int hour = 0;
  private int gateCountAtHour = 0;

  Airport(IO consoleIO) 
  {
    this.consoleIO = consoleIO;
    try {
        this.gateWriter = new FileIO(new FileWriter("/home/kanchan/Desktop/gateCount.csv"), new String[] {"timestamp", "usedGates", "unusedGates"});
        this.perHourGateWriter = new FileIO(new FileWriter("/home/kanchan/Desktop/gateCountPerHour.csv"), new String[] {"hour", "usedGates"});
    }
    catch(Exception e) {
      this.gateWriter = null;
    }
    this.runway = new Runway();
    this.waitingQueue = new LinkedList<>();
    this.gates = new ArrayList<>(1);
    this.gates.add(new Gate());
    this.currentTime = 0;
  }

  void addGate() {
    this.gates.add(new Gate());
  }

  void arriveAircraft(Aircraft aircraft, int currentTime)
  {
    this.currentTime = currentTime;
    this.handleAirportOperations();

    this.runway.addForLanding(currentTime, aircraft);
    consoleIO.write("\n******************************\n");
    consoleIO.write("\nWaiting queue");
    this.printQueue(this.waitingQueue);
    consoleIO.write("\nGate");
    this.printGates();
  }

  private void handleAirportOperations() 
  {
    this.freeGate();
    this.parkAtGates();
    this.landPlanes();
  }

  private void landPlanes()
  {
    this.waitingQueue.addAll(this.runway.getLandedPlanes(this.currentTime));
  }

  private void parkAtGates() 
  {
    this.freeGate();
    if(this.gates.size() == 0 || this.waitingQueue.size() == 0) {
      return;
    }

    int time = this.currentTime;

    while(this.waitingQueue.size() > 0) {
      ScheduleRow scheduleRow = this.waitingQueue.get(0);
      if(time < scheduleRow.getTime()) {
        return;
      }

      int waitingAircraftTime = scheduleRow.getTime();
      Aircraft aircraft = scheduleRow.getAircraft();
      int landingLatency = aircraft.getLandingLatency();

      int freeGateNumber = -1;
      for(int i = 0; i < this.gates.size(); i++) {
        Gate gate = this.gates.get(i);
        if(gate.isGateFree(waitingAircraftTime + landingLatency)) {
          freeGateNumber = i;
          break;
        }
      }
      if(freeGateNumber == -1) {
        this.addGate();
        freeGateNumber = this.gates.size() - 1;
      }
      this.gates.get(freeGateNumber).occupyGate(waitingAircraftTime + landingLatency, aircraft);
      this.waitingQueue.remove(scheduleRow);
    }
  }

  void freeGate() 
  {
    for(Gate gate : gates) {
      if(gate.isGateFree(this.currentTime)) {
        Aircraft aircraft = gate.freeGate();
        if(aircraft != null) {
          this.runway.addForDeparture(this.currentTime, aircraft);
        }
      }
    }
  }
  
  int maxGates()
  {
	  return gates.size();
  }

  void printGates()
  {
    int occupiedGates = 0;
    if(this.hour != this.currentTime / 60) {
        this.perHourGateWriter.write(new String[]{this.hour+"", this.gateCountAtHour+""});
        this.hour += 1;
    }

   consoleIO.write("CurrentTime: " + this.currentTime + "\n ");
    for(int i = 0; i < this.gates.size(); i++) {
      Gate gate = this.gates.get(i);
      if(!gate.isGateFree(this.currentTime)) {
        occupiedGates++;
      }
      consoleIO.write("Gate " + i + ": " + gate);
    }
    this.gateWriter.write(new String[]{this.currentTime+"", occupiedGates+"", (this.gates.size() - occupiedGates)+""});
    this.gateCountAtHour = occupiedGates;
  }

  void printQueue(List<ScheduleRow> listToPrint)
  {
    consoleIO.write("CurrentTime: " + this.currentTime + "\n ");
    if(listToPrint.size() == 0) {
      consoleIO.write("Queue empty");
      return;
    }
    for(ScheduleRow row: listToPrint) {
      if(row.getAircraft() != null) {
          consoleIO.write(row);
      }
    }
  }
  
   
  void closeAllConnections() 
  {
    this.gateWriter.close();
    this.perHourGateWriter.close();
  }
  
  }
