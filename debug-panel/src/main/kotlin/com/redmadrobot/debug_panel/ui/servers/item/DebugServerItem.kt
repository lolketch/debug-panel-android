package com.redmadrobot.debug_panel.ui.servers.item

import androidx.core.view.isVisible
import com.redmadrobot.debug_panel.R
import com.redmadrobot.debug_panel.data.storage.entity.DebugServer
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_debug_server.view.*

class DebugServerItem(var debugServer: DebugServer) : Item() {

    override fun getLayout() = R.layout.item_debug_server

    override fun bind(viewHolder: GroupieViewHolder, position: Int) = with(viewHolder) {
        containerView.item_server_url.text = debugServer.url
        containerView.is_selected_icon.isVisible = debugServer.isSelected
    }

    fun update(debugServer: DebugServer) {
        this.debugServer = debugServer
        notifyChanged()
    }
}
