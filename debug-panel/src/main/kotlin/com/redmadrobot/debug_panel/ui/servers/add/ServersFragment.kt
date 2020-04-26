package com.redmadrobot.debug_panel.ui.servers.add

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.redmadrobot.debug_panel.R
import com.redmadrobot.debug_panel.extension.observe
import com.redmadrobot.debug_panel.extension.obtainShareViewModel
import com.redmadrobot.debug_panel.internal.DebugPanel
import com.redmadrobot.debug_panel.ui.base.BaseFragment
import com.redmadrobot.debug_panel.ui.servers.item.DebugServerItem
import com.redmadrobot.debug_panel.ui.view.ItemTouchHelperCallback
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.fragment_add_server.*

class ServersFragment : BaseFragment(R.layout.fragment_add_server) {

    companion object {
        fun getInstance() = ServersFragment()
    }

    private val serversAdapter = GroupAdapter<GroupieViewHolder>()

    private val serversViewModel by lazy {
        obtainShareViewModel {
            DebugPanel.getContainer().createServersViewModel()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(serversViewModel.servers, ::setServerList)
        serversViewModel.loadServers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setViews()
    }

    private fun setViews() {
        server_list.layoutManager = LinearLayoutManager(requireContext())
        server_list.adapter = serversAdapter

        val itemTouchHelperCallback = ItemTouchHelperCallback(
            onSwiped = { position ->
                /*remove server from DB*/
                val item = serversAdapter.getItem(position) as DebugServerItem
                serversViewModel.removeServer(item)
            },
            canBeSwiped = { position ->
                serversAdapter.getGroupAtAdapterPosition(position) == addedServersSection &&
                        serversAdapter.getItem(position) is DebugServerItem
            }
        )

        ItemTouchHelper(itemTouchHelperCallback).apply {
            attachToRecyclerView(server_list)
        }

        add_server.setOnClickListener {
            ServerHostDialog.show(childFragmentManager)
        }

        serversAdapter.setOnItemClickListener { item, _ ->
            handleItemClick(item as DebugServerItem)
        }
    }

    private fun handleItemClick(item: DebugServerItem) {
        val host = item.debugServer.url
        val bundle = Bundle().apply {
            putString(ServerHostDialog.HOST, host)
        }
        ServerHostDialog.show(childFragmentManager, bundle)
    }

    private fun setServerList(servers: List<Item>) {
        serversAdapter.update(servers)
    }
}
