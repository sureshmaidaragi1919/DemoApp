package maida.com.demoapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.facts_row_item.view.*
import maida.com.demoapp.R
import maida.com.demoapp.presenter.model.RowModel

class CountryFactsAdapter(context: Context, factsList: ArrayList<RowModel>) : RecyclerView.Adapter<FactsViewsHolder>() {

    var items: MutableList<RowModel>? = null
    var context: Context? = null

    init {
        this.items = factsList
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): FactsViewsHolder {

        val view = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_row_item, parent, false)
        return FactsViewsHolder(view)
    }

    override fun onBindViewHolder(factsViewholder: FactsViewsHolder, position: Int) {

        factsViewholder.factTitleTv.setText(items?.get(position)?.title)
        factsViewholder.factDescTv.setText(items?.get(position)?.description)
        Glide.with(context).load(items?.get(position)?.imageHref).into(factsViewholder.factImageView)

    }

    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size

    }

}

class FactsViewsHolder(mView: View) : RecyclerView.ViewHolder(mView) {
    // Holds the TextView that will add each Data to
    val factImageView = mView.facts_img
    val factTitleTv = mView.facts_title
    val factDescTv = mView.facts_desc
}
