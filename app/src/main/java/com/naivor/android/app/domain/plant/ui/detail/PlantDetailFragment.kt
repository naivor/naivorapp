/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.naivor.android.app.domain.plant.ui.detail

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ShareCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.naivor.android.app.R
import com.naivor.android.app.common.base.BaseFragment
import com.naivor.android.app.databinding.FragmentPlantDetailBinding
import com.naivor.android.app.embedder.imageloader.load
import com.naivor.android.kotlinex.expandHideTitle
import com.naivor.android.kotlinex.loadHtml
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a single Plant detail screen.
 */
@AndroidEntryPoint
class PlantDetailFragment : BaseFragment<FragmentPlantDetailBinding, PlantDetailViewModel>() {

    override val viewModel: PlantDetailViewModel by viewModels()
    override val pageTitle: String = "植物详情"

    override var isToolbarColorPrimary: Boolean = false
    override var isUseWhiteNavigation: Boolean= false
    override var blackNavigation: Int=R.drawable.ic_detail_back

    override val inflateRootView: (ViewGroup?) -> View = {
        __binding = FragmentPlantDetailBinding.inflate(layoutInflater, it, false)
        binding.root
    }

    override fun initTitle(activity: AppCompatActivity) {
        binding.customTitle.run {
            toolbarView = toolbar
            titleView = tvTitle

            tvTitle.run {
                setTextColor(ContextCompat.getColor(context, R.color.black))
            }

            tvRight.run {
                visibility = View.VISIBLE

                setCompoundDrawablesRelativeWithIntrinsicBounds(
                    R.drawable.ic_detail_share,
                    0,
                    0,
                    0
                )

                setOnClickListener {
                    createShareIntent()
                }
            }
        }

        super.initTitle(activity)

        binding.appbar.expandHideTitle(titleView = titleView)
    }

    override fun initPageView() {
        with(binding) {
            galleryNav.setOnClickListener { navigateToGallery() }

            fab.setOnClickListener {
                hideAppBarFab(fab)
                viewModel.addPlantToGarden()
                Snackbar.make(root, R.string.added_plant_to_garden, Snackbar.LENGTH_LONG)
                    .show()
            }

        }

    }

    override fun initPageData() {
        super.initPageData()

        with(viewModel) {
            plant.observe(viewLifecycleOwner) {
                with(binding) {
                    titleView?.run { text = it.name }

                    plantDetailName.text = it.name
                    detailImage.load(it.imageUrl)
                    plantWatering.text = "$it.wateringInterval"
                    galleryNav.visibility = if (hasValidUnsplashKey()) View.VISIBLE else View.GONE
                    plantDescription.loadHtml(it.description)

                }
            }

            isPlanted.observe(viewLifecycleOwner) {
                binding.fab.visibility = if (it) View.GONE else View.VISIBLE
            }
        }
    }

    private fun navigateToGallery() {
        viewModel.plant.value?.let { plant ->
//            val direction =
//                PlantDetailFragmentDirections.actionPlantDetailFragmentToGalleryFragment(plant.name)
//            findNavController().navigate(direction)
        }
    }

    // Helper function for calling a share functionality.
    // Should be used when user presses a share button/menu item.
    @Suppress("DEPRECATION")
    private fun createShareIntent() {
        val shareText = viewModel.plant.value.let { plant ->
            if (plant == null) {
                ""
            } else {
                getString(R.string.share_text_plant, plant.name)
            }
        }
        val shareIntent = ShareCompat.IntentBuilder.from(requireActivity())
            .setText(shareText)
            .setType("text/plain")
            .createChooserIntent()
            .addFlags(Intent.FLAG_ACTIVITY_NEW_DOCUMENT or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
        startActivity(shareIntent)
    }


    private fun hideAppBarFab(fab: FloatingActionButton) {
        val params = fab.layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as FloatingActionButton.Behavior
        behavior.isAutoHideEnabled = false
        fab.hide()
    }
}
