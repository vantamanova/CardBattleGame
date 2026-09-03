// Functions responsible for creating, shuffling, and dealing the game deck
fun createDeck(pokemonCollection: PokemonCollection): MutableList<PokemonCard> {
    val deck = pokemonCollection.pokemonCards.toMutableList()

    return deck
}

fun shuffleDeck(deck: MutableList<PokemonCard>) {
    deck.shuffle()
}