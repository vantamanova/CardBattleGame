// Responsible for creating a Pokemon collection
import java.io.File

// Loads the Pokemon collection from the CSV file
fun loadPokemon(): List<PokemonCard> {
    val file = File("src/pokemon.csv")
    val lines = file.readLines()
    val dataLines = lines.drop(1)

    val pokemonCards = mutableListOf<PokemonCard>()

    for (line in dataLines) {
        val values = line.split(",")

        val pokemon = PokemonCard(
            number = values[0].toInt(),
            name = values[1],
            hp = values[2].toInt(),
            type = values[3],
            image = values[4]
        )

        pokemonCards.add(pokemon)
    }

    return pokemonCards
}