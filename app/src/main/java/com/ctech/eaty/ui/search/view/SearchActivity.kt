package com.ctech.eaty.ui.search.view

import android.app.SearchManager
import android.app.SharedElementCallback
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.Typeface
import android.os.Bundle
import android.support.annotation.TransitionRes
import android.support.v7.widget.LinearLayoutManager
import android.text.InputType
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.TextUtils
import android.text.style.StyleSpan
import android.transition.Transition
import android.transition.TransitionInflater
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.SparseArray
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.TextView
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseActivity
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.tracking.FirebaseTrackManager
import com.ctech.eaty.ui.home.viewmodel.ProductItemViewModel
import com.ctech.eaty.ui.search.action.SearchAction
import com.ctech.eaty.ui.search.state.SearchState
import com.ctech.eaty.ui.search.viewmodel.SearchViewModel
import com.ctech.eaty.ui.topiclist.view.SearchViewHolder
import com.ctech.eaty.util.GlideImageLoader
import com.ctech.eaty.util.TransitionUtils
import com.ctech.eaty.util.hideIme
import com.ctech.eaty.util.showIme
import com.ctech.eaty.widget.recyclerview.InfiniteScrollListener
import com.ctech.eaty.widget.recyclerview.SlideInItemAnimator
import com.ctech.eaty.widget.recyclerview.VerticalSpaceItemDecoration
import com.ctech.eaty.widget.transition.CircularReveal
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.kotlin.autoDisposeWith
import kotlinx.android.synthetic.main.activity_search.*
import vn.tiki.noadapter2.DiffCallback
import vn.tiki.noadapter2.OnlyAdapter
import javax.inject.Inject

class SearchActivity : BaseActivity(), Injectable, com.ctech.eaty.ui.search.view.SearchView {

    companion object {

        fun newIntent(context: Context): Intent {
            val intent = Intent(context, SearchActivity::class.java)
            return intent
        }
    }

    private val transitions = SparseArray<Transition>()
    private var focusQuery = true

    @Inject
    lateinit var trackingManager: FirebaseTrackManager

    @Inject
    lateinit var store: Store<SearchState>

    @Inject
    lateinit var viewModel: SearchViewModel

    @Inject
    lateinit var imageLoader: GlideImageLoader

    private val tvNoResult: TextView by lazy {
        val view = stub_no_search_results.inflate() as TextView
        view.setOnClickListener {
            searchView.setQuery("", false)
            searchView.requestFocus()
            showIme(searchView)
        }
        return@lazy view
    }

    private val diffCallback = object : DiffCallback {

        override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
            if (oldItem is ProductItemViewModel && newItem is ProductItemViewModel) {
                return oldItem.id == newItem.id
            }
            return false
        }

        override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
            return oldItem == newItem
        }

    }

    val adapter: OnlyAdapter by lazy {
        OnlyAdapter.builder()
                .diffCallback(diffCallback)
                .onItemClickListener { view, item, position ->
                    if (item is ProductItemViewModel) {
                        if (view.id == R.id.flProductHolder) {
                            viewModel.toProduct(item.id)
                        }

                    }
                }
                .viewHolderFactory { viewGroup, _ ->
                    SearchViewHolder.create(viewGroup, imageLoader, true)
                }
                .build()
    }

    override fun getScreenName() = "Search"

    private val loadMoreCallback by lazy {
        InfiniteScrollListener(rvSearchResult.layoutManager as LinearLayoutManager, 3) {
            store.dispatch(SearchAction.LoadMore(searchView.query.toString()))
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        setupSearchView()
        setupTransitions()
        onNewIntent(intent)
        setupListener()
        setupRecyclerView()
        setupViewModel()

        store
                .startBinding()
                .autoDisposeWith(AndroidLifecycleScopeProvider.from(this))
                .subscribe()

        viewModel.attachView(this)
        trackingManager.trackScreenView(getScreenName())

    }

    private fun setupRecyclerView() {
        rvSearchResult.adapter = adapter
        rvSearchResult.itemAnimator = SlideInItemAnimator()

        val layoutManager = LinearLayoutManager(this)

        rvSearchResult.layoutManager = layoutManager
        rvSearchResult.setHasFixedSize(true)
        rvSearchResult.addItemDecoration(
                VerticalSpaceItemDecoration(SearchViewHolder::class.java,
                        resources.getDimensionPixelSize(R.dimen.divider_space)))

        rvSearchResult.addOnScrollListener(loadMoreCallback)

    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
        hideIme(searchView)
        searchView.clearFocus()
    }

    override fun showLoadError() {
        progressBar.visibility = View.GONE
    }

    override fun showEmpty() {
        TransitionManager.beginDelayedTransition(
                container, getTransition(R.transition.auto))
        progressBar.visibility = View.GONE
        setNoResultsVisibility(View.VISIBLE)
    }

    override fun showContent(content: List<ProductItemViewModel>) {
        if (rvSearchResult.visibility != View.VISIBLE) {
            TransitionManager.beginDelayedTransition(container,
                    getTransition(R.transition.search_show_results))
            progressBar.visibility = View.GONE
            rvSearchResult.visibility = View.VISIBLE
        }
        adapter.setItems(content)
    }

    private fun setupViewModel() {
        viewModel.render()
    }

    private fun setupListener() {
        scrim.setOnClickListener {
            searchBack.background = null
            finishAfterTransition()
        }

        searchBack.setOnClickListener {
            searchBack.background = null
            finishAfterTransition()
        }
    }

    override fun onNewIntent(intent: Intent) {
        if (intent.hasExtra(SearchManager.QUERY)) {
            val query = intent.getStringExtra(SearchManager.QUERY)
            if (!TextUtils.isEmpty(query)) {
                searchView.setQuery(query, false)
                store.dispatch(SearchAction.Load(query))
            }
        }
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

    override fun onDestroy() {
        viewModel.detachView()
        super.onDestroy()
    }

    override fun onEnterAnimationComplete() {
        if (focusQuery) {
            // focus the search view once the enter transition finishes
            searchView.requestFocus()
            showIme(searchView)
        }
    }


    private fun setNoResultsVisibility(visibility: Int) {
        if (visibility == View.VISIBLE) {
            val message = String.format(getString(R.string.no_search_results), searchView.query.toString())

            val ssb = SpannableStringBuilder(message)
            ssb.setSpan(StyleSpan(Typeface.ITALIC), message.indexOf('“') + 1,
                    message.length - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            tvNoResult.text = ssb
        }

        tvNoResult.visibility = visibility
    }

    private fun clearResults() {
        TransitionManager.beginDelayedTransition(container, getTransition(R.transition.auto))
        adapter.setItems(emptyList<ProductItemViewModel>())
        rvSearchResult.visibility = View.GONE
        progressBar.visibility = View.GONE
        resultsScrim.visibility = View.GONE
        setNoResultsVisibility(View.GONE)
    }

    private fun getTransition(@TransitionRes transitionId: Int): Transition {
        var transition = transitions.get(transitionId)
        if (transition == null) {
            transition = TransitionInflater.from(this).inflateTransition(transitionId)
            transitions.put(transitionId, transition)
        }
        return transition
    }

    private fun setupSearchView() {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_hint)
        searchView.inputType = InputType.TYPE_TEXT_FLAG_CAP_WORDS
        searchView.imeOptions = searchView.imeOptions or EditorInfo.IME_ACTION_SEARCH or
                EditorInfo.IME_FLAG_NO_EXTRACT_UI or EditorInfo.IME_FLAG_NO_FULLSCREEN

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                trackingManager.trackSearchKeyword(query)
                store.dispatch(SearchAction.Load(query))
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (TextUtils.isEmpty(query)) {
                    clearResults()
                }
                return true
            }
        })
    }

    private fun setupTransitions() {
        // grab the position that the search icon transitions in *from*
        // & use it to configure the return transition
        setEnterSharedElementCallback(object : SharedElementCallback() {

            override fun onSharedElementStart(sharedElementNames: List<String>, sharedElements: List<View>?,
                                              sharedElementSnapshots: List<View>) {
                if (sharedElements != null && !sharedElements.isEmpty()) {
                    val searchIcon = sharedElements[0]

                    if (searchIcon.id != R.id.searchBack)
                        return

                    val centerX = (searchIcon.left + searchIcon.right) / 2

                    val hideResults = TransitionUtils.findTransition(window.returnTransition as TransitionSet, CircularReveal::class.java, R.id.resultsContainer) as? CircularReveal
                    hideResults?.setCenter(Point(centerX, 0))
                }
            }
        })
    }
}