fun main() {
    // create players
    val players = createPlayers(
        listOf("Vera", "Daniil", "Sofia")
    )

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

    // Check for choosing card
    val chosenCard = game.chooseCard(turnOrder[0])
    println("You chose: ${chosenCard.name}")
}