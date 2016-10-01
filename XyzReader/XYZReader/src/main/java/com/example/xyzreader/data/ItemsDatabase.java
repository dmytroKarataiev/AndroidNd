/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.xyzreader.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.xyzreader.data.ItemsProvider.Tables;

public class ItemsDatabase extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "xyzreader.db";
    private static final int DATABASE_VERSION = 1;

    public ItemsDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.ITEMS + " ("
                + ItemsContract.ItemsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ItemsContract.ItemsColumns.SERVER_ID + " TEXT,"
                + ItemsContract.ItemsColumns.TITLE + " TEXT NOT NULL,"
                + ItemsContract.ItemsColumns.AUTHOR + " TEXT NOT NULL,"
                + ItemsContract.ItemsColumns.BODY + " TEXT NOT NULL,"
                + ItemsContract.ItemsColumns.THUMB_URL + " TEXT NOT NULL,"
                + ItemsContract.ItemsColumns.PHOTO_URL + " TEXT NOT NULL,"
                + ItemsContract.ItemsColumns.ASPECT_RATIO + " REAL NOT NULL DEFAULT 1.5,"
                + ItemsContract.ItemsColumns.PUBLISHED_DATE + " INTEGER NOT NULL DEFAULT 0"
                + ")" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Tables.ITEMS);
        onCreate(db);
    }
}
