package com.example.tensor_project

data class Word(
    val word: String,
    val listMeans: List<Definition>?
) {

    companion object {
        fun getWords() = listOf(
            Word("lemon", mutableListOf(Definition("noun","1)wfqwwqfqwfw; \n2)wfwfffw \n3)qfofqfo"),
                Definition("verb","1)wfqwwqfqwfw \n2)wfefwwfewfwefew q w rww ww \n3)wqwwqeqw qwrqwrqqw qwqwrqrqwqqwr"),
                Definition("verb","1)wfqwwqw \n2) q wwdwdwddwdwdwdwdwdwdwddwhtht rww ww \n3)wqwwqeqw qwrqwrqqw qwqwrqrqwqqwr"))),
            Word("apple",null),
            Word("orange", null),
            Word("wine",null),
            Word("egg",null),
            Word("milk",null),
            Word("cream",null),
            Word("cottage",null),
            Word("water",null),
            Word("meat",null),
            Word("meat",null),
            Word("meat",null),
            Word("meat",null),
            Word("meat",null),
            Word("meat",null),
            Word("meat",null),
        )
    }
}