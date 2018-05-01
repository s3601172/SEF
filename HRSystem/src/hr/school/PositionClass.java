package hr.school;

import hr.staff.*;

public class PositionClass {
	private int startTime;
	private int endTime;
	private String location;
	private int[] repeats;
	private Position position;

	public PositionClass(Position position, String location, int startTime, int endTime, int[] repeats) {
		this.position = position;
		this.location = location;
		this.startTime = startTime;
		this.endTime = endTime;
		this.repeats = repeats;
	}
	
	public void setStartTime(int inputTime) {
		startTime = inputTime;
	}

	public int getStartTime() {
		return startTime;
	}
	
	public void setEndTime(int inputTime) {
		endTime = inputTime;
	}

	public int getEndTime() {
		return endTime;
	}
	
	public void setLocation(String input) {
		location = input;
	}

	public String getLocation() {
		return location;
	}
	
	public void setRepeats(int[] days) {
		repeats = days;
	}

	public int[] getRepeats() {
		return repeats;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}
