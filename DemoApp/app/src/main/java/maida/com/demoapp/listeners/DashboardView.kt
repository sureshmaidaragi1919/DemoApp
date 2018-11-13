package maida.com.demoapp.listeners

  interface DashboardView {
    fun screenRoated(): Boolean
    fun showUserError(s: String)
    fun checkNetworkState():Boolean
}