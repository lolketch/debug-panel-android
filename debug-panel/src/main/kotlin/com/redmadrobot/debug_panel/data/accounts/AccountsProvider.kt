package com.redmadrobot.debug_panel.data.accounts

import com.redmadrobot.debug_panel.data.DebugDataLoadingStrategy
import com.redmadrobot.debug_panel.data.accounts.model.DebugUserCredentials
import io.reactivex.Single

class AccountsProvider(private val accountsLoadStrategy: DebugDataLoadingStrategy<DebugUserCredentials>) {

    fun getAccounts(): Single<List<DebugUserCredentials>> {
        return accountsLoadStrategy.loadData()
    }
}
