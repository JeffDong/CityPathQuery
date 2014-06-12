package models.mapItems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

/**
 * ���������㷨�ĳ����ͼ�࣬����ʵ�ĵ�ͼ��ȡ·����Ϣ�õ�������ִ��·�������㷨
 * @author Jeff
 *
 */
public class AbstractMap {
	private ArrayList<CrossPoint> crossPoints; //crosspoint��
	
	public AbstractMap(Map map) {
		ArrayList<PathUnit> pathUnitList = map.getPathUnitList();
		crossPoints = new ArrayList<CrossPoint>();
		for (PathUnit p : pathUnitList) {
			insertPathUnit(p);
		}
	}
	
	/**
	 * ���һ�������Ӧ��CrossPoint��
	 * @param coordinate
	 * @return ����ж�Ӧ�����������CrossPoint���У��������crosspoint�����򴴽�
	 * �µ�crosspoint ����,���������crossPoint����
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
	 * ��һ��·�μ�������ͼ
	 * ���俪ʼ��ͽ��������crosspoint�����ֱ�Ϊ������ھ���Ϣ
	 * @param pathUnit
	 * @return
	 */
	public boolean insertPathUnit(PathUnit pathUnit) {
		CrossPoint start,end;
		start = getCrossPointByCoordinate(pathUnit.getStartPoint());
		end = getCrossPointByCoordinate(pathUnit.getEndPoint());
		return (start.addNeighbor(end, pathUnit) && end.addNeighbor(start, pathUnit));
	}
	
	//����Ϊ·�������㷨
	
	/**
	 * ��������ҵ���һ������������crossPoint
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
	 * ��¼���ھӵ���Ϣ�����������ھӵ�·�κ��ھӽڵ�
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
	
	//�ý���������
	private Coordinate coordinate;
	//�ھӱ���¼�˽������ھ�
	private ArrayList<CrossPoint> neighborList;
	//�ھ���Ϣ��,��¼���ھӶ�Ӧ��·�Σ�Ϊ�������ʱ��ȡ·����Ϣ
	private ArrayList<NeighborInfo> neighborInfoList;
	
	public CrossPoint(Coordinate coordinate) {
		this.coordinate = coordinate;
		neighborInfoList = new ArrayList<CrossPoint.NeighborInfo>();
		neighborList = new ArrayList<CrossPoint>();
	}
	
	
	
	/**
	 * ����������ھӽڵ��·�μ����ھ���Ϣ�����ؼ�����
	 * @param neighbor
	 * @param pathUnit
	 * @return
	 */
	public boolean addNeighbor(CrossPoint neighbor , PathUnit pathUnit) {
		//���neighbor�Ƿ��ظ�,���ظ�������ھӱ�
		if (neighborList.add(neighbor) == false) {
			return false;
		}
		//��·����Ϣ�����ھ���Ϣ��
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