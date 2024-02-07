package com.app.wocare.infoFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentTransaction
import com.app.wocare.R

class FragmentInfo2 : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var btnNext: TextView
    private lateinit var tv1: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info2, container, false)

        tv1 = view.findViewById(R.id.tv1)
        btnNext = view.findViewById(R.id.btnNext)

        val text = "Instanly <font color=#FF7272>detect</font> menstrual blood"
        tv1.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        btnNext.setOnClickListener{
            val thirdFrag = FragmentInfo3()
            val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
            trans.replace(R.id.placeholder, thirdFrag)
            trans.addToBackStack(null)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            trans.commit()
        }
        // Inflate the layout for this fragment
        return view
    }
}