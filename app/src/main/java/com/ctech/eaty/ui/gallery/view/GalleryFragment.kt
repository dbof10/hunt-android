package com.ctech.eaty.ui.gallery.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ctech.eaty.R
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.Media
import com.ctech.eaty.ui.gallery.loader.YoutubeThumbnailLoader
import com.ctech.eaty.ui.gallery.viewmodel.GalleryViewModel
import com.ctech.eaty.util.GlideImageLoader
import kotlinx.android.synthetic.main.fragment_gallery.*
import javax.inject.Inject

class GalleryFragment : Fragment(), Injectable {

    companion object {

        private val MEDIA_KEY = "mediaKey"

        fun newInstance(media: ArrayList<Media>): Fragment {

            val args = Bundle()

            val fragment = GalleryFragment()
            args.putParcelableArrayList(MEDIA_KEY, media)
            fragment.arguments = args
            return fragment
        }
    }


    @Inject
    lateinit var imageLoader: GlideImageLoader

    @Inject
    lateinit var thumbnailLoader: YoutubeThumbnailLoader

    @Inject
    lateinit var viewModel: GalleryViewModel


    private val pagerAdapter: PagerAdapter by lazy {
        val adapter = PagerAdapter(activity)
        adapter.apply {
            onThumbnailLoad { view, url ->
                thumbnailLoader.loadThumbnailInto(view, url)
            }
            onPlayClick { url ->
                viewModel.navigateYoutube(url)
            }
        }
        adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_gallery, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPager.adapter = pagerAdapter
        indicator.setViewPager(viewPager)

    }

    private fun setupViewModel() {
        val media = arguments.getParcelableArrayList<Media>(MEDIA_KEY)
        viewModel.content().subscribe {
            pagerAdapter.setItems(it)
        }
        viewModel.setMedia(media)

    }


    override fun onDestroy() {
        thumbnailLoader.onDestroy()
        super.onDestroy()
    }

}