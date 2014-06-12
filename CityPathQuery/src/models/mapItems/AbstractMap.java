package models.mapItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * 用于搜索算法的抽象地图类，由真实的地图提取路段信息得到，可以执行路径搜索算法
 * @author Jeff
 *
 */
public class AbstractMap {
	private ArrayList<CrossPoint> crossPoints; //crosspoint表
	
	public AbstractMap(Map map) {
		ArrayList<PathUnit> pathUnitList = map.getPathUnitList();
		crossPoints = new ArrayList<CrossPoint>();
		for (PathUnit p : pathUnitList) {
			insertPathUnit(p);
		}
	}
	
	/**
	 * 获得一个坐标对应的CrossPoint，
	 * @param coordinate
	 * @return 如果有对应的坐标存在于CrossPoint表中，返回这个crosspoint，否则创建
	 * 新的crosspoint 对象,并将其加入crossPoint表中
	 */
	private CrossPoint getCrossPointByCoordinate(Coordinate coordinate) {
		for (CrossPoint p : crossPoints) {
			if (coordinate.isEqual(p.getCoordinate())) {
				return p;
			}
		}
		CrossPoint newPoint = new CrossPoint(coordinate);
		crossPoints.add(newPoint);
		return newPoint;
	}
	
	/**
	 * 将一个路段加入抽象地图
	 * 将其开始点和结束点加入crosspoint表，并分别为其添加邻居信息
	 * @param pathUnit
	 * @return
	 */
	public boolean insertPathUnit(PathUnit pathUnit) {
		CrossPoint start,end;
		start = getCrossPointByCoordinate(pathUnit.getStartPoint());
		end = getCrossPointByCoordinate(pathUnit.getEndPoint());
		return (start.addNeighbor(end, pathUnit) && end.addNeighbor(start, pathUnit));
	}
	
	//以下为路径查找算法
	
	/**
	 * 这个函数找到离一个坐标点最近的crossPoint
	 * @param coordinate
	 * @return
	 */
	private CrossPoint findNearestCrossPoint(Coordinate coordinate) {
		CrossPoint result = null;
		double minDistance = Double.MAX_VALUE;
		for (CrossPoint p : crossPoints) {
			double distance = coordinate.getDistance(p.getCoordinate());
			if (distance < minDistance) {
				minDistance = distance;
				result = p;
			}
		}
		return result;
	}
	
	
	private Path findPath(CrossPoint start, CrossPoint end, HashMap<CrossPoint, Boolean> isVisited,
			ArrayList<Path> resultPathList) {
		//Path result = new Path();
		final CrossPoint p = end;
		Comparator<CrossPoint> compareCrosspoint = new Comparator<CrossPoint>() {
			@Override
			public int compare(CrossPoint arg0, CrossPoint arg1) {
				Double distance0 = arg0.getCoordinate().getDistance(p.getCoordinate());
				Double distance1 = arg1.getCoordinate().getDistance(p.getCoordinate());
				return distance0.compareTo(distance1);
			}
		};
		Collections.sort(start.getNeighborList(), compareCrosspoint);
		for (CrossPoint neighbor : start.getNeighborList()) {
			
			if (neighbor != end && isVisited.get(neighbor) == false) {
				isVisited.put(neighbor, true);
				Path path = findPath(neighbor, end, isVisited, resultPathList);
				if (path != null) {
					path.getPathUnitList().add(start.getPathUnitByNeighbor(neighbor));
					return path;
				}
			}
			else if (neighbor == end){
				Path path = new Path();
				isVisited.put(neighbor, true);
				path.getPathUnitList().add(start.getPathUnitByNeighbor(neighbor));
				resultPathList.add(path);
				return path;
			}
		}
		return null;
	}
	
	
	public Path findPath(Coordinate startCoor, Coordinate endCoor) {
		CrossPoint start,end;
		start = findNearestCrossPoint(startCoor);
		end = findNearestCrossPoint(endCoor);
		ArrayList<Path> resultPathList = new ArrayList<Path>();
		HashMap<CrossPoint, Boolean> isVisited = new HashMap<CrossPoint, Boolean>();
		for (CrossPoint p : crossPoints) {
			isVisited.put(p, false);
		}
		return findPath(start, end, isVisited, resultPathList);
	}
	
	
	
}

class CrossPoint {
	
	
	/**
	 * 记录了邻居的信息，包括到达邻居的路段和邻居节点
	 * @author Jeff
	 *
	 */
	class NeighborInfo {
		CrossPoint neighbor;
		PathUnit pathUnit;
		
		public NeighborInfo(CrossPoint neighbor, PathUnit pathUnit) {
			this.neighbor = neighbor;
			this.pathUnit = pathUnit;
		}
	}
	
	//该交叉点的坐标
	private Coordinate coordinate;
	//邻居表，记录了交叉点的邻居
	private ArrayList<CrossPoint> neighborList;
	//邻居信息表,记录了邻居对应的路段，为方便查找时获取路段信息
	private ArrayList<NeighborInfo> neighborInfoList;
	
	public CrossPoint(Coordinate coordinate) {
		this.coordinate = coordinate;
		neighborInfoList = new ArrayList<CrossPoint.NeighborInfo>();
		neighborList = new ArrayList<CrossPoint>();
	}
	
	
	
	/**
	 * 这个函数将邻居节点和路段加入邻居信息表，返回加入结果
	 * @param neighbor
	 * @param pathUnit
	 * @return
	 */
	public boolean addNeighbor(CrossPoint neighbor , PathUnit pathUnit) {
		//检查neighbor是否重复,不重复则加入邻居表
		if (neighborList.add(neighbor) == false) {
			return false;
		}
		//将路段信息加入邻居信息表
		neighborInfoList.add(new NeighborInfo(neighbor, pathUnit));
		return true;
	}
	
	public PathUnit getPathUnitByNeighbor(CrossPoint neighbor) {
		for (NeighborInfo p : neighborInfoList) {
			if (p.neighbor == neighbor) {
				return p.pathUnit;
			}
		}
		return null;
	}
	
	public Coordinate getCoordinate() {
		return coordinate;
	}

	public ArrayList<CrossPoint> getNeighborList() {
		return neighborList;
	}
	
	
	
}