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
    val startingPlayer = chooseStartingPlayer(players)
    println("${startingPlayer.name} will play first!")

    // The order of players
    val turnOrder = game.getTurnOrder(players, startingPlayer)

    // Play turn
    game.playTurn(turnOrder)

    // Check Played cards
    println("\nPlayed cards:")
    for ((player, card) in game.playedCards) {
        println("${player.name} played ${card.name} - ${card.type} - HP: ${card.hp}")
    }

    // Determine the winner
    val turnWinner = game.determineTurnWinner()
    println("\n${turnWinner.name} wins the turn!")

    //
    val nextTurnOrder = game.getTurnOrder(players, turnWinner)
    game.playTurn(nextTurnOrder)

    val secondTurnWinner = game.determineTurnWinner()
    println("\n${secondTurnWinner.name} wins the turn!")
}