package com.example.tensor_project.testWord

data class WordTest(
    val word: String,
    val listMeans: List<DefinitionTest>?
) {

    companion object {
        fun getWords() = listOf(
            WordTest("lemon", mutableListOf(DefinitionTest("noun","1)wfqwwqfqwfw; \n2)wfwfffw \n3)qfofqfo"),
                DefinitionTest("verb","1)wfqwwqfqwfw \n2)wfefwwfewfwefew q w rww ww \n3)wqwwqeqw qwrqwrqqw qwqwrqrqwqqwr"),
                DefinitionTest("verb","1)wfqwwqw \n2) q wwdwdwddwdwdwdwdwdwdwddwhtht rww ww \n3)wqwwqeqw qwrqwrqqw qwqwrqrqwqqwr"),
                DefinitionTest("pronoun","1)wfqwwqw \n2) q wwdwdwddwdwdwdwdwdwdw \n3)wqwwqeqw qwrqwrqqw qwqwrqrqwqqwr"))),
            WordTest("apple",null),
            WordTest("orange", null),
            WordTest("wine",null),
            WordTest("egg",null),
            WordTest("milk",null),
            WordTest("cream",null),
            WordTest("cottage",null),
            WordTest("water",null),
            WordTest("meat",null),
            WordTest("meat",null),
            WordTest("meat",null),
            WordTest("meat",null),
            WordTest("meat",null),
            WordTest("meat",null),
            WordTest("meat",null),
        )
    }
}