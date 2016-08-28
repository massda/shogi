package org.dyndns.jubegraph.board.piece;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Fu extends Piece {

	public Fu(Turn turn) {
		super(turn);
	}

	public Fu(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public Fu clone(){
		return new Fu(this.turn, this.isPromotion);
	}

	@Override
	public String toStringRegular() {
		return "歩";
	}

	@Override
	public String toStringPromotion() {
		return "と";
	}

	@Override
	public List<Move> getMoveRegularSente(Position before, Board board) {
		List<Move> positionList = new ArrayList<Move>();

		Integer newX = before.getX();
		Integer newY = before.getY() - 1;
		if (newY > 0) {
			Piece p = board.get(newX, newY); // 異動先の駒
			if (p == null || p.getTurn() != this.getTurn()) {
				positionList.add(new Move(before, new Position(newX, newY), false, this));
				if (newY <= 3) {
					positionList.add(new Move(before, new Position(newX, newY), false, this));
				}
			}
		}

		return positionList;
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		return Kin.getMoveSenteKinBase(before, this, board);
	}

	@Override
	public List<Move> getMovePromotionGote(Position before, Board board) {
		return Kin.getMoveGoteKinBase(before, this, board);
	}

	@Override
	public List<Move> getMoveRegularGote(Position before, Board board) {
		List<Move> positionList = new ArrayList<Move>();

		Integer newX = before.getX();
		Integer newY = before.getY() + 1;
		if (newY < 9) {
			Piece p = board.get(newX, newY); // 異動先の駒
			if (p == null || p.getTurn() != this.getTurn()) {
				positionList.add(new Move(before, new Position(newX, newY), false, this));
				if (newY >= 7) {
					positionList.add(new Move(before, new Position(newX, newY), true, this));
				}
			}
		}

		return positionList;

	}

}