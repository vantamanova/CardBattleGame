fun main() {
    // create players
    val players = createPlayers(
        listOf("Vera", "Daniil", "Sofia")
    )

    // Create Game
    val game = Game()

    // Create collection and deck
    val pokemonCollection = loadPokemon()
    val deck = createDeck(pokemonCollection)
    shuffleDeck(deck)

    // deal cards
    dealCards(deck, players)

    // Choose starting player
    var startingPlayer = chooseStartingPlayer(players)
    println("${startingPlayer.name} will play first!")

    // The order of players
    var turnOrder = game.getTurnOrder(players, startingPlayer)

    // keep playing till first player's hand is not empty
    while (players[0].hand.isNotEmpty()) {
        // Play turn
        game.playTurn(turnOrder)

        // Determine the winner
        val turnWinner = game.determineTurnWinner()
        println("\n${turnWinner.name} wins the turn! Score: ${turnWinner.score}")

        // Next first player is this trick winner
        startingPlayer = turnWinner

        // Next trick new order
        turnOrder = game.getTurnOrder(players, startingPlayer)
    }
}