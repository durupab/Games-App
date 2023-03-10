package com.example.movieproject.ui



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.databinding.ItemList2Binding
import com.example.movieproject.model.Favorite

class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.MyFavoriteViewHolder>() {

    private var favorites: List<Favorite> = ArrayList()
    private lateinit var onClickMaterialCardView: OnClickMaterialCardView

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyFavoriteViewHolder {
        return MyFavoriteViewHolder(ItemList2Binding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(myFavoriteViewHolder: MyFavoriteViewHolder, position: Int) {

        val favorite = favorites[position]

        myFavoriteViewHolder.binding.apply {


            Glide.with(mImageView.context)
                .load(favorite.imageLink)
                .error(R.drawable.brokenimg)
                .into(mImageView)
            mTextViewName.text = favorite.name
            ratings.text = favorite.meta



        }

        myFavoriteViewHolder.itemView.setOnClickListener {
            onClickMaterialCardView.onClick(favorite)
        }

    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    class MyFavoriteViewHolder(val binding: ItemList2Binding): RecyclerView.ViewHolder(binding.root)


    fun setFavorites(favorites: List<Favorite>) {
        this.favorites = favorites
        notifyDataSetChanged()
    }

    fun setOnClickMaterialCardView(onClickMaterialCardView: OnClickMaterialCardView) {
        this.onClickMaterialCardView = onClickMaterialCardView
    }

    interface OnClickMaterialCardView {
        fun onClick(favorite: Favorite)
    }


    fun getPos(position: Int): Favorite{
        return favorites.get(position)
    }

}