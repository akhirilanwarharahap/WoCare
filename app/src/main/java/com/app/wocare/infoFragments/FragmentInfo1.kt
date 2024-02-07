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

// TODO: Rename parameter arguments, choose names that match
class FragmentInfo1 : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var btnNext: TextView
    private lateinit var tv1: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_info1, container, false)

        btnNext = view.findViewById(R.id.btnNext)
        tv1 = view.findViewById(R.id.tv1)

        val text = "Effortlessly <font color=#FF7272>track</font> your cycle for accurate"
        tv1.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)

        btnNext.setOnClickListener{
            val secondFrag = FragmentInfo2()
            val trans: FragmentTransaction = parentFragmentManager.beginTransaction()
            trans.replace(R.id.placeholder, secondFrag)
            trans.addToBackStack(null)
            trans.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            trans.commit()
        }
        // Inflate the layout for this fragment
        return view
    }
}