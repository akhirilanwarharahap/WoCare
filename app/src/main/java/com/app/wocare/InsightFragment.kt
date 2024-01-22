package com.app.wocare

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.wocare.adapters.InsightAdapter
import com.app.wocare.models.News
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class InsightFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var rv: RecyclerView
    private lateinit var loadingBar: ProgressBar
    lateinit var adapter: InsightAdapter
    var listData: MutableList<News> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_insight, container, false)

        //  difine id
        rv = v.findViewById(R.id.rv)
        loadingBar = v.findViewById(R.id.pb)

        adapter = InsightAdapter(listData, requireContext())
        adapter.setHasStableIds(true)
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter
        rv.setHasFixedSize(true)

        callListNewsFromFirebase()
        return v
    }

    private fun callListNewsFromFirebase() {
        val query = FirebaseDatabase.getInstance().getReference("Berita")
        query.addValueEventListener(object: ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                adapter.clear()
                for (listBerita in snapshot.children){
                    val list = listBerita.getValue(News::class.java)
                    if (list != null){
                        listData.add(list)
                    } else {
                        Toast.makeText(requireContext(), "List Kosong...", Toast.LENGTH_SHORT).show()
                    }
                    adapter.notifyDataSetChanged()
                    checkList()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun checkList() {
        if (adapter.itemCount == 0){
            loadingBar.visibility = View.VISIBLE
        } else {
            loadingBar.visibility = View.GONE
        }
    }
}