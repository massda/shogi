package org.dyndns.jubegraph.board.piece;

import java.util.ArrayList;
import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public abstract class Piece {

	Turn turn;
	Boolean isPromotion = false;

	public Piece(Turn turn) {
		this.turn = turn;
	}

	public Piece(Turn turn, Boolean isPromotion) {
		this.turn = turn;
		this.isPromotion = isPromotion;
	}

	public void promote() {
		isPromotion = true;
	}

	public Boolean isSente() {
		return turn == Turn.SENTE;
	}

	public Turn getTurn() {
		return turn;
	}

	public Boolean isPromotion() {
		return isPromotion;
	}

	public Boolean canPromote(Integer y) {
		return this.getTurn() == Turn.SENTE && y < 3 || this.getTurn() == Turn.GOTE && y >= 6;
	}

	public Boolean addMove(Integer newX, Integer newY, List<Move> moveList, Position before, Boolean promotion,
			Board board) {
		Piece p = board.get(newX, newY);
		if (p == null || p.getTurn() != this.getTurn()) {
			moveList.add(new Move(before, new Position(newX, newY), promotion, this));
			if (!promotion && !this.isPromotion() && this.canPromote(newY)) {
				moveList.add(new Move(before, new Position(newX, newY), true, this));
			}
		}
		return p == null;
	}

	public List<Move> getMovedPositionList(Position before, Board board) {
		if (isPromotion) {
			return isSente() ? getMovePromotionSente(before, board)
					: getMovePromotionGote(before, board);
		} else {
			return isSente() ? getMoveRegularSente(before, board)
					: getMoveRegularGote(before, board);
		}
	}

	abstract public List<Move> getMovePromotionSente(Position before,
			Board board);

	abstract public List<Move> getMoveRegularSente(Position before, Board board);

	abstract public List<Move> getMovePromotionGote(Position before, Board board);

	abstract public List<Move> getMoveRegularGote(Position before, Board board);

	public static List<Move> makeMove(Position before, Integer[][] moveType,
			Boolean promotion, Piece piece, Board board) {
		List<Move> moveList = new ArrayList<Move>();
		for (Integer[] move : moveType) {
			Integer newX = before.getX() + move[0];
			Integer newY = before.getY() + move[1];
			if (Position.isValid(newX, newY)) {
				Piece p = board.get(newX, newY); // 異動先の駒
				if (p == null || p.getTurn() != piece.getTurn()) {

					moveList.add(new Move(before, new Position(newX, newY),
							promotion, piece));
				}
			}
		}
		return moveList;
	}

	public List<Move> makeMove(Position before, Integer[][] moveType,
			Boolean promotion, Board board) {
		return makeMove(before, moveType, promotion, this, board);
	}

	@Override
	public String toString() {
		if (isPromotion) {
			return isSente() ? " " + toStringPromotion() : "v"
					+ toStringPromotion();
		} else {
			return isSente() ? " " + toStringRegular() : "v"
					+ toStringRegular();
		}
	}

	abstract protected String toStringRegular();

	abstract protected String toStringPromotion();

	public boolean equals(Piece koma) {
		return this.getClass() == koma.getClass()
				&& isSente().equals(koma.isSente())
				&& isPromotion().equals(koma.isPromotion());
	}

}