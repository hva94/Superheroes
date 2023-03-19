package com.hvasoft.superheroes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Superhero(
    val appearance: @RawValue Appearance,
    val biography: @RawValue Biography,
    val connections: @RawValue Connections,
    val id: String,
    val image: @RawValue Image,
    val name: String,
    val powerstats: @RawValue Powerstats,
    val response: String,
    val work: @RawValue Work
) : Parcelable