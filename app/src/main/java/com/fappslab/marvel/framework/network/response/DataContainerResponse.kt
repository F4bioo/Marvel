package com.fappslab.marvel.framework.network.response

data class DataContainerResponse(
    val offset: Int,
    val total: Int,
    val results: List<CharacterResponse>
)
