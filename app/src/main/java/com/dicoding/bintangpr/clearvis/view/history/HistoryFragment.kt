package com.dicoding.bintangpr.clearvis.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import arrow.core.Either
import arrow.core.getOrElse
import com.dicoding.bintangpr.clearvis.data.model.DataItem
import com.dicoding.bintangpr.clearvis.databinding.FragmentHistoryBinding
import com.dicoding.bintangpr.clearvis.utils.gone
import com.dicoding.bintangpr.clearvis.utils.visible
import io.github.nefilim.kjwt.JWT
import org.koin.androidx.viewmodel.ext.android.viewModel

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding
    private val historyViewModel: HistoryViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.getUser().observe(viewLifecycleOwner) { data ->
            val getId = JWT.decode(data.accessToken).map {
                it.claimValue("id").getOrElse { "error" }
            }
            val id = when (getId) {
                is Either.Right -> getId.value
                else -> "error"
            }

            historyViewModel.getHistory(data.accessToken, id.toInt())
        }

        historyViewModel.isLoading.observe(viewLifecycleOwner) {
            if (it == true) {
                showLoading(true)
            } else {
                showLoading(false)
            }
        }

        historyViewModel.history.observe(viewLifecycleOwner) { data ->
            setRecycleview(data)
        }

    }

    private fun setRecycleview(listUser: List<DataItem>) {
        if (listUser.isNullOrEmpty()) {
            showLoading(false)
            binding?.rvHistory?.gone()
            binding?.tvEmpty?.visible()
        } else {
            showLoading(false)
            binding?.tvEmpty?.gone()
            binding?.rvHistory?.visible()
            val mainAdapter = HistoryAdapter(listUser)
            binding?.rvHistory?.apply {
                layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                setHasFixedSize(true)
                adapter = mainAdapter
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding?.rvHistory?.gone()
            binding?.pbUser?.visible()
            binding?.tvEmpty?.gone()
        } else {
            binding?.pbUser?.gone()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}