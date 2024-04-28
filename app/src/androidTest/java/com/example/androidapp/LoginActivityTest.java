package com.example.androidapp;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.androidapp.ui.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule<>(LoginActivity.class);

    @Test
    public void testLogin() {
        // Type text and then press the button.
        onView(withId(R.id.editTextUsername)).perform(typeText("testuser"));
        onView(withId(R.id.editTextPassword)).perform(typeText("testpassword"));
        onView(withId(R.id.buttonLogin)).perform(click());

        // Check if the login was successful and redirected to the main activity
        onView(withId(R.id.main_activity_title)).check(matches(withText("Main Activity")));
    }
}