package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.annotation.NonNull;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class DetailNeighbourActivityTest {

    private NeighbourApiService mNeighbourApiService;

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    @Before
    public void setup(){
        mNeighbourApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void detailNeighbourActivity_isDisplay_returnTrue(){
        //Arrange
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0)).check(matches(isDisplayed()));
        //Act
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Assert
        onView(withId(R.id.main_content)).check(matches(isDisplayed()));
    }


    //TODO comparer la String de l'élément 0 de la recyclerview avec la String de la textView de l'activité détailNeighbour
    @Test
    public void detailActivityLaunch_isDetailTextViewFilledWithCorrectUserName_returnTrue() throws Exception {
        //Arrange
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0)).check(matches(isDisplayed()));
        //Act
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Assert
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0))
                .check(matches(atPosition(0, hasDescendant(getText(withId(R.id.avatar_whiteName_txt))))));
    }

    private String getText(final Matcher<View> matcher) {
        final String[] stringHolder = { null };
        onView(matcher).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isAssignableFrom(TextView.class);
            }

            @Override
            public String getDescription() {
                return "getting text from a TextView";
            }

            @Override
            public void perform(UiController uiController, View view) {
                TextView tv = (TextView)view; //Save, because of check in getConstraints()
                stringHolder[0] = tv.getText().toString();
            }
        });
        return stringHolder[0];
    }


    private static Matcher<View> atPosition(final int position, @NonNull final Matcher<View> itemMatcher) {
        checkNotNull(itemMatcher);
        return new BoundedMatcher<View, RecyclerView>(RecyclerView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has item at position " + position + ": ");
                itemMatcher.describeTo(description);
            }

            @Override
            protected boolean matchesSafely(final RecyclerView view) {
                RecyclerView.ViewHolder viewHolder = view.findViewHolderForAdapterPosition(position);
                if (viewHolder == null) {
                    // has no item on such position
                    return false;
                }
                return itemMatcher.matches(viewHolder.itemView);
            }
        };
    }

    private static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;
            @Override public void describeTo(Description description) {
                description.appendText("with index: "); description.appendValue(index); matcher.describeTo(description);
            }
            @Override public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == index;
            }
        };
    }
}