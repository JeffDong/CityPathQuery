package models.mapItems;

import java.util.ArrayList;

/**
 * 描述：路径类，一个路径是由多个路段和一个开始点和结束点组成的集合，注意路径的开始点或结束点不一定<br>
 * 是某个路段的开始或结束点，因为一个路段可以只有一部分在路径中
 * @author 董剑峰
 *
 */
public class Path {
	private ArrayList<PathUnit> pathUnitList;
	private Coordinate startPoint;
	private Coordinate endPoint;
	
	public Path() {
		pathUnitList = new ArrayList<PathUnit>();
	}
	
	double getScore () {
		double score = -1;
		//遍历pathUnitList计算路径的分数
		return score;
	}
	
	double getLength() {
		return startPoint.getDistance(endPoint);
	}

	public ArrayList<PathUnit> getPathUnitList() {
		return pathUnitList;
	}
	
	
}
