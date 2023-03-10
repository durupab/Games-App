package com.example.movieproject.ui

import android.app.AlertDialog
import android.content.res.Configuration
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieproject.R
import com.example.movieproject.databinding.GameFavoriteBinding

class FavoriteFragment : Fragment(R.layout.game_favorite) {

    private val viewModel: FavViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var homeButton: MenuItem
    private lateinit var gridButton2: MenuItem
    private lateinit var favadapter: FavoriteAdapter

    private lateinit var gridAdapter: GameAdapter
    private var _binding: GameFavoriteBinding? = null
    private val binding get() = _binding!!

    private var currentPage = 1
    private var fav_layout_flag = 1
    private lateinit var favoriteViewModel: FavViewModel





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favoriteViewModel = ViewModelProvider(this)[FavViewModel::class.java]
        favadapter = FavoriteAdapter()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GameFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpRecyclerView()
        binding.text.text = "Favorites "
        observeFavorites()
        val recyclerView = view.findViewById<RecyclerView>(R.id.favRecyclerView)

        // Set the layout manager on the RecyclerView if the device is in landscape orientation
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.layoutManager = GridLayoutManager(context, 2)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setUpRecyclerView() {

        binding.favRecyclerView.apply {
            adapter = favadapter
        }




        val itemSwipe = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.RIGHT
        ){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }


            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {


                val builder = AlertDialog.Builder(activity)
                builder.setTitle("Delete Item.")
                builder.setMessage("Are you sure?")
                val pos = viewHolder.adapterPosition

                val id = (favadapter.getPos(pos).id)
                println("dhsjhjds   "   + id)

                builder.setPositiveButton("Confirm"){dialog, which ->
                    favoriteViewModel.deleteFavoriteById(id.toString())

                    println("After   " + favadapter.itemCount)

                    favadapter.notifyItemRemoved(pos)

                }

                builder.setNegativeButton("Cancel"){dialog, which ->
                    favadapter.notifyItemChanged(pos)

                }
                builder.show()

            }


        }

        val swap = ItemTouchHelper(itemSwipe)
        swap.attachToRecyclerView(binding.favRecyclerView)



    }

    private fun observeFavorites() {

        viewModel.observeFavorites().observe(viewLifecycleOwner) {

            favadapter.setFavorites(it)

        }

    }



    override fun onPrepareOptionsMenu(menu: Menu) {
        println("onPrepareOptionsMenu")

        val linearLayoutManager = LinearLayoutManager(activity)


        recyclerView.adapter = favadapter
        recyclerView.layoutManager = linearLayoutManager

        homeButton = menu.findItem(R.id.home_icon)
        homeButton.setOnMenuItemClickListener {
            view?.let { it1 -> Navigation.findNavController(it1).navigate(FavoriteFragmentDirections.actionFavoriteFragmentToMainFragment()) }
            return@setOnMenuItemClickListener true
        }


        super.onPrepareOptionsMenu(menu)
    }




}


