package com.redmadrobot.debug_panel.ui.servers

import androidx.lifecycle.MutableLiveData
import com.redmadrobot.debug_panel.data.servers.DebugServerRepository
import com.redmadrobot.debug_panel.data.servers.DebugServersProvider
import com.redmadrobot.debug_panel.data.storage.entity.DebugServer
import com.redmadrobot.debug_panel.extension.observeOnMain
import com.redmadrobot.debug_panel.extension.zipList
import com.redmadrobot.debug_panel.ui.base.BaseViewModel
import com.redmadrobot.debug_panel.ui.servers.item.DebugServerItem
import com.xwray.groupie.kotlinandroidextensions.Item
import io.reactivex.rxkotlin.subscribeBy

class ServersViewModel(
    private val serversRepository: DebugServerRepository,
    private val localServersProvider: DebugServersProvider,
    private val preInstalledServersProvider: DebugServersProvider
) : BaseViewModel() {

    val servers = MutableLiveData<List<Item>>()

    fun loadServers() {
        preInstalledServersProvider.loadServers()
            .zipList(localServersProvider.loadServers())
            .map { it.map(::DebugServerItem) }
            .observeOnMain()
            .subscribeBy(onSuccess = { servers.value = it })
            .autoDispose()
    }

    fun addServer(host: String) {
        val server = DebugServer(host)
        serversRepository.addServer(server)
            .observeOnMain()
            .subscribeBy(onComplete = {
                addServerToEndOfList(server)
            })
            .autoDispose()
    }

    private fun addServerToEndOfList(server: DebugServer) {
        val serverList = servers.value?.toMutableList()
        serverList?.add(DebugServerItem(server))
        servers.value = serverList
    }
}
