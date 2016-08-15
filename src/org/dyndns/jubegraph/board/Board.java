package org.dyndns.jubegraph.board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dyndns.jubegraph.board.piece.Fu;
import org.dyndns.jubegraph.board.piece.Gin;
import org.dyndns.jubegraph.board.piece.Gyoku;
import org.dyndns.jubegraph.board.piece.Hisha;
import org.dyndns.jubegraph.board.piece.Kaku;
import org.dyndns.jubegraph.board.piece.Keima;
import org.dyndns.jubegraph.board.piece.Kin;
import org.dyndns.jubegraph.board.piece.Kyou;
import org.dyndns.jubegraph.board.piece.Piece;

/* 左上が原点(0.0)*/
public class Board {

	public enum Turn {
		SENTE, GOTE;

		public Turn changeTurn() {
			if (this == SENTE) {
				return GOTE;
			} else {
				return SENTE;
			}
		}
	}

	Piece board[][];
	Map<Piece, Integer> senteHold;
	Map<Piece, Integer> goteHold;

	public Board() {
		init();
	}

	public void init() {

		board = new Piece[9][9];
		senteHold = new HashMap<Piece, Integer>();
		goteHold = new HashMap<Piece, Integer>();

		initBoard();
	}

	public void initBoard() {
		// 歩
		for (Integer x = 0; x < 9; x++) {
			board[x][2] = new Fu(Turn.GOTE);
			board[x][6] = new Fu(Turn.SENTE);
		}
		// 香
		board[0][0] = new Kyou(Turn.GOTE);
		board[8][0] = new Kyou(Turn.GOTE);
		board[0][8] = new Kyou(Turn.SENTE);
		board[8][8] = new Kyou(Turn.SENTE);

		// 桂
		board[1][0] = new Keima(Turn.GOTE);
		board[7][0] = new Keima(Turn.GOTE);
		board[1][8] = new Keima(Turn.SENTE);
		board[7][8] = new Keima(Turn.SENTE);

		// 銀
		board[2][0] = new Gin(Turn.GOTE);
		board[6][0] = new Gin(Turn.GOTE);
		board[2][8] = new Gin(Turn.SENTE);
		board[6][8] = new Gin(Turn.SENTE);

		// 金
		board[3][0] = new Kin(Turn.GOTE);
		board[5][0] = new Kin(Turn.GOTE);
		board[3][8] = new Kin(Turn.SENTE);
		board[5][8] = new Kin(Turn.SENTE);

		// 飛
		board[1][1] = new Hisha(Turn.GOTE);
		board[7][7] = new Hisha(Turn.SENTE);

		// 角
		board[7][1] = new Kaku(Turn.GOTE);
		board[1][7] = new Kaku(Turn.SENTE);

		// 玉
		board[4][0] = new Gyoku(Turn.GOTE);
		board[4][8] = new Gyoku(Turn.SENTE);
	}

	public void print() {
		for (Integer y = 0; y < 9; ++y) {
			for (Integer x = 0; x < 9; ++x) {
				if (board[x][y] == null) {
					System.out.print(" . ");
				} else {
					System.out.print(board[x][y].toString());
				}
			}
			System.out.println();
		}

	}

	public void move(Move move) {
		Piece koma = move.getPiece();

		Position before = move.getBefore();
		Position after = move.getAfter();

		remove(before, koma);
		if (move.getPromotion()) {
			koma.promote();
		}
		put(after, koma);
	}

	public void remove(Position p, Piece koma) {
		if (board[p.getX()][p.getY()] == null || !board[p.getX()][p.getY()].equals(koma)) {
			System.err.println("Invalid remove");
			throw new RuntimeException();
		} else {
			board[p.getX()][p.getY()] = null;
		}
	}

	public void put(Position p, Piece piece) {
		Integer x = p.getX();
		Integer y = p.getY();

		if (board[x][y] != null) {
			Piece currentPiece = board[x][y];
			currentPiece.getTurn().changeTurn();
			if (currentPiece.isSente()) {
				senteHold.put(currentPiece, senteHold.get(currentPiece) + 1);
			} else {
				goteHold.put(currentPiece, goteHold.get(currentPiece) + 1);
			}

			System.err.println("Invalid put");
			throw new RuntimeException();
		} else {
			board[p.getX()][p.getY()] = piece;
		}
	}

	public Piece get(Integer i, Integer j) {
		return board[i][j];
	}

	public static List<Move> getMoveList(Board board, Turn turn) {
		for (Integer i = 0; i < 9; ++i) {
			for (Integer j = 0; j < 9; ++j) {
				Piece piece = board.get(i, j);
				if (piece != null) {
					if (piece.getTurn() == turn) {
						Position position = new Position(i, j);
						System.out.println("" + i + "," + j + "," + piece);
						List<Move> moveList = piece.getMovedPositionList(position, board);
						System.out.println(moveList);
					}
				}
			}
		}
		return null;
	}

	public static void main(String[] args) {
		Board board = new Board();

		board.print();

		Move move = new Move(new Position(7, 6), new Position(7, 5), false, new Fu(Turn.SENTE));
		System.out.println();
		board.move(move);
		board.print();

		Board.getMoveList(board, Turn.SENTE);

	}

}