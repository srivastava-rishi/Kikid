package com.example.otpsender.explore

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.otpsender.R
import com.example.otpsender.constant.Constant
import com.example.otpsender.databinding.FragmentExploreBinding
import com.example.otpsender.other.UserDetailActivity
import com.example.otpsender.explore.adapter.ExploreAdapter
import com.example.otpsender.main.viewModel.MainViewModel
import com.example.otpsender.retrofit.UserNetworkEntity
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable

@AndroidEntryPoint
class ExploreFragment : Fragment() ,ExploreAdapter.ExploreAdapterListener {

    lateinit var binding: FragmentExploreBinding

    private lateinit var exploreAdapter: ExploreAdapter

    val logTag = "@ExploreFragment"

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
         ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_explore, container, false)

        initRecyclerView()

        viewModel.responseUserDetail.observe(viewLifecycleOwner) { response ->
            if (response != null) {
                // submit list
                exploreAdapter.submitList(response)
            }
        }
        return binding.root
    }

    private fun initRecyclerView() {
        val llm = LinearLayoutManager(requireContext())
        binding.rvUserDetail.setHasFixedSize(true)
        binding.rvUserDetail.layoutManager = llm
        exploreAdapter = ExploreAdapter(requireContext(), this)
        binding.rvUserDetail.adapter = exploreAdapter
    }


    override fun onItemClicked(item: UserNetworkEntity) {
        val intent = Intent(requireContext(),UserDetailActivity::class.java)
        intent.putExtra(Constant.PARAM_USER_DETAIL, item as Serializable)
        startActivity(intent)
    }

}