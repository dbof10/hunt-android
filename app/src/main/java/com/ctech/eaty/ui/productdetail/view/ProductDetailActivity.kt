package com.ctech.eaty.ui.productdetail.view

import android.animation.ValueAnimator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.graphics.Palette
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.ctech.eaty.R
import com.ctech.eaty.annotation.IS_DARK
import com.ctech.eaty.annotation.LIGHTNESS_UNKNOWN
import com.ctech.eaty.annotation.Lightness
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.productdetail.action.USE_MOBILE_DATA
import com.ctech.eaty.ui.productdetail.state.ProductDetailState
import com.ctech.eaty.ui.productdetail.viewmodel.ProductDetailViewModel
import com.ctech.eaty.ui.web.support.CustomTabActivityHelper
import com.ctech.eaty.util.AnimUtils.getFastOutSlowInInterpolator
import com.ctech.eaty.util.ColorUtils
import com.ctech.eaty.util.glide.GlideImageLoader
import com.ctech.eaty.util.ViewUtils
import com.ctech.eaty.widget.ElasticDragDismissFrameLayout
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_product_detail.fab
import kotlinx.android.synthetic.main.activity_product_detail.flDraggable
import kotlinx.android.synthetic.main.activity_product_detail.flProductHolder
import kotlinx.android.synthetic.main.activity_product_detail.ivBack
import kotlinx.android.synthetic.main.activity_product_detail.ivProduct
import kotlinx.android.synthetic.main.layout_data_saver.llDataSaver
import kotlinx.android.synthetic.main.layout_data_saver.tvDisable
import timber.log.Timber
import javax.inject.Inject

class ProductDetailActivity : BaseActivity(), HasSupportFragmentInjector, FragmentContract,
        CustomTabActivityHelper.ConnectionCallback {

    private val SCRIM_ADJUSTMENT = 0.075f
    private val FAB_SCALE_DURATION = 300L

    override fun getScreenName(): String = "Product Detail"

    companion object {
        val PRODUCT_ID = "productId"

        fun newIntent(context: Context, id: Int): Intent {
            val intent = Intent(context, ProductDetailActivity::class.java)
            intent.putExtra(PRODUCT_ID, id)
            return intent
        }
    }

    @Inject
    lateinit var customTabActivityHelper: CustomTabActivityHelper

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var store: Store<ProductDetailState>

    @Inject
    lateinit var viewModel: ProductDetailViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    private lateinit var chromeFader: ElasticDragDismissFrameLayout.SystemChromeFader

    private var fabOffset: Int = 0

    private val productId by lazy {
        intent.getIntExtra(PRODUCT_ID, 0)
    }

    private val imageLoaderCallback by lazy {
        object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                Timber.e(e)
                return false
            }

            override fun onResourceReady(resource: Drawable, model: Any?, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                val bitmap = GlideImageLoader.getBitmap(resource)
                val twentyFourDip = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                        24f, resources.displayMetrics).toInt()

                bitmap?.run {

                    Palette.from(this)
                            .maximumColorCount(3)
                            .clearFilters() /* by default palette ignore certain hues
                        (e.g. pure black/white) but we don't want this. */
                            .setRegion(0, 0, bitmap.width - 1, twentyFourDip) /* - 1 to work around
                        https://code.google.com/p/android/issues/detail?id=191013 */
                            .generate { palette ->
                                val isDark: Boolean

                                @Lightness val lightness = ColorUtils.isDark(palette)

                                isDark = if (lightness == LIGHTNESS_UNKNOWN) {
                                    ColorUtils.isDark(bitmap, bitmap.width / 2, 0)
                                } else {
                                    lightness == IS_DARK
                                }

                                if (!isDark) { // make back icon dark on light images
                                    ivBack.setColorFilter(ContextCompat.getColor(
                                            applicationContext, R.color.black_60))
                                }

                                // color the status bar. Set a complementary dark color on L,
                                // light or dark color on M (with matching status bar icons)
                                var statusBarColor = window.statusBarColor
                                val topColor = ColorUtils.getMostPopulousSwatch(palette)
                                if (topColor != null && (isDark || Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
                                    statusBarColor = ColorUtils.scrimify(topColor.rgb,
                                            isDark, SCRIM_ADJUSTMENT)
                                    // set a light status bar on M+
                                    if (!isDark && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        ViewUtils.setLightStatusBar(ivProduct)
                                    }
                                }

                                if (statusBarColor != window.statusBarColor) {
                                    ivProduct.setScrimColor(statusBarColor)
                                    val statusBarColorAnim = ValueAnimator.ofArgb(
                                            window.statusBarColor, statusBarColor)
                                    statusBarColorAnim.addUpdateListener { animation -> window.statusBarColor = animation.animatedValue as Int }
                                    statusBarColorAnim.duration = 1000L
                                    statusBarColorAnim.interpolator = getFastOutSlowInInterpolator(applicationContext)
                                    statusBarColorAnim.start()
                                }
                            }

                    Palette.from(this)
                            .clearFilters()
                            .generate { palette ->
                                // color the ripple on the image spacer (default is grey)
                                val fragment = supportFragmentManager.findFragmentById(R.id.fBody) as? ProductBodyFragment
                                fragment?.apply {
                                    setSpacerBackground(
                                            ViewUtils.createRipple(palette, 0.25f,
                                                    0.5f,
                                                    ContextCompat.getColor(this@ProductDetailActivity, R.color.mid_grey),
                                                    true)
                                    )
                                }
                                // slightly more opaque ripple on the pinned image to compensate
                                // for the scrim
                                ivProduct.foreground = ViewUtils.createRipple(palette, 0.3f, 0.6f,
                                        ContextCompat.getColor(applicationContext, R.color.mid_grey),
                                        true)
                            }
                }

                // TODO should keep the background if the image contains transparency?!
                //   ivProduct.background = null
                return false
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        chromeFader = object : ElasticDragDismissFrameLayout.SystemChromeFader(this) {
            override fun onDragDismissed() {
                finishAfterTransition()
            }
        }

        setupChromeService()
        setupViewModel()
        setupListener()
        setupBodyFragment()
        setupHeader()
        setupToolbar()
        trackingManager.trackScreenView(getScreenName())

    }

    override fun onFinishFragmentInflate() {
        ivProduct.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        ivProduct.viewTreeObserver.removeOnPreDrawListener(this)
                        calculateFabPosition()
                        return true
                    }
                })
    }

    private fun calculateFabPosition() {
        fabOffset = flProductHolder.height - (fab.height / 2)
        fab.setOffset(fabOffset)
        fab.setMinOffset(flProductHolder.minimumHeight - (fab.height / 2))
    }

    override fun onResume() {
        super.onResume()
        flDraggable.addListener(chromeFader)
    }

    override fun onPause() {
        flDraggable.removeListener(chromeFader)
        super.onPause()
    }

    override fun onStart() {
        super.onStart()
        customTabActivityHelper.bindCustomTabsService(this)
    }

    override fun onStop() {
        customTabActivityHelper.unbindCustomTabsService(this)
        super.onStop()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    private fun setupChromeService() {
        customTabActivityHelper.setConnectionCallback(this)
    }

    private fun setupViewModel() {
        viewModel.liking().subscribe {
            showLiked(it)
        }
        viewModel.unliking().subscribe {
            showLiked(it)
        }
        viewModel.liked().subscribe {
            showLiked(it)
        }
        viewModel.header()
                .subscribe {
                    renderHeader(it)
                }
        viewModel.requiredLoggedIn()
                .subscribe()
        viewModel.dataSaver().subscribe {
            llDataSaver.visibility = View.VISIBLE
        }
    }

    private fun setupListener() {
        fab.setOnClickListener {
//            if (fab.isChecked) {
//                store.dispatch(UnLike(productId))
//            } else {
//                store.dispatch(Like(productId))
//            }
        }

        tvDisable.setOnClickListener {
            AlertDialog.Builder(this)
                    .setMessage(getString(R.string.data_saver_confirm))
                    .setPositiveButton(getString(android.R.string.ok), { dialog, _ ->
                        store.dispatch(USE_MOBILE_DATA())
                        dialog.dismiss()
                    })
                    .setNegativeButton(getString(android.R.string.no)) { dialog, _ ->
                        dialog.dismiss()
                    }.create()
                    .show()
        }
    }

    override fun onDataLoaded() {
        fab.animate()
                .scaleX(1F)
                .scaleY(1F)
                .setDuration(FAB_SCALE_DURATION)
                .start()
    }

    private fun setupHeader() {
        ivProduct.setOnClickListener {
            viewModel.navigateGallery()
        }
    }

    private fun renderHeader(productUrl: String) {
        ivProduct.visibility = View.VISIBLE
        llDataSaver.visibility = View.GONE
        imageLoader.downloadInto(productUrl, ivProduct, imageLoaderCallback)
    }

    private fun setupBodyFragment() {
        var fragment = supportFragmentManager.findFragmentById(R.id.fBody)
        if (fragment == null) {
            fragment = ProductBodyFragment.newInstance(productId)
            supportFragmentManager.beginTransaction()
                    .add(R.id.fBody, fragment)
                    .commit()
        }
    }

    private fun setupToolbar() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    override fun onScrollStateChanged(newState: Int) {
        ivProduct.setImmediatePin(newState == RecyclerView.SCROLL_STATE_SETTLING)
    }

    override fun onScrolled(headerView: View) {
        val scrollY = headerView.top
        fab.setOffset(fabOffset + scrollY)
        flProductHolder.setOffset(scrollY)
    }

    override fun onFling() {
        flProductHolder.setImmediatePin(true)
    }

    override fun onCustomTabsConnected() {

    }

    override fun onCustomTabsDisconnected() {

    }

    private fun showLiked(liked: Boolean) {
        fab.isChecked = liked
    }

}
