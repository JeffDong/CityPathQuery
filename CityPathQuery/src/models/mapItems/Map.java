package models.mapItems;

import java.util.ArrayList;

public class Map {
	private ArrayList<Location> locationList;
	private ArrayList<PathUnit> pathUnitList;
	
	public Map (ArrayList<Location> locationList, ArrayList<PathUnit> pathUnitList) {
		this.pathUnitList = pathUnitList;
		this.locationList = locationList;
	}
	
	public ArrayList<Location> getLocationList() {
		return locationList;
	}
	public void setLocationList(ArrayList<Location> locationList) {
		this.locationList = locationList;
	}
	public ArrayList<PathUnit> getPathUnitList() {
		return pathUnitList;
	}
	public void setPathUnitList(ArrayList<PathUnit> pathUnitList) {
		this.pathUnitList = pathUnitList;
	}
	
	
}
