package org.dyndns.jubegraph.board.piece;

import java.util.List;

import org.dyndns.jubegraph.board.Board;
import org.dyndns.jubegraph.board.Board.Turn;
import org.dyndns.jubegraph.board.Move;
import org.dyndns.jubegraph.board.Position;

public class Kin extends Piece {

	public Kin(Turn turn) {
		super(turn);
	}

	public Kin(Turn turn, Boolean isPromotion) {
		super(turn, isPromotion);
	}

	@Override
	public List<Move> getMovePromotionSente(Position before, Board board) {
		return getMoveSenteKin(before, board);
	}

	@Override
	public List<Move> getMoveRegularSente(Position before, Board board) {
		return getMoveSenteKin(before, board);
	}

	@Override
	public List<Move> getMovePromotionGote(Position before, Board board) {
		return getMoveGoteKin(before, board);
	}

	@Override
	public List<Move> getMoveRegularGote(Position before, Board board) {
		return getMoveGoteKin(before, board);
	}

	@Override
	public String toStringRegular() {
		// TODO 自動生成されたメソッド・スタブ
		return "金";
	}

	@Override
	public String toStringPromotion() {
		return toStringRegular();
	}

	public List<Move> getMoveSenteKin(Position before, Board board) {
		return getMoveSenteKinBase(before, this, board);
	}

	public List<Move> getMoveGoteKin(Position before, Board board) {
		return getMoveGoteKinBase(before, this, board);
	}

	public static List<Move> getMoveSenteKinBase(Position before, Piece piece, Board board) {
		Integer[][] moveType = { { 1, -1 }, { 0, -1 }, { -1, -1 }, { 1, 0 }, { -1, 0 }, { 0, 1 } };
		List<Move> moveList = Piece.makeMove(before, moveType, false, piece, board);
		return moveList;
	}

	public static List<Move> getMoveGoteKinBase(Position before, Piece piece, Board board) {
		Integer[][] moveType = { { 1, 1 }, { 0, 1 }, { -1, 1 }, { 1, 0 }, { -1, 0 }, { 0, -1 } };
		List<Move> moveList = Piece.makeMove(before, moveType, false, piece, board);
		return moveList;
	}
}