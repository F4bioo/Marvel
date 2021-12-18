package com.fappslab.marvel.framework.network.response

data class CharacterResponse(
    val id: String,
    val name: String,
    val thumbnail: ThumbnailResponse
)