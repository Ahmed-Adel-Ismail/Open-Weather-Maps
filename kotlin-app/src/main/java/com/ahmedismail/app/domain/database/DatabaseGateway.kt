package com.ahmedismail.app.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahmedismail.app.entities.City
import com.ahmedismail.app.entities.FavoriteCityId
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

private const val DATABASE_NAME = "DatabaseGateway.db"

@Database(entities = [(City::class), (FavoriteCityId::class)], version = 1, exportSchema = false)
@TypeConverters(CoordinatesConverter::class)
abstract class DatabaseGateway : RoomDatabase() {

    abstract val citiesTable: CitiesTable

    abstract val favoriteCityIdsTable: FavoriteCityIdsTable

}

suspend fun database(context: Context) =
        copyDatabaseFromAssets(context)
                .let { Room.databaseBuilder(context, DatabaseGateway::class.java, DATABASE_NAME) }
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




