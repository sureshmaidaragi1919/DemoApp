package maida.com.demoapp.presenter

import maida.com.demoapp.listeners.DashboardView
import maida.com.demoapp.threads.GetCountryFactsThread
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.runners.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DashboardActivityPresenterTest {

    @Mock
    private lateinit var dashboardView: DashboardView
    @Mock
    private lateinit var getCountryFactsThread: GetCountryFactsThread
    @Mock
    private lateinit var dashboardActivityPresenter: DashboardActivityPresenter

    @Before
    fun setUp() {
        dashboardActivityPresenter = DashboardActivityPresenter(dashboardView, getCountryFactsThread)
    }

    @Test
    fun shouldShowErrorMessageScreenRoated() {
        if (dashboardView.screenRoated()) {
            dashboardActivityPresenter.onUpdateAdp()
        } else {
            Mockito.verify(dashboardView).showUserError("Not able to load data due screen roated please swipe down to refresh")
        }
    }

    @Test
    fun showIsNetworkAvailable() {
        if (dashboardView.checkNetworkState()) {
            dashboardActivityPresenter.onUpdateAdp()
        } else {
            Mockito.verify(dashboardView).showUserError("Internet connection not available")
        }
    }
}