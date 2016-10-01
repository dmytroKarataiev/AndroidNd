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

package com.udacity.gradle.builditbigger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.adkdevelopment.jokesactivity.JokesActivity;
import com.example.karataev.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.io.IOException;

/**
 * Created by karataev on 3/3/16.
 */
public class FetchJokesEndpoint extends AsyncTask<Activity, Void, String> {
    private MyApi myApiService = null;
    private Context activity;

    // variables for android test
    private GetTaskListener mListener = null;
    private Exception mError = null;


    @Override
    protected String doInBackground(Activity... params) {
        if(myApiService == null) {
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null)
                    .setRootUrl("https://builditbigger-1238.appspot.com/_ah/api/");

            // Uncomment following lines to test backend on local machine
//                    .setRootUrl("http://192.168.0.6:8080/_ah/api/")
//                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
//                        @Override
//                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
//                            abstractGoogleClientRequest.setDisableGZipContent(true);
//                        }
//                    });

            myApiService = builder.build();
        }

        if (params.length > 0) {
            activity = params[0];
        }

        try {

            if (activity != null && activity.getPackageName().contains("paid")) {
                return myApiService.getJoke().execute().getData();
            } else {
                return myApiService.getFreeJoke().execute().getData();
            }
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (this.mListener != null) {
            this.mListener.onComplete(result, mError);
        }

        if (activity != null) {
            Toast.makeText(activity, result, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(activity, JokesActivity.class);
            intent.putExtra("joke", result);
            activity.startActivity(intent);
        }

    }

    public interface GetTaskListener {
        public void onComplete(String result, Exception e);
    }

    public FetchJokesEndpoint setListener(GetTaskListener listener) {
        this.mListener = listener;
        return this;
    }

    @Override
    protected void onCancelled() {
        if (this.mListener != null) {
            mError = new InterruptedException("AsyncTask cancelled");
            this.mListener.onComplete(null, mError);
        }
    }
}
