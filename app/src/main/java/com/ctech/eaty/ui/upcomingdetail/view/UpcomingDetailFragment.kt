package com.ctech.eaty.ui.upcomingdetail.view

import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.AutoTransition
import android.support.transition.Transition
import android.support.transition.TransitionManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.LoadEvent
import com.ctech.eaty.ui.upcomingdetail.action.UpcomingAction
import com.ctech.eaty.ui.upcomingdetail.state.ImageLoadWatcher
import com.ctech.eaty.ui.upcomingdetail.state.UpcomingProductState
import com.ctech.eaty.ui.upcomingdetail.viewmodel.MessageViewModel
import com.ctech.eaty.ui.upcomingdetail.viewmodel.UpcomingDetailViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import com.ctech.eaty.widget.transition.TransitionListener
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.controller.BaseControllerListener
import com.facebook.imagepipeline.image.ImageInfo
import com.github.florent37.viewanimator.ViewAnimator
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposable
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.fragment_upcoming_detail.ivAvatar
import kotlinx.android.synthetic.main.fragment_upcoming_detail.progressBar
import kotlinx.android.synthetic.main.fragment_upcoming_detail.rvMessage
import kotlinx.android.synthetic.main.fragment_upcoming_detail.tvSubscriberCount
import kotlinx.android.synthetic.main.fragment_upcoming_detail.tvTopSubscribers
import kotlinx.android.synthetic.main.fragment_upcoming_detail.vConstraint
import kotlinx.android.synthetic.main.fragment_upcoming_detail.vError
import timber.log.Timber
import javax.inject.Inject


class UpcomingDetailFragment : BaseReduxFragment<UpcomingProductState>(), Injectable {


    companion object {
        private val KEY_SLUG = "slug"

        fun newInstance(slug: String): Fragment {

            val args = Bundle()

            val fragment = UpcomingDetailFragment()
            args.putString(KEY_SLUG, slug)
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var store: Store<UpcomingProductState>

    @Inject
    lateinit var viewModel: UpcomingDetailViewModel

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var imageLoadWatcher: ImageLoadWatcher

    private val slug by lazy {
        arguments!!.getString(KEY_SLUG)
    }

    private val animSubject: PublishSubject<LoadEvent> = PublishSubject.create()

    private val postAnimConstraintSet = ConstraintSet()

    private val adapter by lazy {
        val adapter = MessageAdapter()
        adapter.linkClickListener = { _, url ->
            viewModel.openLink(url, customTabActivityHelper.session)
            true
        }
        adapter.emailSubmitListener = { email ->
            val id = store.getState().content!!.id
            store.dispatch(UpcomingAction.SUBSCRIBE(id, email))
        }
        adapter
    }

    override fun store() = store

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_upcoming_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupAnimation()
        setupRecyclerView()
        store.dispatch(UpcomingAction.LOAD(slug))

        postAnimConstraintSet.clone(vConstraint)
        ivAvatar.scaleX = 0F
        ivAvatar.scaleY = 0F
    }

    private fun setupRecyclerView() {
        rvMessage.adapter = adapter
        rvMessage.layoutManager = LinearLayoutManager(context)
        rvMessage.addItemDecoration(VerticalSpaceItemDecoration(MessageViewHolder::class.java, resources.getDimensionPixelSize(R.dimen.divider_space)))
    }

    private fun setupAnimation() {
        imageLoadWatcher.onAnimReady = {
            startAnimation()
        }
    }

    private fun showError() {
        vError.visibility = View.VISIBLE
        progressBar.visibility = View.GONE
    }

    private fun startAnimation() {
        vError.visibility = View.GONE
        progressBar.visibility = View.GONE

        ViewAnimator
                .animate(ivAvatar)
                .scale(1F)
                .accelerate()
                .duration(500L)
                .onStop {
                    val transition = AutoTransition()
                    transition.addListener(object : TransitionListener() {
                        override fun onTransitionEnd(transition: Transition) {
                            animSubject.onNext(LoadEvent.SUCCESS)
                        }
                    })
                    transition.duration = 1000
                    TransitionManager.beginDelayedTransition(vConstraint, transition)
                    postAnimConstraintSet.setHorizontalBias(R.id.ivAvatar, .05F)
                    postAnimConstraintSet.setVisibility(R.id.ivForeground, View.VISIBLE)
                    postAnimConstraintSet.applyTo(vConstraint)
                }
                .start()
    }

    private fun setupViewModel() {

        viewModel.loading()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    progressBar.visibility = View.VISIBLE
                }

        viewModel.content()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    setupAvatar(it.user.imageUrl.px64)
                    tvSubscriberCount.text = resources.getString(R.string.number_subscriber, it.subscriberCount)
                }

        viewModel.topSubscribers()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    tvTopSubscribers.text = resources.getString(R.string.top_subscribers, it[0], it[1], it[2])
                }

        viewModel.loadError().subscribe {
            vError.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
        }

        viewModel.loadError()
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    showError()
                    Timber.e(it)
                }

        Observable.zip(animSubject, viewModel.messages(), BiFunction<LoadEvent, List<MessageViewModel>, List<MessageViewModel>> { _, t2 ->
            return@BiFunction t2
        })
                .autoDisposable(AndroidLifecycleScopeProvider.from(this))
                .subscribe {
                    adapter.items = it
                    val controller =
                            AnimationUtils.loadLayoutAnimation(context, R.anim.layout_message_rise)

                    rvMessage.layoutAnimation = controller
                    adapter.notifyDataSetChanged()
                    rvMessage.scheduleLayoutAnimation()
                    rvMessage.layoutAnimationListener = object : Animation.AnimationListener {
                        override fun onAnimationRepeat(anim: Animation) {
                        }

                        override fun onAnimationStart(anim: Animation) {
                        }

                        override fun onAnimationEnd(anim: Animation) {
                            TransitionManager.beginDelayedTransition(vConstraint)
                            postAnimConstraintSet.setVisibility(R.id.tvSubscriberCount, View.VISIBLE)
                            postAnimConstraintSet.setVisibility(R.id.tvTopSubscribers, View.VISIBLE)
                            postAnimConstraintSet.applyTo(vConstraint)
                        }
                    }
                }

    }


    private fun setupAvatar(url: String) {
        val listener = object : BaseControllerListener<ImageInfo>() {
            override fun onFinalImageSet(id: String, imageInfo: ImageInfo?, animatable: Animatable?) {
                imageLoadWatcher += LoadEvent.SUCCESS
            }

            override fun onFailure(id: String, throwable: Throwable) {
                imageLoadWatcher += LoadEvent.ERROR
                Timber.e(throwable)
            }
        }
        val controller = Fresco.newDraweeControllerBuilder()
                .setUri(url)
                .setControllerListener(listener)
                .build()
        ivAvatar.controller = controller
    }

}