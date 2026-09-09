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

    fun chooseCard(player: Player, leadType: String? = null): PokemonCard {
        displayHand(player)

        // Check if the player has leading type pokemon
        val hasLeadType = leadType != null && player.hand.any { it.type == leadType }

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

            // Check if the card is legal move
            val selectedCard = player.hand[cardIndex]
            if (hasLeadType && selectedCard.type != leadType) {
                println("You must play a $leadType card.")
                continue
            }

            // Removes card from the player's hand
            player.hand.removeAt(cardIndex)
            return selectedCard
        }
    }

    fun playTurn(turnOrder: List<Player>) {
        // Make sure there are no cards in the list in the beginning of the trick
        playedCards.clear()
        var leadType: String? = null

        for (player in turnOrder) {
            // chose card
            val chosenCard = chooseCard(player, leadType)

            // First player can pick any card and establish the type
            if (leadType == null) {
                leadType = chosenCard.type
            }

            // store card
            playedCards[player] = chosenCard
        }

        // Display cards in play
        println("\nCards in play:")

        for ((player, card) in playedCards) {
            println("${player.name}: ${card.name} - ${card.type} - HP: ${card.hp}")
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
            // don't compare first card to itself
            if (card == firstCard) {
                continue
            }

            // Check if the new card is strong against the lead type
            val cardBeatsLead = isStrongAgainst(card.type, leadType)

            // Check if the current winning card is strong against the lead type
            val winningCardBeatsLead = isStrongAgainst(winningCard.type, leadType)

            // A card that is strong against the lead type beats a lead-type card
            if (cardBeatsLead && !winningCardBeatsLead) {
                winningPlayer = player
                winningCard = card
            }

            // If both cards are strong against the lead type, higher HP wins
            else if (cardBeatsLead && card.hp > winningCard.hp) {
                winningPlayer = player
                winningCard = card
            }

            // If neither card beats the lead type, only lead-type cards can compete by HP
            else if (
                !cardBeatsLead &&
                !winningCardBeatsLead &&
                card.type == leadType &&
                winningCard.type == leadType &&
                card.hp > winningCard.hp
            ) {
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

    fun determineGameWinner(players: List<Player>): List<Player> {
        // finds highest score among all
        val highestScore = players.maxOf { it.score }

        // finds the player/players with that score
        val winners = players.filter { it.score == highestScore }

        return winners

    }

    fun displayGameResult(winners: List<Player>) {
        // Only one winner
        if (winners.size == 1) {
            val winner = winners[0]
            println("\n${winner.name} wins the game with ${winner.score} points!")
        }

        // more winners
        else {
            println("\nIt's a tie!")
            for (winner in winners) {
                println("${winner.name} - ${winner.score} points")
            }
        }
    }

    fun waitForEnter() {
        print("\nPress Enter to continue...")
        readln()
    }
}