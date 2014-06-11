package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import models.mapItems.Coordinate;
import models.mapItems.Location;
import models.mapItems.Map;
import models.mapItems.PathUnit;

public class TranslateMapFile {
	
	
	public static ArrayList<Location> translateLocation(String filename) {
		File inFile = new File(filename);
		Scanner input = null;
		try {
			input = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("file not exists");
			e.printStackTrace();
		}				
		
		ArrayList<Location> locationList = new ArrayList<Location>();
		
		while (input.hasNextLine()) {
			String newLine = input.nextLine();
			
			Scanner newLineScanner = new Scanner(newLine);
			
			int x = newLineScanner.nextInt();
			int y = newLineScanner.nextInt();
			String name = newLineScanner.next();
			
			Location newLocation = new Location(new Coordinate(x,y), name);
			locationList.add(newLocation);
			
			newLineScanner.close();
		}
		
		input.close();
		
		return locationList;
	}
	
	public static ArrayList<PathUnit> translatePathUnit(String filename) {
		File inFile = new File(filename);
		Scanner input = null;
		try {
			input = new Scanner(inFile);
		} catch (FileNotFoundException e) {
			System.out.println("file not exists");
			e.printStackTrace();
		}				
		
		ArrayList<PathUnit> pathUnitList = new ArrayList<PathUnit>();
		
		while (input.hasNextLine()) {
			String newLine = input.nextLine();
			
			Scanner newLineScanner = new Scanner(newLine);
			
			int x1 = newLineScanner.nextInt();
			int y1 = newLineScanner.nextInt();
			int x2 = newLineScanner.nextInt();
			int y2 = newLineScanner.nextInt();
			
			PathUnit newPathUnit = new PathUnit(new Coordinate(x1, y1), new Coordinate(x2, y2));
			pathUnitList.add(newPathUnit);
			
			newLineScanner.close();
		}
		
		input.close();
		
		return pathUnitList;
	}

	public static Map translateMap() {
		ArrayList<PathUnit> pathUnitList = translatePathUnit("map_path_unit");
		ArrayList<Location> locationList = translateLocation("map_location");
		
		Map map = new Map(locationList, pathUnitList);
		
		return map;
	}
	
	public static void printMap(Map map) {
		System.out.println("Path Units are :");
		for (PathUnit pathUnit : map.getPathUnitList()) {
			Coordinate start = pathUnit.getStartPoint();
			Coordinate end = pathUnit.getEndPoint();
			System.out.println(start.getX() + "," + start.getY() + "," + end.getX() + "," + end.getY());
		}
		System.out.println("\nLocations are :");
		for (Location location : map.getLocationList()) {
			Coordinate p = location.getCoordinate();
			String name = location.getLocationName();
			System.out.println(p.getX() + "  " + p.getY() + "  " + name);
		}
	}
	
	public static void main(String[] args) {		
		Map map = translateMap();
		printMap(map);
		
	}
	
}
