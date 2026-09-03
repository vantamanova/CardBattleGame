fun main() {
    val pokemonCollection = PokemonCollection()

    val deck = createDeck(pokemonCollection)
    shuffleDeck(deck)

    for (pokemon in deck) {
        println("#${pokemon.number.toString().padStart(3, '0')} ${pokemon.name}, HP: ${pokemon.hp}, Type: ${pokemon.type}")
    }
}