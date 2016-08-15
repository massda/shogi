package org.dyndns.jubegraph.board;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.piece.Piece;

public class Move {
	Position before;
	Position after;
	Boolean promotion;
	Piece piece;

	public Move(Position before, Position after, Boolean promotion, Piece piece) {
		super();
		this.before = before;
		this.after = after;
		this.promotion = promotion;
		this.piece = piece;
	}

	public Position getBefore() {
		return before;
	}

	public Position getAfter() {
		return after;
	}

	public Boolean getPromotion() {
		return promotion;
	}

	public Piece getPiece() {
		return piece;
	}

	public static List<Move> filterInvalidMove(List<Move> moveList) {
		List<Move> filteredMoveList = new ArrayList<Move>();
		for (Move move : moveList) {
			if (Position.isValid(move.getAfter()) && Position.isValid(move.getBefore())) {
				filteredMoveList.add(move);
			}
		}
		return filteredMoveList;
	}

	public String toString() {
		String promotionStr = "";
		if (getPromotion()) {
			promotionStr = "成";
		}
		return before + "->" + after + " " + piece.toString() + promotionStr;
	}

}