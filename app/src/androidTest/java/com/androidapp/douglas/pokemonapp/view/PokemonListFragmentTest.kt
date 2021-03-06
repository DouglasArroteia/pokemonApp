package com.androidapp.douglas.pokemonapp.view

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.androidapp.douglas.pokemonapp.R
import com.androidapp.douglas.pokemonapp.view.activities.PokemonActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class PokemonListFragmentTest {

    @Test
    fun pokemonListFragment_shouldHaveRecyclerView() {
        ActivityScenario.launch(PokemonActivity::class.java)
        Espresso.onView(withId(R.id.recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}