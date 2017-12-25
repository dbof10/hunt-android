package com.ctech.eaty.ui.home.component.upcoming

import android.graphics.Color
import com.ctech.eaty.R
import com.ctech.eaty.entity.User
import com.ctech.eaty.util.ResizeImageUrlProvider
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.Row
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.Prop
import com.facebook.litho.fresco.FrescoImage
import com.facebook.litho.widget.Card
import com.facebook.yoga.YogaEdge

@LayoutSpec
object TopUsersComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext, @Prop users: List<User>): ComponentLayout {


        val size = c.resources.getDimensionPixelSize(R.dimen.top_user_avatar_size)
        val margin = c.resources.getDimensionPixelSize(R.dimen.space_medium)
        val builder = Row
                .create(c)

        users.take(3).forEachIndexed { _ , user ->

            val controller = Fresco.newDraweeControllerBuilder()
                    .setUri(ResizeImageUrlProvider.overrideUrl(user.imageUrl.px64, size))
                    .build()

            builder.child(
                    Card.create(c)
                            .cornerRadiusPx(size / 2F)
                            .shadowStartColor(Color.TRANSPARENT)
                            .content(
                                    FrescoImage
                                            .create(c)
                                            .controller(controller)
                                            .widthPx(size)
                                            .heightPx(size)

                            )
                            .marginPx(YogaEdge.LEFT, -margin)
            )
        }
        return builder.build()
    }

}