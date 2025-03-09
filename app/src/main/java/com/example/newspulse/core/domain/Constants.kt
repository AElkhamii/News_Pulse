package com.example.newspulse.core.domain

class Constants {
    companion object {
        /* API key that you got when you created account on NewsAPI */
        const val API_KEY = "3ea6216b85134b17b091fee7435e545d"
        /* URL for the news */
        const val BASE_URL = "https://newsapi.org"

        const val FIRST_BREAKING_NEWS_PAGE = 1

         val NEWS_CATEGORIES = mapOf(
             "General" to "general",
             "Business" to "business",
             "Entertainment" to "entertainment",
             "Health" to "health",
             "Science" to "science",
             "Sports" to "sports",
             "Technology" to "technology"
        )
        val NEWS_COUNTRY = mapOf(
             "United State" to "us",
             "Egypt" to "eg"
        )

        val NEWS_PER_PAGE = mapOf(
            "20" to "20",
            "30" to "30",
            "40" to "40",
            "50" to "50",
            "60" to "60",
            "70" to "70",
            "80" to "80",
            "90" to "90",
            "100" to "100"
        )
    }
}