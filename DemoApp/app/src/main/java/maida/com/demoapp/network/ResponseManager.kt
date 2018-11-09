package maida.com.demoapp.network

import maida.com.demoapp.presenter.model.CountryModel
import maida.com.demoapp.presenter.model.RowModel
import org.json.JSONObject

class ResponseManager {

    /*
        Class to parse json response for various methods
    */
    companion object {
        fun parseCountryFactsRespo(countryFactsRespo: String): CountryModel? {
            var mCountryModel: CountryModel? = null

            if (!countryFactsRespo.isNullOrEmpty()) {

                try {
                    val jsObject = JSONObject(countryFactsRespo)
                    var countryName = ""
                    var rowModelList = ArrayList<RowModel>()
                    if (jsObject.has("title")) {
                        countryName = jsObject.getString("title")
                    }
                    if (jsObject.has("rows")) {

                        var rowsArray = jsObject.getJSONArray("rows")
                        if (rowsArray.length() > 0) {
                            for (i in 0 until rowsArray.length()) {

                                var title = if (rowsArray.getJSONObject(i).has("title")) rowsArray.getJSONObject(i).getString("title") else ""
                                var description = if (rowsArray.getJSONObject(i).has("description")) rowsArray.getJSONObject(i).getString("description") else ""
                                var imageHref = if (rowsArray.getJSONObject(i).has("imageHref")) rowsArray.getJSONObject(i).getString("imageHref") else ""

                                if (title.isNullOrEmpty() || title.equals("null")) {
                                    title = "No title" //Currently I am hardcoding we can keep this validations in string.xml
                                }
                                if (description.isNullOrEmpty() || description.equals("null")) {
                                    description = "No description" //Currently I am hardcoding we can keep this validations in string.xml
                                }
                                rowModelList.add(RowModel(title, description, imageHref))
                            }
                        }
                    }
                    mCountryModel = CountryModel(countryName, rowModelList)

                    return mCountryModel!!
                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
            }
            return mCountryModel
        }
    }
}