package maida.com.demoapp.view

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_dashboard.*
import maida.com.demoapp.R
import maida.com.demoapp.adapters.CountryFactsAdapter
import maida.com.demoapp.listeners.DashboardView
import maida.com.demoapp.network.ResponseManager
import maida.com.demoapp.presenter.DashboardActivityPresenter
import maida.com.demoapp.presenter.model.CountryModel
import maida.com.demoapp.presenter.model.RowModel
import maida.com.demoapp.threads.GetCountryFactsThread
import maida.com.demoapp.util.CommonUtils


class DashboardActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener, DashboardView {


    /* Pagination can be handled if API response is giving following parameters
     *
     * 1. Page-number
     * 2. Total-count
     * 3. Offset
     *
     * Sorry Current API not giving any pagination data so I am ignoring the pagination for this app
     *
     *
     * For UI unit test I have used mockito framework
     *
     * Important note :please make sure to uncomment dashboardActivityPresenter object from this activity while doing unit test
     * */


    var mCountryModel: CountryModel? = null
    var mIncomingHandler = Handler(IncomingHandlerCallback())
    var countryFactsAdapter: CountryFactsAdapter? = null
    var factrRowsList: ArrayList<RowModel>? = null
    val llm = LinearLayoutManager(this)

    internal inner class IncomingHandlerCallback : Handler.Callback {

        override fun handleMessage(message: Message): Boolean {

            swipe_refresh_layout.isRefreshing = false
            if (message.what == GetCountryFactsThread.GETCOUNTRYFACTSTHREAD_SUCCESS) {
                val countryFactRespo = message.obj as String
                mCountryModel = ResponseManager.parseCountryFactsRespo(countryFactRespo)

                updateAdp(mCountryModel!!)

            } else if (message.what == GetCountryFactsThread.GETCOUNTRYFACTSTHREAD_FAIL) {
                //Write code handle if any session expiry
                showSnackBar(resources.getString(R.string.no_internet))
            }
            return true
        }
    }

    lateinit var dashboardActivityPresenter: DashboardActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        /*    As I am having one datamodel in API response that's rows,
              I am going to keep simple screen rotation using onsavedinstate
              incase if there are many data models we can use live data/Interface  for handling screen rotation

            */
        if (savedInstanceState != null) {
            //When rotation occurs

        } else {
            //when first time activity loaded
            loadRecylerViewdata()
            // dashboardActivityPresenter = DashboardActivityPresenter(this, GetCountryFactsThread())//Uncomment during unit testing
        }

        swipe_refresh_layout.setOnRefreshListener(this);
        swipe_refresh_layout.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_red_light);
    }

    //Handle screen orientation to avoid making network calls
    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putSerializable("myData", mCountryModel)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        mCountryModel = savedInstanceState?.getSerializable("myData") as CountryModel?
        updateAdp(mCountryModel!!);

    }

    override fun onRefresh() {
        loadRecylerViewdata()
    }

    private fun loadRecylerViewdata() {
        if (CommonUtils.isInternetAvailable(this)) {
            swipe_refresh_layout.isRefreshing = true
            GetCountryFactsThread(this, mIncomingHandler).start()
        } else {
            swipe_refresh_layout.isRefreshing = false
            showSnackBar(resources.getString(R.string.no_internet))
        }
    }

    private fun updateAdp(countryModel: CountryModel) {
//        dashboardActivityPresenter.onUpdateAdp()  //Uncomment during unit testing
        factrRowsList = countryModel.rows as ArrayList<RowModel>?
        setTitle(mCountryModel?.title)
        countryFactsAdapter = CountryFactsAdapter(this, factrRowsList!!)
        llm.orientation = LinearLayoutManager.VERTICAL
        country_rcylrv?.setLayoutManager(llm)
        country_rcylrv?.adapter = countryFactsAdapter
    }

    //Run this using Junit.4
    override fun screenRoated(): Boolean {
        //dashboardActivityPresenter.onUpdateAdp() //Uncomment during unit testing
        return true
    }

    override fun showUserError(s: String) {
        Log.e("DashboardActivity", s)
    }

    fun showSnackBar(userMessage: String) {
        val snackbar = Snackbar
                .make(dashboard_cordlyt, userMessage, Snackbar.LENGTH_INDEFINITE)
                .setAction(resources.getString(R.string.lable_retry), object : View.OnClickListener {
                    override fun onClick(view: View) {
                        val snackbar1 = Snackbar.make(swipe_refresh_layout, resources.getString(R.string.lable_trying), Snackbar.LENGTH_LONG)
                        snackbar1.show()
                        onRefresh()
                    }
                })

        snackbar.show()
    }
}
