package org.dyndns.jubegraph.board;

public class Position {
	private static String[] xPrint = { "９", "８", "７", "６", "５", "４", "３", "２", "１" };
	private static String[] yPrint = { "一", "二", "三", "四", "五", "六", "七", "八", "九" };

	public Integer x; // 0 to 8
	public Integer y; // 0 to 8

	public Position(Integer x, Integer y) {
		this.x = x;
		this.y = y;
	}

	public Integer getX() {
		return x;
	}

	public Integer getY() {
		return y;
	}

	public Boolean isValid() {
		return getX() >= 0 && getX() < 9 && getY() >= 0 && getY() < 9;
	}

	public static Boolean isValid(Position position) {
		return position.getX() >= 0 && position.getX() < 9 && position.getY() >= 0 && position.getY() < 9;
	}

	public static Boolean isValid(Integer x, Integer y) {
		return x >= 0 && x < 9 && y >= 0 && y < 9;
	}

	public String toString() {
		if (isValid()) {
			return xPrint[x] + yPrint[y];
		} else {
			return "(" + x + "," + y + ")";
		}
	}

	public String toStringKifuBefore(){
		Integer kifuX = 9 - x;
		Integer kifuY = y + 1;
		return "" + kifuX + kifuY;
	}

}