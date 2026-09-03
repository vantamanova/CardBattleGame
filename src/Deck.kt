// Functions responsible for creating, shuffling, and dealing the game deck
fun createDeck(pokemonCollection: List<PokemonCard>): MutableList<PokemonCard> {
    val deck = pokemonCollection.toMutableList()

    return deck
}

fun shuffleDeck(deck: MutableList<PokemonCard>) {
    deck.shuffle()
}

fun dealCards(
    deck: MutableList<PokemonCard>,
    players: List<Player>,
    cardsPerPlayer: Int = 5
) {
    repeat(cardsPerPlayer) {
        for (player in players) {
            val card = deck.removeAt(0)
            player.hand.add(card)
        }
    }
}