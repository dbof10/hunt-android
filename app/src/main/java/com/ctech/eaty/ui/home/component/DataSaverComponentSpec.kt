package com.ctech.eaty.ui.home.component

import android.graphics.Typeface.BOLD
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import com.ctech.eaty.R
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.state.HomeState
import com.facebook.litho.Border
import com.facebook.litho.ClickEvent
import com.facebook.litho.Column
import com.facebook.litho.ComponentContext
import com.facebook.litho.ComponentLayout
import com.facebook.litho.annotations.LayoutSpec
import com.facebook.litho.annotations.OnCreateLayout
import com.facebook.litho.annotations.OnEvent
import com.facebook.litho.annotations.Prop
import com.facebook.litho.widget.Image
import com.facebook.litho.widget.Text
import com.facebook.litho.widget.VerticalGravity
import com.facebook.yoga.YogaAlign
import com.facebook.yoga.YogaEdge
import com.facebook.yoga.YogaJustify

@LayoutSpec
object DataSaverComponentSpec {

    @OnCreateLayout
    fun onCreateLayout(c: ComponentContext): ComponentLayout {

        return Column.create(c)
                .child(
                        Image.create(c)
                                .drawableRes(R.drawable.ic_photo_placeholder)
                                .widthRes(R.dimen.home_footer_action_icon)
                                .heightRes(R.dimen.home_footer_action_icon)
                )
                .child(
                        Text.create(c, 0, R.style.Text_Body3)
                                .text(c.getString(R.string.data_saver_mode_content))
                                .paddingRes(YogaEdge.BOTTOM, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.TOP, R.dimen.content_padding_vertical)
                                .paddingRes(YogaEdge.LEFT, R.dimen.content_padding_horizontal)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.content_padding_horizontal)

                )
                .child(
                        Text.create(c, 0, R.style.Text_InlineAction)
                                .text(c.getString(R.string.disable_data_saver))
                                .verticalGravity(VerticalGravity.CENTER)
                                .paddingRes(YogaEdge.BOTTOM, R.dimen.space_small)
                                .paddingRes(YogaEdge.TOP, R.dimen.space_small)
                                .paddingRes(YogaEdge.LEFT, R.dimen.space_medium)
                                .paddingRes(YogaEdge.RIGHT, R.dimen.space_medium)
                                .textStyle(BOLD)
                                .textColor(ContextCompat.getColor(c, R.color.colorPrimary))
                                .border(
                                        Border.create(c)
                                                .widthRes(YogaEdge.ALL, R.dimen.border_width)
                                                .color(YogaEdge.ALL, ContextCompat.getColor(c, R.color.colorPrimary))
                                                .cornerEffect(c.resources.getDimensionPixelSize(R.dimen.button_radius).toFloat())
                                                .build()

                                )
                                .clickHandler(BodyComponent.onClick(c))
                )
                .alignItems(YogaAlign.CENTER)
                .justifyContent(YogaJustify.CENTER)
                .backgroundColor(ContextCompat.getColor(c, R.color.gray_50))
                .heightRes(R.dimen.data_saver_height)
                .build()
    }


    @OnEvent(ClickEvent::class)
    fun onClick(
            c: ComponentContext, @Prop store: Store<HomeState>) {
        AlertDialog.Builder(c)
                .setMessage(c.getString(R.string.data_saver_confirm))
                .setPositiveButton(c.getString(android.R.string.ok), { dialog, _ ->
                    store.dispatch(HomeAction.USE_MOBILE_DATA)
                    dialog.dismiss()
                })
                .setNegativeButton(c.getString(android.R.string.no)) { dialog, _ ->
                    dialog.dismiss()
                }.create()
                .show()

    }
}