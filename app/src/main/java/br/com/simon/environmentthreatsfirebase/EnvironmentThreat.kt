package br.com.simon.environmentthreatsfirebase

import java.io.Serializable

data class EnvironmentThreat(
    val id: Long? = 0,
    val address: String? = "",
    val date: String? = "",
    val description: String? = "",
    val image: String? = ""
) : Serializable