package com.udacity.gradle.builditbigger;

import android.os.AsyncTask;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<EndpointsAsyncTask.GotJokeCallback, Void, String> {
    private MyApi myApiService = null;
    private GotJokeCallback callback;
    private boolean errorOccurred=false;

    @Override
    protected String doInBackground(GotJokeCallback... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("https://udacitybuilditbigger-1222.appspot.com/_ah/api/")
                    //.setRootUrl("https://10.0.2.2/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        callback = params[0];

        try {
            return myApiService.getJoke().execute().getData();
        } catch (IOException e) {
            errorOccurred = true;
            return e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if (callback != null) {
            callback.done(result, errorOccurred);
        }
    }

    public interface GotJokeCallback {
        void done(String result, boolean error);
    }
}
