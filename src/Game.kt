class Game {

    val playedCards = mutableMapOf<Player, PokemonCard>()

    fun getTurnOrder(players: List<Player>, startingPlayer: Player): List<Player> {
        val startIndex = players.indexOf(startingPlayer)
        val playersFromStart = players.drop(startIndex)
        val playersBeforeStart = players.take(startIndex)
        val turnOrder = playersFromStart + playersBeforeStart

        return turnOrder
    }

    fun displayHand(player: Player) {
        println("\n${player.name}'s hand:")

        for ((index, pokemon) in player.hand.withIndex()) {
            println("${index + 1}. ${pokemon.name} - ${pokemon.type} - HP: ${pokemon.hp}")
        }
    }

    fun chooseCard(player: Player): PokemonCard {
        displayHand(player)

        while (true) {
            print("Choose a card: ")
            val choice = readln().toIntOrNull()

            // Check to make sure the correct number is entered
            if (choice == null) {
                println("Please enter a number.")
                continue
            }

            // Get correct index of the card which was picked
            val cardIndex = choice - 1

            // Check if the card actually exists
            if (cardIndex !in player.hand.indices) {
                println("Please choose a number between 1 and ${player.hand.size}.")
                continue
            }

            // Removes card from the player's hand
            val chosenCard = player.hand.removeAt(cardIndex)
            return chosenCard
        }
    }

    fun playTurn(turnOrder: List<Player>) {
        // Make sure there are no cards in the list in the beginning of the trick
        playedCards.clear()

        for (player in turnOrder) {
            // chose card
            val chosenCard = chooseCard(player)

            // store card
            playedCards[player] = chosenCard
        }
    }

    fun determineTurnWinner(): Player {
        // first played card
        val firstCard = playedCards.values.first()

        // get the lead type from that first card
        val leadType = firstCard.type

        // temporary winner
        var winningPlayer = playedCards.keys.first()
        var winningCard = firstCard

        // compare cards
        for ((player, card) in playedCards) {
            // don't compare first cart to itself
            if (card == firstCard) {
                continue
            }

            // compare 2 cards
            if (isStrongAgainst(card.type, winningCard.type)) {
                winningPlayer = player
                winningCard = card
            }
            else if (isStrongAgainst(winningCard.type, card.type)) {
                continue
            }
            else if (card.hp > winningCard.hp) {
                winningPlayer = player
                winningCard = card
            }
        }
        // Increase winner's score
        winningPlayer.score += 10
        return winningPlayer
    }

    fun isStrongAgainst(attackingType: String, defendingType: String): Boolean {
        // compare types
        val strengths = mapOf(
            "Electric" to listOf("Water"),
            "Water" to listOf("Fire", "Rock", "Ground"),
            "Fire" to listOf("Grass", "Bug"),
            "Grass" to listOf("Water", "Ground", "Rock"),
            "Ground" to listOf("Electric", "Fire", "Poison", "Rock"),
            "Rock" to listOf("Fire", "Bug"),
            "Fighting" to listOf("Normal", "Rock"),
            "Psychic" to listOf("Fighting", "Poison"),
            "Ghost" to listOf("Psychic", "Ghost"),
            "Poison" to listOf("Grass", "Fairy"),
            "Bug" to listOf("Grass", "Psychic"),
            "Fairy" to listOf("Fighting")
        )

        // return T or F
        return defendingType in (strengths[attackingType] ?: emptyList())
    }
}