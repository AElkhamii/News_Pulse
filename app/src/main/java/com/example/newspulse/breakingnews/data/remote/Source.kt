package com.example.newspulse.breakingnews.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class Source(
    val id: String?,
    val name: String
)

