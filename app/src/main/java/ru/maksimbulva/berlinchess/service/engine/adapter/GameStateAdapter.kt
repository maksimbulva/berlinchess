package ru.maksimbulva.berlinchess.service.engine.adapter

import chess.Game
import ru.maksimbulva.berlinchess.model.chess.GameState

object GameStateAdapter {
    fun fromEngine(game: Game): GameState {
        return GameState(
            moveList = game.moveList.map(MoveAdapter::fromEngine)
        )
    }
}