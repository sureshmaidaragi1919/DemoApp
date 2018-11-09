package maida.com.demoapp.presenter.model

import java.io.Serializable

data class RowModel(
        val title: String,
        val description: String,
        val imageHref: Any
):Serializable