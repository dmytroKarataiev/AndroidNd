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

import android.net.Uri;

public class ItemsContract {
	public static final String CONTENT_AUTHORITY = "com.example.xyzreader";
	public static final Uri BASE_URI = Uri.parse("content://com.example.xyzreader");

	interface ItemsColumns {
		/** Type: INTEGER PRIMARY KEY AUTOINCREMENT */
		String _ID = "_id";
		/** Type: TEXT */
		String SERVER_ID = "server_id";
		/** Type: TEXT NOT NULL */
		String TITLE = "title";
		/** Type: TEXT NOT NULL */
		String AUTHOR = "author";
		/** Type: TEXT NOT NULL */
		String BODY = "body";
        /** Type: TEXT NOT NULL */
        String THUMB_URL = "thumb_url";
		/** Type: TEXT NOT NULL */
		String PHOTO_URL = "photo_url";
		/** Type: REAL NOT NULL DEFAULT 1.5 */
		String ASPECT_RATIO = "aspect_ratio";
		/** Type: INTEGER NOT NULL DEFAULT 0 */
		String PUBLISHED_DATE = "published_date";
	}

	public static class Items implements ItemsColumns {
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.xyzreader.items";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.xyzreader.items";

        public static final String DEFAULT_SORT = PUBLISHED_DATE + " DESC";

		/** Matches: /items/ */
		public static Uri buildDirUri() {
			return BASE_URI.buildUpon().appendPath("items").build();
		}

		/** Matches: /items/[_id]/ */
		public static Uri buildItemUri(long _id) {
			return BASE_URI.buildUpon().appendPath("items").appendPath(Long.toString(_id)).build();
		}

        /** Read item ID item detail URI. */
        public static long getItemId(Uri itemUri) {
            return Long.parseLong(itemUri.getPathSegments().get(1));
        }
	}

	private ItemsContract() {
	}
}
