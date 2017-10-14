package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.util.Log;

import org.junit.Test;

/**
 * Created by rafae on 14/10/2017.
 */
public class JokesAsyncTaskTest extends AndroidTestCase {

    @Test
    public void testTaskReturn() {
        String result = null;
        JokesAsyncTask jokesAsyncTask = new JokesAsyncTask(getContext(), null);
        jokesAsyncTask.execute();
        try {
            result = jokesAsyncTask.get();
            Log.d(JokesAsyncTaskTest.class.getSimpleName(), "String retrieved: " + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        assertNotNull(result);
    }

}