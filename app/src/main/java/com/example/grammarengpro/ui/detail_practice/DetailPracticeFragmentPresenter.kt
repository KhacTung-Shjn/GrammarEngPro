package com.example.grammarengpro.ui.detail_practice

import android.util.Log
import com.example.grammarengpro.MainApp
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.DetailExam
import com.example.grammarengpro.data.model.Practice
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class DetailPracticeFragmentPresenter(val view: DetailPracticeFragmentContact.View) :
    DetailPracticeFragmentContact.Presenter {
    override fun getListQuestion(idPractice: String?) {
        MainApp.getFirebaseFireStore()
            .collection("Exam")
            .document(idPractice!!)
            .collection("Detail")
            .orderBy("timeCreate", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                error?.let {
                    Log.d(
                        DetailPracticeFragmentPresenter::class.java.simpleName,
                        "Listen failed.",
                        error
                    )
                    return@addSnapshotListener
                }
                value?.let { items ->
                    val listExam = mutableListOf<DetailExam>()
                    for (item in items) {
                        val json = Gson().toJson(item.data)
                        if (json != null) {
                            val exam = Gson().fromJson(json, DetailExam::class.java)
                            listExam.add(exam)
                        }
                    }
                    if (listExam.size != 0) {
                        view.successGetListExam(listExam)
                    } else {
                        view.showMessage(R.string.msg_get_list_detail_practice_empty)
                    }
                }
            }
    }
}