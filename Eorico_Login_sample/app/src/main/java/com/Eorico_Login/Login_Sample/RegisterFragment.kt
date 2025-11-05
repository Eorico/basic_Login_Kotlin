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
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class RegisterFragment : Fragment() {

    private lateinit var sharedPref: android.content.SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPref = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(
            R.layout.fragment_register,
            container,
            false
        )

        val etUsername = view.findViewById<EditText>(R.id.usernameReg_input)
        val etPassword = view.findViewById<EditText>(R.id.passwordReg_input)
        val etConfirmPass = view.findViewById<EditText>(R.id.confirmPasswordReg_input)
        val btnRegister = view.findViewById<Button>(R.id.button_Register)
        val tvGoToLogin = view.findViewById<TextView>(R.id.textGoLogin)

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

        // 🔹 Show/Hide Confirm Password toggle
        var isConfirmVisible = false
        etConfirmPass.setOnTouchListener { _, event ->
            val DRAWABLE_END = 2
            if (event.action == MotionEvent.ACTION_UP) {
                if (event.rawX >= (etConfirmPass.right - etConfirmPass.compoundDrawables[DRAWABLE_END].bounds.width())) {
                    isConfirmVisible = !isConfirmVisible
                    if (isConfirmVisible) {
                        etConfirmPass.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                        etConfirmPass.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_visibility, 0
                        )
                    } else {
                        etConfirmPass.inputType =
                            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                        etConfirmPass.setCompoundDrawablesWithIntrinsicBounds(
                            0, 0, R.drawable.ic_visibility_off, 0
                        )
                    }
                    etConfirmPass.setSelection(etConfirmPass.text.length)
                    return@setOnTouchListener true
                }
            }
            false
        }

        // 🔹 Register button logic
        btnRegister.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPass = etConfirmPass.text.toString().trim()

            val passwordPattern = Regex("^[A-Z][A-Za-z0-9]{7,}$")

            when {
                username.isEmpty() || password.isEmpty() || confirmPass.isEmpty() -> {
                    Toast.makeText(requireContext(), "Please naman lagyan mo yan!", Toast.LENGTH_SHORT).show()
                }

                !username.endsWith("@gmail.com") && !username.endsWith("@yahoo.com") -> {
                    Toast.makeText(requireContext(), "Yung email ngani dapat gmail o yahoo, hay nako!", Toast.LENGTH_SHORT).show()
                }

                !password.matches(passwordPattern) -> {
                    Toast.makeText(requireContext(), "Kailangan malaking letter at * character yang pass mo, tsk!", Toast.LENGTH_SHORT).show()
                }

                password != confirmPass -> {
                    Toast.makeText(requireContext(), "Hindi magkapareho password mo bes!", Toast.LENGTH_SHORT).show()
                }

                else -> {
                    val editor = sharedPref.edit()
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.apply()

                    Toast.makeText(requireContext(), "Nakarehistro ka na!", Toast.LENGTH_SHORT).show()
                    tvGoToLogin.performClick()
                }
            }
        }

        tvGoToLogin.setOnClickListener {
            findNavController().navigate(R.id.action_Register_to_Login)
        }

        return view
    }
}
