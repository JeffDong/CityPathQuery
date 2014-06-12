package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import models.mapItems.*;



// for my test
public class TestFrame extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public AbstractMap absMap;
	public MapPanel mapPanel;

	public TestFrame () {
		Map map = TranslateMapFile.translateMap();
		mapPanel = new MapPanel(map);
		add(mapPanel);
		absMap = new AbstractMap(map);
	}
	
	public static void main(String[] args) {
		TestFrame frame = new TestFrame();
		frame.setTitle("Map");
		//frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		frame.setVisible(true);
		
		Coordinate startCoor = new Coordinate(50, 300);
		Coordinate endCoor = new Coordinate(1000, 500);
		Path path = frame.absMap.findPath(startCoor, endCoor);
		frame.mapPanel.paintPath(path);
	}
}
	
class MapPanel extends JPanel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Map map;
	
	private Path currentPath;
	
	public MapPanel(Map map) {
		this.map = map;
		currentPath = new Path();
	}
	
	public MapPanel() {
		super();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		paintMap(g, map);
		paintPath(g, currentPath);
		
	}
	
	private void paintMap(Graphics g, Map map) {
		paintPathUnits(g, map.getPathUnitList());
		paintLocation(g, map.getLocationList());
	}
	
	private void paintPathUnits(Graphics g, ArrayList<PathUnit> pathUnitList) {
		for (PathUnit pathUnit : pathUnitList) {
			int x1 = (int) pathUnit.getStartPoint().getX();
			int y1 = (int) pathUnit.getStartPoint().getY();
			int x2 = (int) pathUnit.getEndPoint().getX();
			int y2 = (int) pathUnit.getEndPoint().getY();
			g.drawLine(x1, y1, x2, y2);
		}
	}
	
	private void paintLocation(Graphics g, ArrayList<Location> locationList) {
		for (Location location : locationList) {
			int x = (int) location.getCoordinate().getX();
			int y = (int) location.getCoordinate().getY();
			String name = location.getLocationName();
			g.drawRect(x, y, 3, 3);
			g.drawString(name, x, y);
			
		}
	}
	
	private void paintPath(Graphics g, Path path) {
		// Graphics g = getGraphics();
		ArrayList<PathUnit> pathUnits = path.getPathUnitList();
		Color originColor = g.getColor();

		// test
		for (PathUnit p : pathUnits) {
			System.out.println("start point : " + p.getStartPoint().getX()
					+ "  " + p.getStartPoint().getY() + "  "
					+ p.getEndPoint().getX() + "  " + p.getEndPoint().getY());
		}

		g.setColor(Color.red);
		paintPathUnits(g, pathUnits);
		g.setColor(originColor);
	}
	 
	public void paintPath(Path path) {
		currentPath = path;
		repaint();
	}
	 
}

