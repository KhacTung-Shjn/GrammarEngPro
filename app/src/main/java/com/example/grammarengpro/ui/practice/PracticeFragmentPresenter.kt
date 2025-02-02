package com.example.grammarengpro.ui.practice

import android.util.Log
import com.example.grammarengpro.MainApp
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.Practice
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class PracticeFragmentPresenter(val view: PracticeFragmentContact.View) :
    PracticeFragmentContact.Presenter {
    override fun getListPractice() {
        MainApp.getFirebaseFireStore()
            .collection("Exam")
            .orderBy("timeCreate", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                error?.let {
                    Log.d(
                        PracticeFragmentPresenter::class.java.simpleName,
                        "Listen failed.",
                        error
                    )
                    return@addSnapshotListener
                }
                value?.let { items ->
                    val listPractice = mutableListOf<Practice>()
                    for (item in items) {
                        val json = Gson().toJson(item.data)
                        if (json != null) {
                            val practice = Gson().fromJson(json, Practice::class.java)
                            listPractice.add(practice)
                        }
                    }
                    if (listPractice.size != 0) {
                        view.successGetListPractice(listPractice)
                    } else {
                        view.showMessage(R.string.msg_get_list_practice_empty)
                    }
                }
            }
    }
}