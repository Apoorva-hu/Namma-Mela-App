package com.example.nammamela.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class ChatMessage(
    val text: String,
    val isUser: Boolean
)

class AIHelperViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(
        listOf(ChatMessage("Namaskara! 🙏 I'm your Namma-Mela assistant. Ask me about events, seats, or cast members!", isUser = false))
    )
    val messages: StateFlow<List<ChatMessage>> = _messages

    fun sendMessage(userMessage: String) {
        val current = _messages.value.toMutableList()
        current.add(ChatMessage(userMessage, isUser = true))
        current.add(ChatMessage(generateResponse(userMessage), isUser = false))
        _messages.value = current
    }

    private fun generateResponse(input: String): String {
        val msg = input.lowercase().trim()
        return when {
            msg.contains("family") || msg.contains("children") || msg.contains("kids") ->
                "🎭 For families, I recommend **Village Festival Dance** (June 1) — colorful folk dances that kids love! **Ramayana: Sita's Quest** is also great for all ages with its timeless story."

            msg.contains("mahabharata") ->
                "⚔️ **Mahabharata: The Great War** is on May 20, 2025 at 7:00 PM at Ravindra Kalakshetra, Bengaluru. Duration: 2h 30m. It features Ravi Kumar as Arjuna, Anjali Devi as Draupadi, and Vikram Singh as Karna."

            msg.contains("ramayana") ->
                "🏹 **Ramayana: Sita's Quest** is on May 25, 2025 at 6:30 PM at Chowdiah Memorial Hall. Duration: 2h. Features Deepak Sharma as Rama and Priya Nair as Sita — a must-watch!"

            msg.contains("yakshagana") ->
                "🎨 **Yakshagana Night** is on June 8, 2025 at 8:00 PM at Kalamandira, Mysuru. It's the longest event at 3 hours — arrive early! Features elaborate costumes and live percussion."

            msg.contains("village") || msg.contains("festival") || msg.contains("folk") ->
                "🪘 **Village Festival Dance** is on June 1, 2025 at 5:00 PM at Freedom Park Open Air Stage. Duration: 1h 45m. Celebrate rural Karnataka's folk traditions!"

            msg.contains("historical") || msg.contains("history") ->
                "🏰 **Historical Drama Night** is on June 15, 2025 at 7:30 PM at Sri Jayachamarajendra Auditorium, Mysuru. Learn about the Vijayanagara empire and Rani Abbakka!"

            msg.contains("seat") || msg.contains("ticket") || msg.contains("book") || msg.contains("reserv") ->
                "🎟️ You can reserve seats from the **event details screen** → tap 'Reserve Seats'. Seats are shown in a grid — green means available, red means reserved. Tap any green seat to book it!"

            msg.contains("cast") || msg.contains("actor") || msg.contains("performer") ->
                "🎭 Each event has its own cast. Open any event card on the home screen and tap **'View Cast'** to see all performers with their roles and photos!"

            msg.contains("best") || msg.contains("popular") || msg.contains("recommend") || msg.contains("suggest") ->
                "⭐ Our most popular event is **Mahabharata: The Great War** — it always sells out! For something unique, try **Yakshagana Night** — a rare traditional art form. For families, **Village Festival Dance** is perfect!"

            msg.contains("date") || msg.contains("when") || msg.contains("schedule") ->
                "📅 Upcoming events:\n• Mahabharata — May 20\n• Ramayana — May 25\n• Village Festival — June 1\n• Yakshagana — June 8\n• Historical Drama — June 15"

            msg.contains("venue") || msg.contains("where") || msg.contains("location") || msg.contains("place") ->
                "📍 Venues:\n• Mahabharata → Ravindra Kalakshetra, Bengaluru\n• Ramayana → Chowdiah Memorial Hall\n• Village Festival → Freedom Park\n• Yakshagana → Kalamandira, Mysuru\n• Historical → SJA Auditorium, Mysuru"

            msg.contains("hello") || msg.contains("hi") || msg.contains("namaste") || msg.contains("hey") ->
                "Namaskara! 🙏 Welcome to Namma-Mela! I can help you with event info, seat booking, cast details or recommendations. What would you like to know?"

            msg.contains("thank") || msg.contains("thanks") ->
                "You're welcome! 😊 Enjoy the show and have a wonderful time at Namma-Mela! 🎭"

            msg.contains("fan wall") || msg.contains("comment") || msg.contains("review") ->
                "💬 You can share your thoughts on the **Fan Wall** section! Open any event → tap 'Fan Wall' to read and post comments about the performance."

            else ->
                "🤔 I'm not sure about that. You can ask me about:\n• Specific events (Mahabharata, Ramayana, etc.)\n• Seat booking\n• Cast members\n• Event dates & venues\n• Recommendations for families"
        }
    }
}
