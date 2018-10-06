package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.builditbigger.backend.myApi.MyApi;

import java.io.IOException;

public class EndpointsAsyncTask extends AsyncTask<Context, Void, Pair<String, Exception>> {

    public interface AsyncResponse {
        void processFinish(String output);
        void processError(Exception error);
    }

    private AsyncResponse delegate = null;

    public EndpointsAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }

    private static MyApi myApiService = null;
    private Context context;

    @Override
    protected Pair<String, Exception> doInBackground(Context... params) {
        if (myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
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

        context = params[0];

        try {
            return new Pair(myApiService.tellJoke().execute().getData(), null);

        } catch (IOException e) {
            return new Pair(null, e);
        }
    }

    @Override
    protected void onPostExecute(Pair<String, Exception> result) {
        if(result.first!=null){
            delegate.processFinish(result.first);
        }
        if(result.second!=null){
            delegate.processError(result.second);
        }
    }
}