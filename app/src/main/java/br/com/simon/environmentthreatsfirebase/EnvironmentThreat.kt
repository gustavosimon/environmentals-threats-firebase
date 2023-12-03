package br.com.simon.environmentthreatsfirebase

import java.io.Serializable

data class EnvironmentThreat(
    val address: String? = "",
    val date: String? = "",
    val description: String? = "",
    val image: String? = ""
) : Serializable