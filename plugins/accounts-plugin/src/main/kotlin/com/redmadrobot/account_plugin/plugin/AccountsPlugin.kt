package com.redmadrobot.account_plugin.plugin

import androidx.fragment.app.Fragment
import com.redmadrobot.account_plugin.authenticator.DebugAuthenticator
import com.redmadrobot.account_plugin.authenticator.DefaultAuthenticator
import com.redmadrobot.account_plugin.ui.add.AccountsFragment
import com.redmadrobot.account_plugin.ui.select.AccountSelectionFragment
import com.redmadrobot.core.CommonContainer
import com.redmadrobot.core.data.PreInstalledData
import com.redmadrobot.core.data.storage.entity.DebugAccount
import com.redmadrobot.core.plugin.Plugin
import com.redmadrobot.core.plugin.PluginDependencyContainer

class AccountsPlugin(
    private val preInstalledAccounts: PreInstalledData<DebugAccount> = PreInstalledData(
        emptyList()
    ),
    val debugAuthenticator: DebugAuthenticator = DefaultAuthenticator()
) : Plugin() {

    companion object {
        const val NAME = "ACCOUNTS"
    }

    override fun getName() = NAME

    override fun getPluginContainer(commonContainer: CommonContainer): PluginDependencyContainer {
        return AccountsPluginContainer(
            preInstalledAccounts,
            commonContainer
        )
    }

    override fun getFragment(): Fragment? {
        return AccountSelectionFragment()
    }

    override fun getSettingFragment(): Fragment? {
        return AccountsFragment()
    }
}
