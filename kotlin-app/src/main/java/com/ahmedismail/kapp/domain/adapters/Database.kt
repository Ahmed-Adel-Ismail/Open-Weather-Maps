package com.ahmedismail.kapp.domain.adapters

import android.content.Context
import androidx.room.*
import com.ahmedismail.kapp.entities.City
import com.ahmedismail.kapp.entities.Coordinates
import com.ahmedismail.kapp.entities.FavoriteCityId
import com.google.gson.Gson
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

private const val DATABASE_NAME = "DatabaseGateway.db"

@Database(entities = [(City::class), (FavoriteCityId::class)], version = 1, exportSchema = false)
@TypeConverters(CoordinatesConverter::class)
abstract class DatabaseAdapter : RoomDatabase() {
    abstract val citiesTable: CitiesTable
    abstract val favoriteCityIdsTable: FavoriteCityIdsTable
}

suspend fun databaseAdapter(context: Context) =
        copyDatabaseFromAssets(context)
                .let { Room.databaseBuilder(context, DatabaseAdapter::class.java, DATABASE_NAME) }
                .build()


private suspend fun copyDatabaseFromAssets(context: Context) =
        context.getDatabasePath(DATABASE_NAME)
                ?.takeUnless { it.exists() }
                ?.let { copy(context, it) }


private suspend fun copy(context: Context, databaseFile: File) {
    withContext(CommonPool) {
        databaseFile.parentFile.mkdirs()
        context.assets.open(DATABASE_NAME)
                .use { copyByteArray(databaseFile, it) }
    }
}

private fun copyByteArray(databaseFile: File, assetsInputStream: InputStream) {
    FileOutputStream(databaseFile)
            .use { it.write(byteArray(assetsInputStream)) }
}

private fun byteArray(assetsFileInputStream: InputStream) =
        ByteArray(assetsFileInputStream.available())
                .also { assetsFileInputStream.read(it) }

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
    fun fromCoordinates(coordinates: Coordinates) = Gson().toJson(coordinates)!!


    @TypeConverter
    fun fromString(value: String) = Gson().fromJson<Coordinates>(value, Coordinates::class.java)!!
}

