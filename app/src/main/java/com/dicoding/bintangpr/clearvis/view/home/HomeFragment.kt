package com.dicoding.bintangpr.clearvis.view.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.bintangpr.clearvis.data.model.DataArticle
import com.dicoding.bintangpr.clearvis.databinding.FragmentHomeBinding
import com.dicoding.bintangpr.clearvis.utils.gone
import com.dicoding.bintangpr.clearvis.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homeViewModel.getArtikels()
        homeViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                showLoading(true)
            } else {
                showLoading(false)
            }
        }

        homeViewModel.listArtikels.observe(viewLifecycleOwner) { data ->
            setRecycleview(data)
        }

    }

    private fun setRecycleview(listUser: List<DataArticle>) {
        if (listUser.isNullOrEmpty()) {
            showLoading(false)
            binding?.rvHome?.gone()
        } else {
            showLoading(false)
            binding?.rvHome?.visible()
            val mainAdapter = HomeAdapter(listUser)
            binding?.rvHome?.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = mainAdapter
            }
            mainAdapter.setOnItemClickCallback(object : HomeAdapter.OnItemClickCallback{
                override fun onItemClicked(data: DataArticle) {
                    val openURL = Intent(Intent.ACTION_VIEW)
                    openURL.data = Uri.parse(data.url)
                    startActivity(openURL)
                }
            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.rvHome?.gone()
            binding?.pbArtikel?.visible()
        } else {
            binding?.pbArtikel?.gone()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}