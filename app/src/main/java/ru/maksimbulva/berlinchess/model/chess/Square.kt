package ru.maksimbulva.berlinchess.model.chess

// TODO: Make me inline class
class Square(val index: Int) {
    val row: Int get() = index shr 3
    val column: Int get() = index and 7
}
