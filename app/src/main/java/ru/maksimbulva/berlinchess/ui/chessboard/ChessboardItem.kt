package ru.maksimbulva.berlinchess.ui.chessboard

import ru.maksimbulva.berlinchess.model.chess.PieceType
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square

class ChessboardItem(
    val player: Player,
    val pieceType: PieceType,
    val square: Square
)
