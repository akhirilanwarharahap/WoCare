package com.app.wocare.infoFragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import com.app.wocare.LoginActivity
import com.app.wocare.R

class FragmentInfo3 : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var btnNext: TextView
    private lateinit var tv2: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_info3, container, false)
        tv2 = v.findViewById(R.id.tv2)
        btnNext = v.findViewById(R.id.btnNext)

        val text = "guidance anytime with our <font color=#FF7272>chatbot</font>"
        tv2.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        btnNext.setOnClickListener {
            val i = Intent(activity, LoginActivity::class.java)
            activity?.startActivity(i)
            activity?.finish()
        }
        return v
    }
}