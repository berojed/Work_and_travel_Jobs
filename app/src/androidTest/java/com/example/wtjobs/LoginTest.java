package com.example.wtjobs;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.util.Log;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class LoginTest {

  private FirebaseAuth firebaseAuth;
  private  static  final  String TEST_EMAIL = "test@gmail.com";
  private  static  final  String TEST_PASSWORD = "123456";

  @Before
    public  void setUp(){
      firebaseAuth = FirebaseAuth.getInstance();
      firebaseAuth.signOut();
  }

  @After
  public  void tearDown(){
    firebaseAuth.signOut();
  }

  @Test
  public  void testLogin(){
    firebaseAuth.createUserWithEmailAndPassword(TEST_EMAIL, TEST_PASSWORD).addOnCompleteListener(task ->{

      if(task.isSuccessful()){

        ActivityScenario<Login> loginActivityScenario = ActivityScenario.launch(Login.class);

        onView(withId(R.id.email)).perform(replaceText(TEST_EMAIL));
        onView(withId(R.id.password)).perform(replaceText(TEST_PASSWORD));

        onView(withId(R.id.btnLogin)).perform(click());

        loginActivityScenario.onActivity(activity -> {
          FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
          assertNotNull(firebaseUser);
          assertEquals(TEST_EMAIL, firebaseUser.getEmail());
        });

    }
      else
      {
        throw new AssertionError("Neuspješna prijava." + task.getException());
      }
    });

  }


}
