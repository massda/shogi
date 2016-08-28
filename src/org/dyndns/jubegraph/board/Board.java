package org.dyndns.jubegraph.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
				Integer count = 1;
				if (senteHold.containsKey(currentPiece)) {
					count += senteHold.get(currentPiece);
				}
				senteHold.put(currentPiece, count);
			} else {
				Integer count = 1;
				if (goteHold.containsKey(currentPiece)) {
					count += goteHold.get(currentPiece);
				}
				goteHold.put(currentPiece, count);
			}
		}
		board[p.getX()][p.getY()] = piece;

	}

	public Piece get(Integer i, Integer j) {
		return board[i][j];
	}

	public static List<Move> getMoveList(Board board, Turn turn) {
		List<Move> moveList = new ArrayList<Move>();

		for (Integer i = 0; i < 9; ++i) {
			for (Integer j = 0; j < 9; ++j) {
				Piece piece = board.get(i, j);
				if (piece != null) {
					if (piece.getTurn() == turn) {
						Position position = new Position(i, j);
						moveList.addAll(piece.getMovedPositionList(position, board));
					}
				}
			}
		}
		return moveList;
	}

	public static String getKifuLine(Integer num, Move move) {
		String kifuLine = new String();
		kifuLine += num.toString() + " ";
		kifuLine += move.getAfter().toString();
		Piece p = move.getPiece();
		if (p.isPromotion() && !move.getPromotion()) {
			if(p.getClass() == Kaku.class){
				kifuLine += "馬";
			}else if(p.getClass() == Hisha.class){
				kifuLine += "龍";
			}else if(p.getClass() == Fu.class){
				kifuLine += "と";
			}else{
				kifuLine += "成" + p.toStringRegular();
			}
		}else{
			kifuLine += p.toStringRegular();
		}
		if (move.getPromotion()) {
			kifuLine += "成";
		}
		kifuLine += "(" + move.getBefore().toStringKifuBefore() + ")";
		kifuLine += " ( 0:00/00:00:00)";

		return kifuLine;
	}

	public static void randomTest2() {
		Board board = new Board();
		Turn turn = Turn.SENTE;

		for (Integer i = 0; i < 1000; ++i) {
			List<Move> moveList = getMoveList(board, turn);

			Random rnd = new Random();
			int ran = rnd.nextInt(moveList.size());
			Move move = moveList.get(ran);

			board.move(move);
			String kifuLine = getKifuLine(i + 1, move);
			System.out.println(kifuLine);

			turn = turn.changeTurn();
		}
	}

	public static void randomTest() {
		Board board = new Board();
		Turn turn = Turn.SENTE;
		board.print();

		for (Integer i = 0; i < 200; ++i) {
			List<Move> moveList = getMoveList(board, turn);

			Random rnd = new Random();
			int ran = rnd.nextInt(moveList.size());
			Move move = moveList.get(ran);

			board.move(move);
			System.out.println(move);
			System.out.println();
			turn = turn.changeTurn();
			board.print();
		}
	}

	public static void firstTest() {
		Board board = new Board();

		board.print();

		Move move = new Move(new Position(7, 6), new Position(7, 5), false, new Fu(Turn.SENTE));
		System.out.println();
		board.move(move);
		board.print();

		Board.getMoveList(board, Turn.SENTE);

	}

	public static void main(String[] args) {
		randomTest2();
	}

}