package com.anle.simplequotes;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.anle.simplequotes.data.local.QuotesDAO;
import com.anle.simplequotes.data.local.QuotesDatabase;
import com.anle.simplequotes.data.model.Quote;
import com.anle.simplequotes.utils.LiveDataTestUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class QuotesDaoTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    QuotesDatabase db;
    QuotesDAO dao;

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, QuotesDatabase.class)
                .allowMainThreadQueries().build();
        dao = db.quoteDao();
    }

    @After
    public void closeDb() throws Exception {
        db.close();
    }

    @Test
    public void fetchAllQuoetsTest00() throws InterruptedException {
        LiveData<List<Quote>> allQuotes = dao.fetchAllQuotes();
        int actual = LiveDataTestUtil.getOrAwaitValue(allQuotes).size();
        assertEquals(4995, actual);
    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.anle.simplequotes", appContext.getPackageName());
    }
}
