fun main() {
    val pokemonCollection = PokemonCollection()

    for (pokemon in pokemonCollection.pokemonCards) {
        println("#${pokemon.number.toString().padStart(3, '0')} ${pokemon.name}, HP: ${pokemon.hp}, Type: ${pokemon.type}")
    }
}