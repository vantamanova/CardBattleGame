fun main() {
    // create players
    val players = createPlayers(
        listOf("Vera", "Daniil", "Sofia")
    )

    // Create collection and deck
    val pokemonCollection = loadPokemon()
    val deck = createDeck(pokemonCollection)
    shuffleDeck(deck)

    // deal cards
    dealCards(deck, players)

    // Just to check what is going on
    for (player in players) {
        println("\n${player.name}'s hand:")

        for (pokemon in player.hand) {
            println(
                "#${pokemon.number.toString().padStart(3, '0')} " +
                        "${pokemon.name}, HP: ${pokemon.hp}, Type: ${pokemon.type}"
            )
        }
    }

    // Another temporary check
    println("\nCards remaining in deck: ${deck.size}")
}