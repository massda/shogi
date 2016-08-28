package org.dyndns.jubegraph.board.piece;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Kyou extends Piece {

	public Kyou(Turn turn) {
		super(turn);
	}

	public Kyou(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		return Kin.getMoveSenteKinBase(before, this, board);
	}

	@Override
	public List<Move> getMoveRegularSente(Position before, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		Integer x = before.getX();

		for (Integer y = before.getY() - 1; y >= 0; --y) {
			Piece p = board.get(x, y);
			if (p == null || p.getTurn() != this.getTurn()) {
				moveList.add(new Move(before, new Position(x, y), false, this));
				if (y < 3) {
					moveList.add(new Move(before, new Position(x, y), true,
							this));
				}
			}
			if (p != null) {
				break;
			}
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
		Integer x = before.getX();

		for (Integer y = before.getY() + 1; y < 9; ++y) {
			Piece p = board.get(x, y);
			if (p == null || p.getTurn() != this.getTurn()) {
				moveList.add(new Move(before, new Position(x, y),
						false, this));
				if (y >= 6) {
					moveList.add(new Move(before,
							new Position(before.getX(), y), true, this));
				}
			}
			if (p != null) {
				break;
			}
		}

		return moveList;
	}

	@Override
	public String toStringRegular() {
		return "香";
	}

	@Override
	public String toStringPromotion() {
		return "杏";
	}

}