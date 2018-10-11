package test;

import android.test.AndroidTestCase;
import android.util.Pair;

import com.udacity.gradle.builditbigger.EndpointsAsyncTask;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class EmptyStringTest extends AndroidTestCase {


    @SuppressWarnings("unchecked")
    public void test() throws Throwable {

        //final CountDownLatch signal = new CountDownLatch(1);

        Pair<String, Exception> result = null;
        EndpointsAsyncTask endpointsAsyncTask = new EndpointsAsyncTask(new EndpointsAsyncTask.AsyncResponse() {
            @Override
            public void processFinish(String output) {
                assertNotNull(output);
                //signal.countDown();
            }

            @Override
            public void processError(Exception error) {

            }
        });
        endpointsAsyncTask.execute(getContext());
        try {
            endpointsAsyncTask.get(30, TimeUnit.SECONDS);
        } catch (Exception e) {
            fail("Fail");
        }
        //signal.await();
    }
}