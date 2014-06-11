package models.mapItems;


public class Location {
	private Coordinate coordinate;
	private String locationName;
	private int locationID;
	
	private static int counter = 0;
	
	public Location(Coordinate coordinate, String locationName) {
		this.locationName = locationName;
		this.coordinate = coordinate;
		this.locationID = counter;
		counter ++;
	}

	public Coordinate getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(Coordinate coordinate) {
		this.coordinate = coordinate;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public int getLocationID() {
		return locationID;
	}
	
	
}
