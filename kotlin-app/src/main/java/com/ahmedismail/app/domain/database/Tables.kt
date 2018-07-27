package com.ahmedismail.app.domain.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.TypeConverter
import com.ahmedismail.app.entities.City
import com.ahmedismail.app.entities.Coordinates
import com.ahmedismail.app.entities.FavoriteCityId
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface CitiesTable {

    @Query("select * from City where City.name like :fuzzyName")
    fun queryCityByName(fuzzyName: String): List<City>

    @Query("select count(*) from City ")
    fun queryCitiesCount(): Single<Int>

    @Query("select * from City where City.id in (:ids)")
    fun queryCitiesByIds(ids: List<Long>): Flowable<List<City>>

}


@Dao
interface FavoriteCityIdsTable {

    @Query("select * from FavoriteCityId")
    fun queryFavoriteCityIds(): Flowable<List<FavoriteCityId>>

    @Insert
    fun insertFavoriteCityId(favoriteCityId: FavoriteCityId)

}

class CoordinatesConverter {

    @TypeConverter
    fun fromCoordinates(coordinates: Coordinates): String {
        return Gson().toJson(coordinates)
    }

    @TypeConverter
    fun fromString(value: String): Coordinates {
        return Gson().fromJson<Coordinates>(value, Coordinates::class.java)
    }


}

