package com.example.grammarengpro.ui.detail_grammar

import android.util.Log
import com.example.grammarengpro.MainApp
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.DetailStructure
import com.example.grammarengpro.data.model.Grammar
import com.example.grammarengpro.data.model.Structure
import com.example.grammarengpro.ui.grammar.GrammarFragmentPresenter
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class DetailGrammarFragmentPresenter(val view: DetailGrammarFragmentContact.View) :
    DetailGrammarFragmentContact.Presenter {
    override fun getListStruct(idGrammar: String?) {
        idGrammar?.let {
            MainApp.getFirebaseFireStore()
                .collection("Grammar")
                .document(it)
                .collection("Detail")
                .orderBy("timeCreate", Query.Direction.DESCENDING)
                .addSnapshotListener { value, error ->
                    error?.let {
                        Log.d(
                            DetailGrammarFragmentPresenter::class.java.simpleName,
                            "Listen failed.",
                            error
                        )
                        return@addSnapshotListener
                    }
                    value?.let { items ->
                        val listDetail = mutableListOf<DetailStructure>()
                        for (item in items) {
                            val json = Gson().toJson(item.data)
                            if (json != null) {
                                val detail = Gson().fromJson(json, DetailStructure::class.java)
                                listDetail.add(detail)
                            }
                        }
                        if (listDetail.size != 0) {
                            view.successGetListDetail(listDetail)
                        } else {
                            view.showMessage(R.string.msg_get_list_detail_empty)
                        }
                    }
                }
        }
    }

}