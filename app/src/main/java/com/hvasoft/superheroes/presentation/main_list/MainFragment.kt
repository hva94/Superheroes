package com.hvasoft.superheroes.presentation.main_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hvasoft.superheroes.R
import com.hvasoft.superheroes.core.Utils.showMessage
import com.hvasoft.superheroes.core.exceptions.TypeError
import com.hvasoft.superheroes.data.model.Superhero
import com.hvasoft.superheroes.databinding.FragmentMainBinding
import com.hvasoft.superheroes.presentation.main_list.adapter.OnClickListener
import com.hvasoft.superheroes.presentation.main_list.adapter.SuperheroesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), OnClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SuperheroesAdapter
    private lateinit var gridLayoutManager: GridLayoutManager
    private val viewModel: MainViewModel by viewModels()
    private var isUiLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupViewModel()
        loadSuperheroesOnScrolled()
    }

    private fun setupRecyclerView() {
        adapter = SuperheroesAdapter(this)
        gridLayoutManager = GridLayoutManager(context, resources.getInteger(R.integer.main_columns))

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = gridLayoutManager
            adapter = this@MainFragment.adapter
        }
    }

    private fun setupViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.stateFlow.collectLatest { superheroes ->
                    adapter.submitList(superheroes)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isLoading.collectLatest { isLoading ->
                    binding.apply {
                        val superheroes = viewModel.stateFlow.value
                        val layoutParams = progressBar.layoutParams as ConstraintLayout.LayoutParams
                        layoutParams.topToTop = if (superheroes.isEmpty()) ConstraintSet.PARENT_ID
                        else R.id.guideline_vertical
                        progressBar.layoutParams = layoutParams

                        if (isLoading) {
                            progressBar.visibility = View.VISIBLE
                            isUiLoading = true
                        } else {
                            progressBar.visibility = View.GONE
                            isUiLoading = false
                        }
                    }
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.typeError.collectLatest { typeError ->
                    if (typeError == TypeError.NETWORK)
                        showMessage(R.string.error_network, true)
                }
            }
        }
    }

    private fun loadSuperheroesOnScrolled() {
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val totalItemCount = gridLayoutManager.itemCount
                val lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition()

                if (!isUiLoading && totalItemCount - lastVisibleItem <= 10) {
                    val lastId = (viewModel.stateFlow.value.last().id).toInt() + 1
                    viewModel.getSuperheroes(lastId)
                }
            }
        })
    }

    private fun startDetailFragment(superhero: Superhero) {
        val action = MainFragmentDirections.actionMainFragmentToDetailFragment(superhero)
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /**
     * OnClickListener
     */
    override fun onClick(superhero: Superhero) {
        startDetailFragment(superhero)
    }

}