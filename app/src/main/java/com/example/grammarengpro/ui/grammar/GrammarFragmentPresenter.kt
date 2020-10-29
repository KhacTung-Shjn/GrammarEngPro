package com.example.grammarengpro.ui.grammar

import android.util.Log
import com.example.grammarengpro.MainApp
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.Grammar
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class GrammarFragmentPresenter(val view: GrammarFragmentContact.View) :
    GrammarFragmentContact.Presenter {

    override fun loadListGrammar() {
        MainApp.getFirebaseFireStore()
            .collection("Grammar")
            .orderBy("timeCreate", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                if (error != null) {
                    Log.d(GrammarFragmentPresenter::class.java.simpleName, "Listen failed.", error)
                    return@addSnapshotListener
                }
                if (value != null) {
                    val listGrammar = mutableListOf<Grammar>()
                    for (item in value) {
                        val json = Gson().toJson(item.data)
                        if (json != null) {
                            val grammar = Gson().fromJson(json, Grammar::class.java)
                            listGrammar.add(grammar)
                        }
                    }
                    if (listGrammar.size != 0) {
                        view.successGetListGrammar(listGrammar)
                    } else {
                        view.showMessage(R.string.msg_get_list_grammar_empty)
                    }
                }
            }
    }

}