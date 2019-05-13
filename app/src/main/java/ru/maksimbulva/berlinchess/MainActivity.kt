package ru.maksimbulva.berlinchess

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.BehaviorSubject
import ru.maksimbulva.berlinchess.model.chess.Board
import ru.maksimbulva.berlinchess.model.chess.Player
import ru.maksimbulva.berlinchess.model.chess.Square
import ru.maksimbulva.berlinchess.service.engine.ChessEngine
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardItem
import ru.maksimbulva.berlinchess.ui.chessboard.ChessboardView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val boards: BehaviorSubject<Board?> = BehaviorSubject.create()
    private val selectedSquares: BehaviorSubject<Set<Int>> = BehaviorSubject.create()

    var positionsDisposable: Disposable? = null
    var selectedSquaresDisposable: Disposable? = null
    var boardUpdater: Disposable? = null
    var humanMoveDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener(this)

        val chessboard: ChessboardView = findViewById(R.id.chessboard)

        val chessEngine = ChessEngine()
        positionsDisposable = chessEngine.boardObservable
            .subscribe { boards.onNext(it) }

        selectedSquaresDisposable = chessboard.clicks
            .subscribe { item ->
                val currentSelection: Set<Int> = selectedSquares.value ?: emptySet()
                selectedSquares.onNext(currentSelection + item.square.index)
            }

        selectedSquares.onNext(emptySet())

        boardUpdater = Observable.combineLatest<Board?, Set<Int>, Pair<Board?, Set<Int>>>(
            boards,
            selectedSquares,
            BiFunction { board, selection -> board to selection }
        )
            .subscribe { (board, selection) ->
                chessboard.setItems(
                    board?.squares?.mapIndexed { index, pieceOnBoard ->
                        ChessboardItem(
                            square = Square(index),
                            player = pieceOnBoard?.player,
                            pieceType = pieceOnBoard?.pieceType,
                            isHighlighted = (index in selection)
                        )
                    } ?: emptyList()
                )
            }

        humanMoveDisposable = selectedSquares
            .filter { it.size == 2 }
            .subscribe { selection ->
                val board = boards.value ?: return@subscribe
                val moveFrom = selection.find { board.squares[it]?.player == Player.White }
                val moveTo = selection.find { it != moveFrom }
                if (moveFrom != null && moveTo != null) {
                    chessEngine.playMove(Square(moveFrom), Square(moveTo))
                }
                selectedSquares.onNext(emptySet())
            }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
