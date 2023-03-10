package com.example.movieproject.ui

//import com.example.movieproject.databinding.MainFragmentBinding
//import com.example.movieproject.databinding.MovieDetailBinding
import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.R as R1
import com.example.movieproject.model.ConstantFile
import com.example.movieproject.util.OnItemClickListener
import com.example.movieproject.util.PaginationScrollListener


@SuppressLint("NotifyDataSetChanged")
class MainFragment : Fragment(R1.layout.main_fragment), OnItemClickListener {

    companion object {
        const val KEY = "3be8af6ebf124ffe81d90f514e59856c"
    }

    private val viewModel: MainViewModel by viewModels()

//    private lateinit var binding: MainFragmentBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var buttonSearch: Button
    private lateinit var gridButton2: MenuItem
    private lateinit var favIconButton: MenuItem
    private var initialString = ""
    private lateinit var adapter: GameAdapter
    private lateinit var gridAdapter: GameAdapter
    private var currentPage = 1

    private lateinit var textView: TextView
    private lateinit var editText: EditText
//    private var layout_flag = 1


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("ONVIEWCREATED")
        setHasOptionsMenu(true)
        adapter = GameAdapter(R1.layout.item_list, this, activity)
        gridAdapter = GameAdapter(R1.layout.item_list2, this, activity)
        initViews(view)
        observeViewModel()
        initResultRecyclerView()


        //horizontal rotate'de bi satırda 2 oyun olsun diye
        val recyclerView = view.findViewById<RecyclerView>(com.example.movieproject.R.id.mRecyclerView)

        // Set the layout manager on the RecyclerView if the device is in landscape orientation
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
    }



    override fun onPause() {
        super.onPause()
        println("PAUSE")

        val sharedPref = activity?.getSharedPreferences(ConstantFile.SHARED_PREFS, Context.MODE_PRIVATE)
        if (recyclerView.adapter == adapter) {
            sharedPref?.edit()?.putInt("layoutFlag", 1)?.apply()
        } else if (recyclerView.adapter == gridAdapter) {
            sharedPref?.edit()?.putInt("layoutFlag", 2)?.apply()
        }
    }



    private fun initViews(view: View) {
        println("initVIWS")

        currentPage = 1
        viewModel.getGames(KEY, initialString, currentPage, true)
        recyclerView = view.findViewById(R1.id.mRecyclerView)
        buttonSearch = view.findViewById(R1.id.search_button)
        buttonSearch.setOnClickListener {
            currentPage = 1
            val prompt = view.findViewById<EditText>(R1.id.prompt)
            initialString = prompt.text.toString()

            viewModel.getGames(KEY, initialString, currentPage, true)
            recyclerView.layoutManager?.scrollToPosition(0)
        }
    }

    override fun onItemClick(position: Int) {
        println("ONITEMCLIK")

        val idGame = viewModel.getGameId(position)
        val action = idGame?.let { MainFragmentDirections.actionMainFragmentToDetailFragment(it) }
        view?.let {
            if (action != null) {
                Navigation.findNavController(it).navigate(action)
            }
        }
    }

    private fun initResultRecyclerView() {
        println("initResultRecyclerView")
        val linearLayoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.layoutManager?.let {
            recyclerView.addOnScrollListener(object : PaginationScrollListener(it) {
                override fun loadMoreItems() {
                    println("LOADMOREITEMS")
                    currentPage += 1
                    viewModel.getGames(KEY, initialString, currentPage, false)
                    isLoading = false
                }

                override val isLastPage: Boolean = false
                override var isLoading: Boolean = false
            })
        }
    }

    private fun observeViewModel() {
        println("observeViewModel")

        viewModel.gameLiveData.observe(viewLifecycleOwner) {
            adapter.apply {
                gatheredGames = it
                notifyDataSetChanged()
            }
            gridAdapter.apply {
                gatheredGames = it
                notifyDataSetChanged()
            }
        }
        viewModel.errorStateLiveData.observe(this) {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        println("onPrepareOptionsMenu")

        val linearLayoutManager = LinearLayoutManager(activity)


            recyclerView.adapter = adapter
            recyclerView.layoutManager = linearLayoutManager

        favIconButton = menu.findItem(R1.id.fav_icon)
        favIconButton.setOnMenuItemClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(MainFragmentDirections.actionMainFragmentToFavoriteFragment()) }
            return@setOnMenuItemClickListener true
        }
        initResultRecyclerView()

        super.onPrepareOptionsMenu(menu)
    }




}
