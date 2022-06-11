package com.dicoding.bintangpr.clearvis.view.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import arrow.core.Either
import arrow.core.getOrElse
import com.dicoding.bintangpr.clearvis.databinding.FragmentProfileBinding
import com.dicoding.bintangpr.clearvis.view.login.LoginActivity
import io.github.nefilim.kjwt.JWT
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding
    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.getUser().observe(viewLifecycleOwner) { user ->
            setUpView(user.accessToken)
        }

        binding?.logoutBtn?.setOnClickListener {
            profileViewModel.logout()
            Intent(requireContext(), LoginActivity::class.java).also { intent ->
                startActivity(intent)
                requireActivity().finish()
            }
        }

    }

    private fun setUpView(token: String) {
        val getName = JWT.decode(token).map {
            it.claimValue("name").getOrElse { "" }
        }
        val getEmail = JWT.decode(token).map {
            it.claimValue("email").getOrElse { "" }
        }
        val name = when (getName) {
            is Either.Right -> getName.value
            else -> "error"
        }
        val email = when (getEmail) {
            is Either.Right -> getEmail.value
            else -> "error"
        }

        binding?.nameTv?.text = name
        binding?.emailEt?.text = email
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}