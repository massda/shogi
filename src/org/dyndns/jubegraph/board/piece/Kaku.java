package org.dyndns.jubegraph.board.piece;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Kaku extends Piece {

	public Kaku(Turn turn) {
		super(turn);
	}

	public Kaku(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		moveList.addAll(getMoveBase(before, false, board));

		Integer[][] moveType = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
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
		return "角";
	}

	@Override
	protected String toStringPromotion() {
		return "馬";
	}

	public List<Move> getMoveBase(Position before, Boolean promotion, Board board) {
		List<Move> moveList = new ArrayList<Move>();

		Integer x = before.getX();
		Integer y = before.getY();

		for (Integer newX = x + 1, newY = y + 1; newX < 9 && newY < 9; ++newX, ++newY) {
			if (!addMove(newX, newY, moveList, before, promotion, board)) {
				break;
			}
		}

		for (Integer newX = x - 1, newY = y + 1; newX >= 0 && newY < 9; --newX, ++newY) {
			if (!addMove(newX, newY, moveList, before, promotion, board)) {
				break;
			}
		}
		for (Integer newX = x + 1, newY = y - 1; newX < 9 && newY >= 0; ++newX, --newY) {
			if (!addMove(newX, newY, moveList, before, promotion, board)) {
				break;
			}
		}
		for (Integer newX = x - 1, newY = y - 1; newX >= 0 && newY >= 0; --newX, --newY) {
			if (!addMove(newX, newY, moveList, before, promotion, board)) {
				break;
			}
		}

		return moveList;
	}
}