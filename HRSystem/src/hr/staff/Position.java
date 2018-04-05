package hr.staff;

public class Position {
	double pWage;
	boolean pFilledStatus;
	String pType;
	
	public Position(double wage, boolean filledStatus, String type) {
		pWage = wage;
		pFilledStatus = filledStatus;
		pType = type;
	}
	
	public double getWage() {
		return this.pWage;
	}
	
	public void setWage(double wage) {
		wage = this.pWage;
	}
	
	public boolean isFilled() {
		return this.pFilledStatus;
	}
	
	public void setFilled(boolean filledStatus) {
		filledStatus = this.pFilledStatus;
	}

	public String getType() {
		return this.pType;
	}
	
	public void setType(String type) {
		type = pType;
	}
}
