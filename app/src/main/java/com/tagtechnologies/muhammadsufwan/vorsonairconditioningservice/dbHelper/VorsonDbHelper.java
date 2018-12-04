/*
 * Copyright (C) 2014 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.dbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import com.tagtechnologies.muhammadsufwan.vorsonairconditioningservice.adaptersAndModules.Client;

import java.util.ArrayList;
import java.util.List;

/**
 * A helper class to set up the database that holds the GPS location information
 */
public class VorsonDbHelper extends SQLiteOpenHelper {

    private static final String TAG = "vorsonDbHelper";

    public static final String TABLE_NAME = "user";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_F_NAME = "f_name";
    public static final String COLUMN_L_NAME = "l_name";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_NUMBER = "number";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_GENDER = "gender";
    public static final String COLUMN_DOB = "dob";
    public static final String COLUMN_IMAGE = "image";
    public static final String COLUMN_CREATED_AT = "created_at";
    public static final String COLUMN_DEVICE_F_TOKEN = "f_token";
    public static final String COLUMN_STATUS = "status";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
//    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER ,"
                    + COLUMN_F_NAME + TEXT_TYPE + COMMA_SEP
                    + COLUMN_L_NAME + TEXT_TYPE + COMMA_SEP
                    + COLUMN_EMAIL + TEXT_TYPE + COMMA_SEP
                    + COLUMN_NUMBER + TEXT_TYPE + COMMA_SEP
                    + COLUMN_ADDRESS + TEXT_TYPE + COMMA_SEP
                    + COLUMN_GENDER + TEXT_TYPE + COMMA_SEP
                    + COLUMN_DOB + TEXT_TYPE + COMMA_SEP
                    + COLUMN_IMAGE + TEXT_TYPE + COMMA_SEP
                    + COLUMN_CREATED_AT + TEXT_TYPE + COMMA_SEP
                    + COLUMN_DEVICE_F_TOKEN + TEXT_TYPE + COMMA_SEP
                    + COLUMN_STATUS + INTEGER_TYPE
                    + " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "vorson.db";

    public VorsonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }


    public final long insert(Client entry) {
        if (Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, "Inserting data of login user");
        }
        // Gets the data repository in write mode
        SQLiteDatabase db = getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, entry.getId());
        values.put(COLUMN_F_NAME, entry.getName());
        values.put(COLUMN_L_NAME, entry.getName());
        values.put(COLUMN_EMAIL, entry.getEmail());
        values.put(COLUMN_NUMBER, entry.getNumber());
        values.put(COLUMN_ADDRESS, entry.getAddress());
        values.put(COLUMN_STATUS, entry.getStatus());

        // Insert the new row, returning the primary key value of the new row
        return db.insert(TABLE_NAME, "null", values);
    }
    
//    public final List<LocationEntry> read(Calendar calendar) {
//        SQLiteDatabase db = getReadableDatabase();
//        String[] projection = {
//                COLUMN_NAME_LONGITUDE,
//                COLUMN_NAME_LATITUDE,
//                COLUMN_NAME_TIME
//        };
//        String day = Utils.getHashedDay(calendar);
//
//        // sort ASC based on the time of the entry
//        String sortOrder = COLUMN_NAME_TIME + " ASC";
//        String selection = COLUMN_NAME_DAY + " LIKE ?";
//
//        Cursor cursor = db.query(
//                TABLE_NAME,                 // The table to query
//                projection,                 // The columns to return
//                selection,                  // The columns for the WHERE clause
//                new String[]{day},          // The values for the WHERE clause
//                null,                       // don't group the rows
//                null,                       // don't filter by row groups
//                sortOrder                   // The sort order
//        );
//
//        List<LocationEntry> result = new ArrayList<>();
//        int count = cursor.getCount();
//        if (count > 0) {
//            cursor.moveToFirst();
//            while (!cursor.isAfterLast()) {
//                Calendar cal = Calendar.getInstance();
//                cal.setTimeInMillis(cursor.getLong(2));
//                LocationEntry entry = new LocationEntry(cal, cursor.getDouble(1),
//                        cursor.getDouble(0));
//                result.add(entry);
//                cursor.moveToNext();
//            }
//        }
//        cursor.close();
//        return result;
//    }



}
