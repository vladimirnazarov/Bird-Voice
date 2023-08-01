package by.ssrlab.birdvoice.db.objects

data class RecognizedBird(
    val image: String,
    val name: String,
    val startTime: String,
    val endTime: String
)
