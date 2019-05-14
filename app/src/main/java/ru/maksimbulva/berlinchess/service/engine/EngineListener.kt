package ru.maksimbulva.berlinchess.service.engine

import chess.Move
import chess.Position
import guibase.GUIInterface
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject

class EngineListener : GUIInterface {

    private val positionSubject = BehaviorSubject.create<Position>()

    val positionFlowable: Flowable<Position> get() = positionSubject.toFlowable(BackpressureStrategy.LATEST)

    /** Update the displayed board position.  */
    override fun setPosition(pos: Position?) {
        pos?.let { positionSubject.onNext(it) }
    }

    /** Mark square i as selected. Set to -1 to clear selection.  */
    override fun setSelection(sq: Int) {
    }

    /** Set the status text.  */
    override fun setStatusString(str: String?) {
    }

    /** Update the list of moves.  */
    override fun setMoveListString(str: String?) {
    }

    /** Update the computer thinking information.  */
    override fun setThinkingString(str: String?) {
    }

    /** Get the current time limit.  */
    override fun timeLimit(): Int {
        return Int.MAX_VALUE
    }

    /** Get "random move" setting.  */
    override fun randomMode(): Boolean {
        return true
    }

    /** Return true if "show thinking" is enabled.  */
    override fun showThinking(): Boolean {
        return false
    }

    /** Ask what to promote a pawn to. Should call reportPromotePiece() when done.  */
    override fun requestPromotePiece() {
    }

    /** Run code on the GUI thread.  */
    override fun runOnUIThread(runnable: Runnable?) {
        runnable?.let { AndroidSchedulers.mainThread().scheduleDirect(it) }
    }

    /** Report that user attempted to make an invalid move.  */
    override fun reportInvalidMove(m: Move?) {
    }
}