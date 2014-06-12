package models.mapItems;

public class Coordinate{
	double x;
	double y;
	
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean isEqual(Coordinate other) {
		return (this.x == other.getX() && this.y == other.getY());
	}
	
	public double getDistance(Coordinate other) {
		//distance = sqrt((x1-x2)^2 + (y1-y2)^2)
		return Math.sqrt((other.x - this.x)*(other.x - this.x) + (other.y - this.y)*(other.y - this.y));
	}
	
	//auto getter and setter
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	
}
