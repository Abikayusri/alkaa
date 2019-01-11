package com.escodro.alkaa.framework

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.chip.Chip
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import java.text.DateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Custom [TypeSafeMatcher]s to be used in tests.
 */
object Matchers {

    fun isTextInLines(lines: Int) =
        object : TypeSafeMatcher<View>() {
            override fun matchesSafely(item: View?): Boolean {
                return (item as? TextView)?.lineCount == lines
            }

            override fun describeTo(description: Description?) {
                description?.appendText("check number of lines")
            }
        }

    fun getChildAt(parentMatcher: Matcher<View>, index: Int) =
        object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View?): Boolean {
                if (view?.parent !is ViewGroup) {
                    return parentMatcher.matches(view?.parent)
                }

                val viewGroup = view.parent as ViewGroup
                return parentMatcher.matches(view.parent) && viewGroup.getChildAt(index) == view
            }

            override fun describeTo(description: Description?) {
                description?.appendText("click at child $index from ViewGroup")
            }
        }

    fun compareDates(calendar: Calendar) =
        object : TypeSafeMatcher<View>() {
            override fun matchesSafely(view: View?): Boolean {
                val dateFormat = DateFormat.getDateTimeInstance(
                    DateFormat.LONG,
                    DateFormat.SHORT,
                    Locale.getDefault()
                )

                val chipView = view as Chip
                val date = dateFormat.parse(chipView.text?.toString())
                return calendar.time.compareTo(date) == 0
            }

            override fun describeTo(description: Description?) {
                description?.appendText("compare two dates from string format")
            }
        }
}
