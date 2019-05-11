package ru.maksimbulva.berlinchess.service.engine.adapter

import chess.Piece
import ru.maksimbulva.berlinchess.model.chess.PieceType
import ru.maksimbulva.berlinchess.model.chess.Player

object PieceTypeAdapter {
    fun fromEngine(encoded: Int): Pair<Player, PieceType>? {
        return when (encoded) {
            Piece.EMPTY -> null

            Piece.WKING -> Player.White to PieceType.King
            Piece.WQUEEN -> Player.White to PieceType.Queen
            Piece.WROOK -> Player.White to PieceType.Rook
            Piece.WBISHOP -> Player.White to PieceType.Bishop
            Piece.WKNIGHT -> Player.White to PieceType.Knight
            Piece.WPAWN -> Player.White to PieceType.Pawn

            Piece.BKING -> Player.Black to PieceType.King
            Piece.BQUEEN -> Player.Black to PieceType.Queen
            Piece.BROOK -> Player.Black to PieceType.Rook
            Piece.BBISHOP -> Player.Black to PieceType.Bishop
            Piece.BKNIGHT -> Player.Black to PieceType.Knight
            Piece.BPAWN -> Player.Black to PieceType.Pawn

            else -> throw IllegalArgumentException("Unexpected piece code $encoded")
        }
    }
}
