package com.redmadrobot.debug_panel.ui.accounts.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.redmadrobot.core.extension.getPlugin
import com.redmadrobot.core.extension.obtainShareViewModel
import com.redmadrobot.debug_panel.R
import com.redmadrobot.debug_panel.internal.plugin.account.AccountsPlugin
import com.redmadrobot.debug_panel.internal.plugin.account.AccountsPluginContainer
import kotlinx.android.synthetic.main.dialog_add_account.*

class AddAccountDialog : DialogFragment() {

    companion object {
        private const val KEY_ID = "ID"
        private const val KEY_LOGIN = "LOGIN"
        private const val KEY_PASSWORD = "PASSWORD"

        private const val TAG = "AddAccountDialog"

        fun show(
            fragmentManager: FragmentManager,
            id: Int? = null,
            login: String? = null,
            password: String? = null
        ) {
            AddAccountDialog().apply {
                this.arguments = bundleOf(
                    KEY_ID to id,
                    KEY_LOGIN to login,
                    KEY_PASSWORD to password
                )
            }.show(fragmentManager, TAG)
        }
    }

    private val login by lazy { arguments?.getString(KEY_LOGIN) }
    private val password by lazy { arguments?.getString(KEY_PASSWORD) }
    private val id by lazy { arguments?.getInt(KEY_ID) }

    private val isEditMode: Boolean
        get() = login != null && password != null

    private val sharedViewModel by lazy {
        obtainShareViewModel {
            getPlugin<AccountsPlugin>()
                .getContainer<AccountsPluginContainer>()
                .createAccountsViewModel()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_add_account, container, false)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setView()
        setData()
        account_login.requestFocus()
    }

    private fun setData() {
        if (!login.isNullOrEmpty() && password != null) {
            account_login.setText(login)
            account_password.setText(password)
        }
    }

    private fun setView() {
        save_account_button.setOnClickListener {
            if (dataIsValid()) save()
        }
    }

    private fun save() {
        val login = account_login.text.toString()
        val password = account_password.text.toString()
        if (isEditMode) {
            update(login, password)
        } else {
            saveNew(login, password)
        }
        dialog?.dismiss()
    }

    private fun update(login: String, password: String) {
        id?.let { id ->
            sharedViewModel.updateAccount(id, login, password)
        }
    }

    private fun saveNew(login: String, password: String) {
        sharedViewModel.saveAccount(login, password)
    }

    private fun dataIsValid(): Boolean {
        //TODO Добавить валидацию данных
        return true
    }
}
