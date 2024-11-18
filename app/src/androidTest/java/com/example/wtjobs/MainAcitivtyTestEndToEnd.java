package com.example.wtjobs;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ActivityScenario;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainAcitivtyTestEndToEnd {


    @Test
    public void testMainActivityFlow(){

        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        scenario.onActivity(activity -> {
            Espresso.onView(withId(R.id.navigation_jobs)).perform(click());
            Espresso.onView(withId(R.id.JobsRecyclerView)).check(matches(isDisplayed()));



            Espresso.onView(withId(R.id.navigation_home)).perform(click());
        });
    }


    }

