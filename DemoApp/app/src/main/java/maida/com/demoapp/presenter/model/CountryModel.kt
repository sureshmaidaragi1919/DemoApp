package maida.com.demoapp.presenter.model

import java.io.Serializable

data class CountryModel(
        val title: String,
        val rows: List<RowModel>
) : Serializable