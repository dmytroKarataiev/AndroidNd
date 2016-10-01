/*
 * MIT License
 *
 * Copyright (c) 2016. Dmytro Karataiev
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

package com.adkdevelopment.jokesactivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * A placeholder fragment containing a simple view.
 */
public class JokesActivityFragment extends Fragment {

    public JokesActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_jokes, container, false);
        tellJoke(root);

        return root;
    }

    /**
     * Method to update UI, if flavor is paid -> download comics
     * @param view on which image and text should be updated
     */
    public void tellJoke(View view) {
        String joke = getActivity().getIntent().getStringExtra(JokesActivity.JOKE);
        if (joke.contains("xkcd")) {
            FetchJSON fetchJSON = new FetchJSON(view, joke);
            fetchJSON.execute();
        } else {
            TextView textView = (TextView) view.findViewById(R.id.joke_textview);
            textView.setText(joke);
        }
    }

    /**
     * Class to retreive JSON on background thread
     */
    public class FetchJSON extends AsyncTask<Void, Void, String[]> {

        private View view;
        private String link;

        private final String LOG_TAG = FetchJSON.class.getSimpleName();

        public FetchJSON(View view, String link) {
            this.view = view;
            this.link = link;
        }

        /**
         * AsyncTask to fetch data on background thread
         *
         * @param params receives request link from Utility class
         * @return String[] with link to the image and a title
         */
        protected String[] doInBackground(Void... params) {
            // Network Client
            OkHttpClient client = new OkHttpClient();

            // Will contain the raw JSON response as a string.
            String jokeJson = "";

            try {
                URL url = new URL(link);

                Request request = new Request.Builder()
                        .url(url)
                        .build();
                Response responses = client.newCall(request).execute();

                jokeJson = responses.body().string();
                responses.body().close();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
            } catch (NullPointerException e) {
                Log.e(LOG_TAG, "Null ", e);
            }

            return getJokesInfo(jokeJson);
        }

        @Override
        protected void onPostExecute(String[] output) {
            Picasso.with(getActivity())
                    .load(output[1])
                    .into((ImageView) view.findViewById(R.id.joke_imageview));
            ((TextView) view.findViewById(R.id.joke_textview)).setText(output[0]);
        }
    }

    /**
     * Method to parse JSON string and return String[] with a link and a title
     *
     * @param jokesJson JSON string with info about comics, link, etc.
     * @return String[] with a link and a title
     */
    public static String[] getJokesInfo(String jokesJson) {

        String[] reviews = new String[2];

        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse(jokesJson);

        if (element.isJsonObject()) {
            JsonObject results = element.getAsJsonObject();
            JsonElement title = results.getAsJsonPrimitive("title");
            JsonElement linkToImage = results.getAsJsonPrimitive("img");

            reviews[0] = title.getAsString();
            reviews[1] = linkToImage.getAsString();

            return reviews;
        }

        return null;
    }
}
