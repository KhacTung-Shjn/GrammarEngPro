package com.example.grammarengpro.ui.notebook

import android.util.Log
import com.example.grammarengpro.MainApp
import com.example.grammarengpro.R
import com.example.grammarengpro.data.model.Note
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.gson.Gson

class NotebookFragmentPresenter(val view: NotebookFragmentContact.View) :
    NotebookFragmentContact.Presenter {
    override fun getListNote(deviceID: String?) {
        MainApp.getFirebaseFireStore()
            .collection("Note")
            .whereEqualTo("uuid", deviceID!!)
            .orderBy("timeCreate", Query.Direction.DESCENDING)
            .addSnapshotListener { value, error ->
                error?.let {
                    Log.d(
                        NotebookFragmentPresenter::class.java.simpleName,
                        "Listen failed.",
                        error
                    )
                    return@addSnapshotListener
                }

                value?.let { items ->
                    val listNote = mutableListOf<Note>()
                    for (item in items) {
                        val json = Gson().toJson(item.data)
                        if (json != null) {
                            val note = Gson().fromJson(json, Note::class.java)
                            listNote.add(note)
                        }
                    }
                    if (listNote.size != 0) {
                        view.successGetListNote(listNote)
                    } else {
                        view.showMessage(R.string.msg_get_list_note_empty)
                    }
                }
            }
    }

    override fun addNote(title: String, content: String, deviceID: String?) {
        val notes = hashMapOf<String, Any>()
        notes["uuid"] = deviceID!!
        notes["title"] = title
        notes["content"] = content
        notes["timeCreate"] = FieldValue.serverTimestamp()
        MainApp.getFirebaseFireStore()
            .collection("Note")
            .add(notes).addOnCompleteListener {
                view.addSuccess()
            }
    }

    override fun removeNote(t: Note) {
        MainApp.getFirebaseFireStore()
            .collection("Note")
            .whereEqualTo("timeCreate", t.timestamp)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.let { task ->
                        MainApp.getFirebaseFireStore()
                            .collection("Note")
                            .document(task.documents[0].id).delete().addOnCompleteListener {
                                view.removeSuccess()
                            }
                    }
                }
            }
    }

    override fun editNote(t: Note, title: String, content: String) {
        val notes = hashMapOf<String, Any>()
        notes["uuid"] = t.uuid!!
        notes["title"] = title
        notes["content"] = content
        notes["timeCreate"] = t.timestamp!!
        MainApp.getFirebaseFireStore()
            .collection("Note")
            .whereEqualTo("timeCreate", t.timestamp)
            .get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    it.result?.let { task ->
                        MainApp.getFirebaseFireStore()
                            .collection("Note")
                            .document(task.documents[0].id)
                            .update(notes)
                            .addOnCompleteListener {
                                view.editSuccess()
                            }
                    }
                }
            }
    }

}