package org.dyndns.jubegraph.board.piece;

import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Gyoku extends Piece {

	public Gyoku(Turn turn) {
		super(turn);
	}

	public Gyoku(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public List<Move> getMoveRegularSente(Position before, Board board) {
		Integer[][] moveType = { { 1, -1 }, { 0, -1 }, { -1, -1 }, { 1, 0 }, { -1, 0 }, { 1, 1 }, { 0, 1 }, { -1, 1 } };
		List<Move> moveList = makeMove(before, moveType, false, board);
		return moveList;
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		return getMoveRegularSente(before, board);
	}

	@Override
	public List<Move> getMovePromotionGote(Position before, Board board) {
		return getMoveRegularSente(before, board);
	}

	@Override
	public List<Move> getMoveRegularGote(Position before, Board board) {
		return getMoveRegularSente(before, board);
	}

	@Override
	protected String toStringRegular() {
		return "王";
	}

	@Override
	protected String toStringPromotion() {
		return "王";
	}

}