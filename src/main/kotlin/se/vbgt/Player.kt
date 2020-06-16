package se.vbgt

data class Player(val name: String)

enum class PlayerChoice {
    PLAYER_1,
    PLAYER_2;

    fun nextPlayer(): PlayerChoice =
        when (this) {
            PLAYER_1 -> PLAYER_2
            PLAYER_2 -> PLAYER_1
        }
}