package com.example.movieproject.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.example.movieproject.R
import com.example.movieproject.databinding.GameDetailBinding
import com.example.movieproject.model.Favorite
import com.example.movieproject.model.GameDetails
import com.google.android.material.bottomnavigation.BottomNavigationView

class DetailFragment : Fragment(R.layout.game_detail) {
/*
    private lateinit var buttonFav: Button
    private var imageView: ImageView? = null
    private var textViewTitle: TextView? = null
    private var textViewBudget: TextView? = null
    private var textViewLanguage: TextView? = null
    private var textViewRevenue: TextView? = null
    private var textViewGenre: TextView? = null
    private var textViewProduction: TextView? = null
    private var textViewStatus: TextView? = null
    private var textViewReleaseDate: TextView? = null
    private var textViewScore: TextView? = null
    private var textViewVoteCount: TextView? = null
    private var textViewRuntime: TextView? = null
    private lateinit var textViewOverview: TextView */
    private lateinit var game: GameDetails
    private lateinit var idGameGathered: String
    private lateinit var binding: GameDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private lateinit var homeButton: MenuItem
    private lateinit var favIconButton: MenuItem
    private lateinit var backButton: Button
        private  lateinit var bottomNav: BottomNavigationView

    private var websiteLink = ""
    private var redditLink = ""
    private lateinit var detailViewModel: DetailViewModel

    private var _binding: GameDetailBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        binding = GameDetailBinding.inflate(layoutInflater)


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = GameDetailBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        idGameGathered = args.idMovie
        getGameById()
        observeGame()
        setOnClickFloatingActionButton()
        showLoading()
    }


    private fun showLoading() {

        binding.mImageView.visibility = View.GONE
        binding.mTextViewName.visibility = View.GONE
        binding.mTextViewInstruction.visibility = View.GONE

    }




    private fun getGameById() {
        println( "IDDD detail   " +idGameGathered)
        println( "IDDD  detail to string " +idGameGathered)

        detailViewModel.getGameById(idGameGathered)
        println( "DETAIL VIRW " +detailViewModel)


    }

    private fun observeGame() {

        detailViewModel.observeGame().observe(viewLifecycleOwner) {

            this.game = it
            println("DET ID  "+game.id)
            println("DET NAME "+game.name)

            websiteLink = it.website
            redditLink = it.reddit_url


            setViews()
            hideLoading()

        }

    }


    private fun hideLoading() {

        binding.mImageView.visibility = View.VISIBLE
        binding.mTextViewName.visibility = View.VISIBLE
        binding.mTextViewInstruction.visibility = View.VISIBLE

    }


    private fun setViews() {

        binding.apply {
            binding.mImageView.load(game.background_image) {
                placeholder(R.color.purple_200)
                error(R.color.purple_200)

            }


            println("SET VIEWWSSSS")
            mTextViewName.text = game.name
            mTextViewInstruction.text = game.description


        }

    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.home_icon).isVisible = true
        menu.findItem(R.id.fav_icon).isVisible = true

        homeButton = menu.findItem(R.id.home_icon)
        favIconButton = menu.findItem(R.id.fav_icon)
        homeButton.setOnMenuItemClickListener {
            val fm: FragmentManager = requireActivity().supportFragmentManager
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(DetailFragmentDirections.actionDetailFragmentToMainFragment())
            }
            return@setOnMenuItemClickListener true
        }
        favIconButton.setOnMenuItemClickListener {
            view?.let { it1 ->
                Navigation.findNavController(it1)
                    .navigate(DetailFragmentDirections.actionDetailFragmentToFavoriteFragment())
            }
            return@setOnMenuItemClickListener true
        }
        super.onPrepareOptionsMenu(menu)
    }
    private fun insertFavorite() {

        val favorite = Favorite(
            id = game.id!!.toInt(),
            name = game.name!!,
            imageLink = game.background_image!!,
            meta = game.metacritic!!
        )

        detailViewModel.insertFavorite(favorite)

    }

    private fun deleteFavoriteById() {

        detailViewModel.deleteFavoriteById(idGameGathered)

    }


    private fun isFavorite(): Boolean {

        return detailViewModel.isFavorite(idGameGathered)

    }
    private fun setOnClickFloatingActionButton() {

        binding.favorite.setOnClickListener {

            if (isFavorite()) {

                deleteFavoriteById()
                binding.favorite.text = "Favourite"

            } else {

                insertFavorite()
                binding.favorite.text = "Favorited"

            }

        }



                binding.gamesBack.setOnClickListener{
                    val dir = DetailFragmentDirections.actionDetailFragmentToMainFragment()
                    findNavController().navigate(dir)
                }

                binding.website?.setOnClickListener{

                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(websiteLink)))

                }

                binding.reddit?.setOnClickListener{

                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(redditLink)))

                }

    }




}
