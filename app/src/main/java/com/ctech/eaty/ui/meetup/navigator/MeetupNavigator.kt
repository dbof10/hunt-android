package com.ctech.eaty.ui.meetup.navigator

import android.content.Intent
import android.net.Uri
import android.provider.CalendarContract
import android.support.customtabs.CustomTabsIntent
import android.support.customtabs.CustomTabsSession
import android.support.v4.content.ContextCompat
import com.ctech.eaty.R
import com.ctech.eaty.entity.MeetupEvent
import com.ctech.eaty.ui.meetup.view.MeetupActivity
import com.ctech.eaty.ui.web.WebviewFallback
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import io.reactivex.Completable
import javax.inject.Inject


class MeetupNavigator @Inject constructor(private val context: MeetupActivity) {

    fun toUrl(url: String, session: CustomTabsSession?): Completable {
        return Completable.fromAction {
            val intentBuilder = CustomTabsIntent.Builder(session)
            intentBuilder.setStartAnimations(context, R.anim.slide_in_right, R.anim.slide_out_left)
            intentBuilder.setExitAnimations(context, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            intentBuilder.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary))
            CustomTabActivityHelper.openCustomTab(context, intentBuilder.build(), Uri.parse(url), WebviewFallback())
        }

    }

    fun toShare(link: String): Completable {
        return Completable.fromAction {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, link)
            context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_chooser)))
        }
    }

    fun toCalendar(event: MeetupEvent): Completable {
        return Completable.fromAction {
            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.timeStamp)
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
            intent.putExtra(CalendarContract.Events.TITLE, context.getString(R.string.meetup_title))
            intent.putExtra(CalendarContract.Events.DESCRIPTION, event.description)
            context.startActivity(intent)
        }
    }
}