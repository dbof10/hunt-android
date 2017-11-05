package com.ctech.eaty.ui.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.component.LithoController
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeViewModel
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.rx.plusAssign
import kotlinx.android.synthetic.main.fragment_products.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseReduxFragment<HomeState>(), Injectable {

    companion object {
        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var store: Store<HomeState>

    @Inject
    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var navigator: HomeNavigation

    @Inject
    lateinit var lithoController: LithoController

    override fun store(): Store<HomeState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupRefresh()
        setupLitho()
    }

    private fun setupLitho() {
        lithoController.take(litho)
    }

    private fun setupRefresh() {
//        sfRefresh.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimary))
//        sfRefresh.setOnRefreshListener {
//            store.dispatch(HomeAction.REFRESH)
//        }
    }

    override fun onStart() {
        super.onStart()
        store.dispatch(HomeAction.LOAD)
    }

    override fun onDestroyView() {
        vLottie.cancelAnimation()
        super.onDestroyView()
    }

    private fun renderContent() {
        //  sfRefresh.isRefreshing = false
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
    }

    private fun renderRefreshError() {
        // sfRefresh.isRefreshing = false
    }

    private fun renderLoadMoreError() {

    }


    private fun renderLoadError(error: Throwable) {
        vLottie.cancelAnimation()
        vLottie.visibility = View.GONE
        Timber.e(error)
    }

    private fun renderLoadingMore() {

    }

    private fun renderRefreshing() {
        //   sfRefresh.isRefreshing = true
    }

    private fun renderLoading() {
        vLottie.playAnimation()
        vLottie.visibility = View.VISIBLE
    }

    private fun setupViewModel() {
        disposeOnStop(viewModel.loading().subscribe { renderLoading() })
        disposeOnStop(viewModel.refreshing().subscribe { renderRefreshing() })
        disposeOnStop(viewModel.loadingMore().subscribe { renderLoadingMore() })
        disposeOnStop(viewModel.loadError().subscribe { renderLoadError(it) })
        disposeOnStop(viewModel.loadMoreError().subscribe { renderLoadMoreError() })
        disposeOnStop(viewModel.refreshError().subscribe { renderRefreshError() })
        disposeOnStop(viewModel.content().subscribe { renderContent() })
    }


}