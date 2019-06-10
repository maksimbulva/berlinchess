package ru.maksimbulva.berlinchess.service.engine

import guibase.ChessController
import ru.maksimbulva.berlinchess.model.chess.Move
import ru.maksimbulva.berlinchess.model.chess.PieceType
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.service.engine.adapter.MoveAdapter

class ChessEngine {

    private val listener = EngineListener()
    private val controller = ChessController(listener)

    val positionFlowable = listener.positionFlowable

    val piecePromotionRequestFlowable = listener.piecePromotionRequestFlowable

    private val humanPlayer get() = Player.White

    init {
        val humanIsWhite = humanPlayer == Player.White
        controller.newGame(humanIsWhite, 16, false)
        controller.startGame()
    }

    fun playMove(move: Move) {
        controller.humanMove(MoveAdapter.toEngine(move, humanPlayer))
    }

    fun onPromotionPieceSelected(pieceType: PieceType) {
        val encodedChoice = when (pieceType) {
            PieceType.Knight -> ENGINE_PROMOTION_CHOICE_KNIGHT
            PieceType.Bishop -> ENGINE_PROMOTION_CHOICE_BISHOP
            PieceType.Rook -> ENGINE_PROMOTION_CHOICE_ROOK
            PieceType.Queen -> ENGINE_PROMOTION_CHOICE_QUEEN
            else -> return
        }
        controller.reportPromotePiece(encodedChoice)
    }

    companion object {
        private const val ENGINE_PROMOTION_CHOICE_KNIGHT = 3
        private const val ENGINE_PROMOTION_CHOICE_BISHOP = 2
        private const val ENGINE_PROMOTION_CHOICE_ROOK = 1
        private const val ENGINE_PROMOTION_CHOICE_QUEEN = 0
    }
}
