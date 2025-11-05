package com.Eorico_Login.Login_Sample

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.text.InputType
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private lateinit var sharedPref: android.content.SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val etUsername = view.findViewById<EditText>(R.id.usernameLog_input)
        val etPassword = view.findViewById<EditText>(R.id.passwordLog_input)
        val btnLogin = view.findViewById<Button>(R.id.button_login)
        val tvGoToRegister = view.findViewById<TextView>(R.id.textCreateAcc)


        var isPasswordVisible = false
        etPassword.setOnTouchListener { _, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (etPassword.right - etPassword.compoundDrawables[DRAWABLE_END].bounds.width())) {
                    isPasswordVisible = !isPasswordVisible
                    if (isPasswordVisible) {
                        etPassword.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_visibility, 0
                        )
                    } else {
                        etPassword.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        etPassword.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_visibility_off, 0
                        )
                    }
                    etPassword.setSelection(etPassword.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }

        // 🔹 Login Button
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            val savedUsername = sharedPref.getString("username", null)
            val savedPassword = sharedPref.getString("password", null)

            val passwordPattern = Regex("^[A-Z][A-Za-z0-9]{7,}$")

            when {
                username.isEmpty() || password.isEmpty() -> {
                    Toast.makeText(
                        requireContext(),
                        "lagay ka muna user at pass mo!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                !username.endsWith("@gmail.com") && !username.endsWith("@yahoo.com") -> {
                    Toast.makeText(
                        requireContext(),
                        "Yung email nga dapat may gmail o yahoo.",
                        Toast.LENGTH_SHORT
                    ).show()
                    etUsername.setText("")
                }

                !password.matches(passwordPattern) -> {
                    Toast.makeText(
                        requireContext(),
                        "Ung pass dapat naguumpisa sa malaki at dapat 8 character hays!",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                username == savedUsername && password == savedPassword -> {
                    Toast.makeText(requireContext(), "Login Chocolate", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_Login_to_Home)
                }

                else -> {
                    Toast.makeText(requireContext(), "Mali yan man, aray ko!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

        tvGoToRegister.setOnClickListener {
            findNavController().navigate(R.id.action_Login_to_Register)
        }

        return view
    }
}
