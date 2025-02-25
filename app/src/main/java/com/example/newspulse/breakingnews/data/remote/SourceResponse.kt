package com.example.newspulse.breakingnews.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SourceResponse(
    val id: String?,
    val name: String
)

