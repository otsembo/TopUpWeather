package com.topupmama.weather

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.topupmama.weather.presentation.favorites.FavoritesPage
import com.topupmama.weather.presentation.home.Home
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

//TODO: THIS TEST WORKS ONLY IN APPS WITHOUT DAGGER

@RunWith(AndroidJUnit4::class)
class NavigationTest {


    @Test
    fun testNavigationFromHomeToDetails(){

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())


        val homeScenario = launchFragmentInContainer<Home>()

        homeScenario.onFragment{

            navController.setGraph(R.navigation.main_navigation)

            Navigation.setViewNavController(it.requireView(), navController)

        }

        onView(ViewMatchers.withId(R.id.weatherCard)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.detailsPage)

    }

    @Test
    fun testNavigationFromFavoritesToDetails(){

        val navController = TestNavHostController(ApplicationProvider.getApplicationContext())


        val homeScenario = launchFragmentInContainer<FavoritesPage>()

        homeScenario.onFragment{

            navController.setGraph(R.navigation.main_navigation)

            Navigation.setViewNavController(it.requireView(), navController)

        }

        onView(ViewMatchers.withId(R.id.weatherCard)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.detailsPage)

    }

}