package com.example.retailpro.data.local.entity

import androidx.room.TypeConverter
import com.example.retailpro.data.remote.model.Rating
import com.google.gson.Gson


class RatingConverter {

    @TypeConverter
    fun fromRating(rating: Rating): String {
        return Gson().toJson(rating)
    }

    @TypeConverter
    fun toRating(ratingString: String): Rating {
        return Gson().fromJson(ratingString, Rating::class.java)
    }
}