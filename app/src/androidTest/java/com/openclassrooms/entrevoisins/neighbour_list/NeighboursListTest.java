
package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.contrib.ViewPagerActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;



/**
 * Test class for list of neighbours
 */
@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule = new ActivityTestRule<>(ListNeighbourActivity.class);

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0))
                .check(matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem() {
        // Given : We remove the element at position 2
        // This is fixed
        int ITEMS_COUNT = 12;
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 11
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours),0)).check(withItemCount(ITEMS_COUNT -1));
    }

    @Test
    public void neighbourListFavoris_addNeighbourToListFavoris_listShouldOnlyDisplayFavoris(){
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0)).check(matches(isDisplayed()));
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 0)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(ViewMatchers.withId(R.id.favories_fab)).perform(click());
        onView(ViewMatchers.withId(R.id.return_mainActivity_btn)).perform(click());
        onView(ViewMatchers.withId(R.id.container)).perform(ViewPagerActions.scrollRight());
        int listNeighbourFavorisCount = 1;
        onView(withIndex(ViewMatchers.withId(R.id.list_neighbours), 1)).check((withItemCount(listNeighbourFavorisCount)));
    }

    public static Matcher<View> withIndex(final Matcher<View> matcher, final int index) {
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