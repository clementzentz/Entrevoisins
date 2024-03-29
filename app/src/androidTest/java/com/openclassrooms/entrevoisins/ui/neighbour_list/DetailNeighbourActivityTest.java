package com.openclassrooms.entrevoisins.ui.neighbour_list;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.view.View;
import com.openclassrooms.entrevoisins.R;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class DetailNeighbourActivityTest {

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mTestRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    @Test
    public void detailNeighbourActivity_isDisplay_returnTrue(){
        //Arrange
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours))).check(matches(isDisplayed()));
        //Act
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours))).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Assert
        onView(withId(R.id.main_content)).check(matches(isDisplayed()));
    }

    @Test
    public void detailActivityLaunch_isDetailTextViewFilledWithCorrectUserName_returnTrue() {
        //Arrange
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours))).check(matches(isDisplayed()));
        //Act
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours))).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Assert
        onView(ViewMatchers.withId(R.id.avatar_whiteName_txt)).check(matches(withText("Caroline")));
    }

    private static Matcher<View> withIndex(final Matcher<View> matcher) {
        return new TypeSafeMatcher<View>() {
            int currentIndex = 0;
            @Override public void describeTo(Description description) {
                description.appendText("with index: "); description.appendValue(0); matcher.describeTo(description);
            }
            @Override public boolean matchesSafely(View view) {
                return matcher.matches(view) && currentIndex++ == 0;
            }
        };
    }
}