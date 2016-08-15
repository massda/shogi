package org.dyndns.jubegraph.board.piece;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Hisha extends Piece {

	public Hisha(Turn turn) {
		super(turn);
	}

	public Hisha(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		moveList.addAll(getMoveBase(before, false, board));

		Integer[][] moveType = { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 } };
		moveList.addAll(makeMove(before, moveType, false, board));

		return moveList;
	}

	@Override
	public List<Move> getMoveRegularSente(Position before, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		moveList.addAll(getMoveBase(before, false, board));

		//promotion
		if (before.getY() < 3) {
			moveList.addAll(getMoveBase(before, true, board));
		}

		return moveList;
	}

	@Override
	public List<Move> getMovePromotionGote(Position before, Board board) {
		return getMovePromotionSente(before, board);
	}

	@Override
	public List<Move> getMoveRegularGote(Position before, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		moveList.addAll(getMoveBase(before, false, board));

		//promotion
		if (before.getY() > 5) {
			moveList.addAll(getMoveBase(before, true, board));
		}

		return moveList;
	}

	@Override
	protected String toStringRegular() {
		return "飛";
	}

	@Override
	protected String toStringPromotion() {
		return "龍";
	}

	public List<Move> getMoveBase(Position before, Boolean promotion, Board board) {
		List<Move> moveList = new ArrayList<Move>();

		Integer x = before.getX();
		Integer y = before.getY();

		for (Integer newY = y - 1; newY >= 0; --newY) {
			if (!addMove(x, newY, moveList, before, promotion, board)) {
				break;
			}
		}

		for (Integer newY = y + 1; newY < 9; ++newY) {
			if (!addMove(x, newY, moveList, before, promotion, board)) {
				break;
			}
		}

		for (Integer newX = x - 1; newX >= 0; --newX) {
			if (!addMove(newX, y, moveList, before, promotion, board)) {
				break;
			}
		}

		for (Integer newX = x + 1; newX < 9; ++newX) {
			if (!addMove(newX, y, moveList, before, promotion, board)) {
				break;
			}
		}

		return moveList;
	}

}