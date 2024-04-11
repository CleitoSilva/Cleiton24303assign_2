package com.student24304.simplenavigation.models

import androidx.annotation.DrawableRes
import kotlin.random.Random

/**
 * The @DrawableRes annotation on the bannerResId
 * parameter Denotes that an integer parameter,
 * field or method return value is expected to be a drawable resource reference
 */
data class Movie(
    val id: Int,
    val title: String,
    @DrawableRes val posterResId: Int,
    val starring: String,
    val description: String,
    val runningTime: String,
    val maxSeats: Int = (0..15).random(), //generates maximum random seats from 1 to 15
    val height: Int =  Random.nextInt(210, 340),
    val imgHeight: Int = height - 30
)
