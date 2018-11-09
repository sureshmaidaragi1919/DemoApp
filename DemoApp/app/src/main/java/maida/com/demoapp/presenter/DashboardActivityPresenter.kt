package maida.com.demoapp.presenter

import maida.com.demoapp.listeners.DashboardView
import maida.com.demoapp.threads.GetCountryFactsThread

class DashboardActivityPresenter {


    private lateinit var dashboardView: DashboardView
    private lateinit var getCountryFactsThread: GetCountryFactsThread

    constructor(dasbhoardView: DashboardView, getCountryFactsThread: GetCountryFactsThread) {
        this.dashboardView = dasbhoardView
        this.getCountryFactsThread = getCountryFactsThread

    }

    fun onUpdateAdp() {
    }

}