package com.ieeevit.enigma7.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ieeevit.enigma7.R
import com.ieeevit.enigma7.utils.ConnectionStateMonitor

open class BaseActivity : AppCompatActivity(), ConnectionStateMonitor.OnNetworkAvailableCallbacks {
    private lateinit var builder: AlertDialog.Builder
    private lateinit var customInflater: LayoutInflater
    private var connectionStateMonitor: ConnectionStateMonitor? = null
    var networkIsAvailable = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        customInflater = layoutInflater
        builder = AlertDialog.Builder(this)
    }

    override fun onResume() {
        super.onResume()
        if (connectionStateMonitor == null)
            connectionStateMonitor = ConnectionStateMonitor(this, this)
        connectionStateMonitor?.enable()
        if (connectionStateMonitor?.hasNetworkConnection() == false) onNegative()
        else onPositive()
    }

    override fun onPause() {
        connectionStateMonitor?.disable()
        connectionStateMonitor = null
        super.onPause()
    }

    override fun onPositive() {
        networkIsAvailable = 1
    }

    override fun onNegative() {
        runOnUiThread {
            showAlertDialog(R.layout.internet_connection_lost_dialog)
        }
        networkIsAvailable = 0
    }

    private fun showAlertDialog(layoutId: Int) {
        val customLayout: View = customInflater.inflate(layoutId, null)
        builder.setView(customLayout)
        val alert = builder.create()
        alert.show()
    }
}