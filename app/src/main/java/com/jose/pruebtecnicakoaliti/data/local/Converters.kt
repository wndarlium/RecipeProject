package com.jose.pruebtecnicakoaliti.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jose.pruebtecnicakoaliti.data.model.ingredient.Ingredient

class Converters {
    @TypeConverter
    fun fromIngredientList(value: List<Ingredient>?): String? {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toIngredientList(value: String?): List<Ingredient>? {
        if (value == null) return null
        val listType = object : TypeToken<List<Ingredient>>() {}.type
        return Gson().fromJson(value, listType)
    }
}
