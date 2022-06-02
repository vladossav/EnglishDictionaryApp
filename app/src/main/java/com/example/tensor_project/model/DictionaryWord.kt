package com.example.tensor_project.model

import com.squareup.moshi.Json

data class WordItem(
    @Json(name = "meanings")
    var meanings: List<Meaning> = listOf(),
    @Json(name = "phonetic")
    var phonetic: String = "",
    @Json(name = "word")
    var word: String = "",
) {
    data class Meaning(
        @Json(name = "definitions")
        var definitions: List<Definition> = listOf(),
        @Json(name = "partOfSpeech")
        var partOfSpeech: String = "",
    ) {
        data class Definition(
            @Json(name = "antonyms")
            var antonyms: List<String> = listOf(),
            @Json(name = "definition")
            var definition: String = "",
            @Json(name = "example")
            var example: String = "",
            @Json(name = "synonyms")
            var synonyms: List<String> = listOf(),
        )
    }
}
