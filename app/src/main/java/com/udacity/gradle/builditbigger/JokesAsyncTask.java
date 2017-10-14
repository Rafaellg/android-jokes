package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.rafae.myapplication.backend.myApi.MyApi;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.rafaelguimas.androidjokeslibrary.JokeActivity;

import java.io.IOException;

/**
 * Created by rafae on 12/10/2017.
 */

public class JokesAsyncTask extends AsyncTask<Void, Void, String> {

    private static MyApi myApiService = null;
    private Context mContext;
    private ProgressBar mProgressBar;

    public JokesAsyncTask(@Nullable Context context, @Nullable ProgressBar mProgressBar) {
        this.mContext = context;
        this.mProgressBar = mProgressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.i(JokesAsyncTask.class.getSimpleName(), "Joke received: " + result);

        if (mProgressBar != null) {
            mProgressBar.setVisibility(View.GONE);
        }

        if (result != null) {
            Intent intent = new Intent(mContext, JokeActivity.class);
            intent.putExtra(JokeActivity.EXTRA_JOKE, result);
            mContext.startActivity(intent);
        } else if (mContext != null) {
            Toast.makeText(mContext, R.string.error_server_offline, Toast.LENGTH_LONG).show();
        }
    }
}
