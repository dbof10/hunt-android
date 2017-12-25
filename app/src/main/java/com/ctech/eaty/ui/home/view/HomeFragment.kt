package com.ctech.eaty.ui.home.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.error.ErrorInterpreter
import com.ctech.eaty.ui.home.action.HomeAction
import com.ctech.eaty.ui.home.component.LithoController
import com.ctech.eaty.ui.home.navigation.HomeNavigation
import com.ctech.eaty.ui.home.state.HomeState
import com.ctech.eaty.ui.home.viewmodel.HomeViewModel
import com.ctech.eaty.util.glide.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_products.*
import timber.log.Timber
import javax.inject.Inject

class HomeFragment : BaseFragment(), Injectable {

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

    @Inject
    lateinit var errorInterpreter: ErrorInterpreter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_products, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupLitho()
        setupListener()
        store.dispatch(HomeAction.LOAD)

    }

    private fun setupListener() {
        vError.onRetry = {
            store.dispatch(HomeAction.LOAD)
        }
    }

    private fun setupLitho() {
        lithoController.take(litho)
    }

    private fun renderContent() {
        progressBar.visibility = View.GONE
        vError.visibility = View.GONE
    }

    private fun renderLoadError(error: Throwable) {
        progressBar.visibility = View.GONE
        vError.visibility = View.VISIBLE
        errorInterpreter.getErrorMessage(error).run {
            vError.setReason(reason)
            vError.setExplain(explain)
        }
        Timber.e(error)
    }

    private fun renderLoading() {
        progressBar.visibility = View.VISIBLE
        vError.visibility = View.GONE
    }

    private fun setupViewModel() {
        viewModel.loading().subscribe { renderLoading() }
        viewModel.loadError().subscribe { renderLoadError(it) }
        viewModel.content().subscribe { renderContent() }
    }


}