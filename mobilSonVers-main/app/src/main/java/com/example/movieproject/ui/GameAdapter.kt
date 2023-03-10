package com.example.movieproject.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.example.movieproject.R
import com.example.movieproject.model.Game
import com.example.movieproject.util.OnItemClickListener

class GameAdapter(
    private val layoutId: Int,
    private val listener: OnItemClickListener,
    private val adapterContext: Context?
) : RecyclerView.Adapter<GameAdapter.ViewHolder>() {
    var gatheredGames: List<Game>? = null

    inner class ViewHolder(elementView: View) : RecyclerView.ViewHolder(elementView), View.OnClickListener {
        private val imageView: ImageView = elementView.findViewById(R.id.imageView)
        private val textViewTitle: TextView = elementView.findViewById(R.id.name)
        private val textViewScore: TextView = elementView.findViewById(R.id.ratings)
        private val iconView: TextView = elementView.findViewById(R.id.genre)

        init {
            elementView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }


        fun bind(instanceGame: Game) {
            println("BInDDD")

            val circularProgressDrawable = CircularProgressDrawable(adapterContext!!)
            circularProgressDrawable.strokeWidth = 5f
            circularProgressDrawable.centerRadius = 30f
            circularProgressDrawable.start()
            Glide.with(imageView.context)
                .load(instanceGame.imageLink)
                .placeholder(circularProgressDrawable)
                .error(R.drawable.brokenimg)
                .into(imageView)
//            val sharedPref = adapterContext.getSharedPreferences(ConstantFile.SHARED_PREFS, Context.MODE_PRIVATE)
//            if (sharedPref != null) {
//                if (sharedPref.contains(instanceMovie.id)) {
//                    iconView.visibility = View.VISIBLE
//                } else {
//                    iconView.visibility = View.GONE
//                }
//            }
            textViewTitle.text = instanceGame.name
//            val displayScore = instanceMovie.meta
            textViewScore.text = instanceGame.meta
            var passString: String = ""
            var first_flag = true
            println("SALAMMMM")
                for (item in instanceGame.genre!!) {
                    if (!first_flag) {
                        passString += ", "
                    } else {
                        first_flag = false
                    }
                    passString += item.genreName
                    println("PASSSS   "+ passString)
                }
                iconView.text = passString
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        gatheredGames?.let {
            holder.bind(it[position])
        }
    }

//
//    fun getPos(position: Int): Movie{
//        return gatheredMovies!!.get(position)
//    }

    override fun getItemCount() : Int{

//        println("SIZEEEEE      "+ gatheredMovies?.size)
        return gatheredGames?.size ?: 0
    }
}
