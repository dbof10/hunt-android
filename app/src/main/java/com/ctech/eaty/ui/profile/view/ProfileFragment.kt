package com.ctech.eaty.ui.profile.view


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ctech.eaty.R
import com.ctech.eaty.base.BaseReduxFragment
import com.ctech.eaty.base.redux.Store
import com.ctech.eaty.di.Injectable
import com.ctech.eaty.entity.UserDetail
import com.ctech.eaty.ui.profile.action.SubmitAction
import com.ctech.eaty.ui.profile.state.ProfileState
import com.ctech.eaty.ui.profile.viewmodel.ProfileViewModel
import com.ctech.eaty.util.rx.plusAssign
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject


class ProfileFragment : BaseReduxFragment<ProfileState>(), Injectable {

    companion object {

        private val KEY_USER = "user"

        fun newInstance(): Fragment {

            val args = Bundle()

            val fragment = ProfileFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(user: UserDetail): Fragment {

            val args = Bundle()

            val fragment = ProfileFragment()
            args.putParcelable(KEY_USER, user)
            fragment.arguments = args
            return fragment
        }
    }

    @Inject
    lateinit var store: Store<ProfileState>

    @Inject
    lateinit var viewModel: ProfileViewModel

    @Inject
    lateinit var toolbar: Toolbar


    override fun store(): Store<ProfileState> {
        return store
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
        setupToolbar()
        bindUser()
    }

    private fun bindUser() {
        val user = arguments.getParcelable<UserDetail>(KEY_USER)
        etEmail.setText(user?.email ?: "", TextView.BufferType.EDITABLE)
        etName.setText(user?.name ?: "", TextView.BufferType.EDITABLE)
        etBio.setText(user?.headline ?: "", TextView.BufferType.EDITABLE)

    }

    private fun setupToolbar() {

        toolbar.setOnMenuItemClickListener { item: MenuItem ->
            if (item.itemId == R.id.action_edit) {
                store.dispatch(SubmitAction(etEmail.text.toString(), etName.text.toString(), etBio.text.toString()))
                return@setOnMenuItemClickListener true
            }
            false
        }
    }

    private fun setupViewModel() {
        disposables += viewModel.content().subscribe {
            activity.finish()
        }

        disposables += viewModel.loading().subscribe {
            progressBar.visibility = View.VISIBLE
        }

        disposables += viewModel.emailError().subscribe {
            ilEmail.isErrorEnabled = true
            ilEmail.error = it.message
            ilEmail.requestFocus()
        }

        disposables += viewModel.nameError().subscribe {
            ilName.isErrorEnabled = true
            ilName.error = it.message
            ilEmail.error = ""
            ilName.requestFocus()
        }

        disposables += viewModel.headlineError().subscribe {
            ilBio.isErrorEnabled = true
            ilBio.error = it.message
            ilBio.error = ""
            ilEmail.error = ""
            ilBio.requestFocus()
        }
        disposables += viewModel.submitError().subscribe {
            progressBar.visibility = View.GONE
        }
    }

}

