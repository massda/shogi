package org.dyndns.jubegraph.board.piece;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Keima extends Piece {

	public Keima(Turn turn) {
		super(turn);
	}

	public Keima(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		return Kin.getMoveSenteKinBase(before, this, board);
	}

	@Override
	public List<Move> getMoveRegularSente(Position before, Board board) {

		List<Move> moveList = new ArrayList<Move>();
		Integer[][] moveType = { { -1, -2 }, { 1, -2 } };
		moveList.addAll(makeMove(before, moveType, false, board));
		if (before.getY() - 2 < 3) {
			moveList.addAll(makeMove(before, moveType, true, board));
		}

		return moveList;
	}

	@Override
	public List<Move> getMovePromotionGote(Position before, Board board) {
		return Kin.getMoveGoteKinBase(before, this, board);
	}

	@Override
	public List<Move> getMoveRegularGote(Position before, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		Integer[][] moveType = { { -1, 2 }, { 1, 2 } };
		moveList.addAll(makeMove(before, moveType, false, board));
		if (before.getY() + 2 > 5) {
			moveList.addAll(makeMove(before, moveType, true, board));
		}

		return moveList;
	}

	@Override
	protected String toStringRegular() {
		return "桂";
	}

	@Override
	protected String toStringPromotion() {
		return "圭";
	}

}