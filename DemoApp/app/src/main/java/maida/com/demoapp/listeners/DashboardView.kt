package maida.com.demoapp.listeners

open interface DashboardView {
    fun screenRoated(): Boolean
    fun showUserError(s: String)
}