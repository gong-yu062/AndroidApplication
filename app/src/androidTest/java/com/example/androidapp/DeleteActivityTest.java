package com.example.androidapp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.androidapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class DeleteActivityTest {

    @Rule
    public ActivityScenarioRule<DeleteActivity> activityRule =
            new ActivityScenarioRule<>(DeleteActivity.class);

    @Test
    public void testActivityInView() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextDeletionId))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
        Espresso.onView(ViewMatchers.withId(R.id.buttonDelete))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void testEnterTextAndPressButton() {
        Espresso.onView(ViewMatchers.withId(R.id.editTextDeletionId))
                .perform(ViewActions.typeText("123"), ViewActions.closeSoftKeyboard());
        Espresso.onView(ViewMatchers.withId(R.id.buttonDelete))
                .perform(ViewActions.click());
    }
}