package com.reactive.owm.domain.database;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class DatabaseGatewayInitializer implements Function<Context, Single<DatabaseGateway>> {

    private static final String DATABASE_NAME = "DatabaseGateway.db";

    @Override
    public Single<DatabaseGateway> apply(Context context) {
        return Single.fromCallable(() -> copyDatabaseFromAssets(context))
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map(ignoredResult -> Room.databaseBuilder(context, DatabaseGateway.class, DATABASE_NAME))
                .map(RoomDatabase.Builder::build);
    }

    private boolean copyDatabaseFromAssets(Context context) throws IOException {
        File databaseFile = context.getDatabasePath(DATABASE_NAME);
        if (!databaseFile.exists()) {
            copy(context, databaseFile);
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void copy(Context context, File databaseFile) throws IOException {
        databaseFile.getParentFile().mkdirs();
        InputStream assetsFileStream = null;
        FileOutputStream databaseFileStream = null;
        try {
            assetsFileStream = context.getAssets().open(DATABASE_NAME);
            databaseFileStream = new FileOutputStream(databaseFile);
            databaseFileStream.write(assetsFile(assetsFileStream));
        } finally {
            closeStreams(assetsFileStream, databaseFileStream);
        }
    }

    private byte[] assetsFile(InputStream assetsFileInputStream) throws IOException {
        int size = assetsFileInputStream.available();
        byte[] buffer = new byte[size];
        assetsFileInputStream.read(buffer);
        return buffer;
    }

    private void closeStreams(InputStream assetsFileStream, FileOutputStream databaseFileStream) {
        if (assetsFileStream != null) {
            closeAssetsFileStream(assetsFileStream);
        }

        if (databaseFileStream != null) {
            closeDatabaseFileStream(databaseFileStream);
        }
    }

    private void closeAssetsFileStream(InputStream assetsFileStream) {
        try {
            assetsFileStream.close();
        } catch (IOException e) {
            Log.e("DatabaseGateway", "failed to close assetsFileStream", e);
        }
    }

    private void closeDatabaseFileStream(FileOutputStream databaseFileStream) {
        try {
            databaseFileStream.close();
        } catch (IOException e) {
            Log.e("DatabaseGateway", "failed to close databaseFileStream", e);
        }
    }


}
