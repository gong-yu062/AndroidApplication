package com.example.androidapp;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.androidapp.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import com.example.androidapp.ui.SignUpActivity;


@RunWith(AndroidJUnit4.class)
public class SignUpActivityTest {

    @Rule
    public ActivityScenarioRule<SignUpActivity> activityRule =
            new ActivityScenarioRule<>(SignUpActivity.class);

    @Test
    public void testSignUp() {
        // Fill out the form.
        Espresso.onView(withId(R.id.firstNameEditText)).perform(typeText("John"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.lastNameEditText)).perform(typeText("Doe"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.dateOfBirthEditText)).perform(typeText("01/01/2000"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.genderEditText)).perform(typeText("Male"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.weightEditText)).perform(typeText("70"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.heightEditText)).perform(typeText("170"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.emailEditText)).perform(typeText("john.doe@example.com"), closeSoftKeyboard());
        Espresso.onView(withId(R.id.passwordEditText)).perform(typeText("password"), closeSoftKeyboard());

        // Click the sign up button.
        Espresso.onView(withId(R.id.buttonSignUp)).perform(click());
    }
}