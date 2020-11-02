package com.example.grammarengpro.ui.notebook

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.base.OnItemClick
import com.example.grammarengpro.data.model.Note
import kotlinx.android.synthetic.main.fragment_notebook.*
import java.util.*


class NotebookFragment : BaseFragment(), NotebookFragmentContact.View, View.OnClickListener,
    OnItemClick<Note> {

    private var notebookFragmentPresenter: NotebookFragmentPresenter? = null
    private var dialogNote: AlertDialog? = null
    private var deviceID: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notebook, container, false)
    }

    @SuppressLint("HardwareIds")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        deviceID =
            Settings.Secure.getString(context?.contentResolver, Settings.Secure.ANDROID_ID)
        activity?.findViewById<ImageView>(R.id.imageAdd)!!.setOnClickListener(this)
        notebookFragmentPresenter?.getListNote(deviceID)
    }

    override fun initPresenter() {
        notebookFragmentPresenter = NotebookFragmentPresenter(this)
    }

    companion object {
        val TAG: String = NotebookFragment::class.java.simpleName
        private var instance: NotebookFragment? = null
        fun getInstance(): NotebookFragment =
            instance ?: synchronized(this) {
                instance ?: NotebookFragment().also { instance = it }
            }
    }

    override fun onClick(v: View?) {
        if (activity != null && context != null) {
            val builder = AlertDialog.Builder(context!!)
            val mView = layoutInflater.inflate(R.layout.dialog_note, null)
            builder.setView(mView)
            dialogNote = builder.create()
            Objects.requireNonNull(dialogNote!!.window)
                ?.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                )
            val imageEdit: ImageView = mView.findViewById(R.id.imageEdit)
            val imageDelete: ImageView = mView.findViewById(R.id.imageDelete)
            val editTitleNote: EditText = mView.findViewById(R.id.editTitleNote)
            val editContentNote: EditText = mView.findViewById(R.id.editContentNote)
            val buttonAdd: TextView = mView.findViewById(R.id.buttonAdd)

            imageEdit.visibility = View.INVISIBLE
            imageDelete.visibility = View.INVISIBLE
            buttonAdd.setOnClickListener {
                val title = editTitleNote.text.toString()
                val content = editContentNote.text.toString()
                if (title == "") {
                    Toast.makeText(
                        buttonAdd.context,
                        getString(R.string.msg_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    notebookFragmentPresenter?.addNote(title, content, deviceID)
                }
            }

            dialogNote?.setCancelable(true)
            dialogNote?.show()
        }
    }

    override fun successGetListNote(listNote: MutableList<Note>) {
        listNote.reverse()
        rcvListNote.setHasFixedSize(true)
        val adapter = NoteAdapter()
        adapter.replaceData(listNote)
        adapter.onClickItem = this
        rcvListNote.adapter = adapter
    }

    override fun addSuccess() {
        notebookFragmentPresenter?.getListNote(deviceID)
        dialogNote?.let {
            it.dismiss()
        }
    }

    override fun removeSuccess() {
        notebookFragmentPresenter?.getListNote(deviceID)
        Toast.makeText(context, getString(R.string.msg_remove_success), Toast.LENGTH_SHORT).show()
        dialogNote?.let {
            it.dismiss()
        }
    }

    override fun editSuccess() {
        notebookFragmentPresenter?.getListNote(deviceID)
        dialogNote?.let {
            it.dismiss()
        }
    }

    override fun onClickItem(t: Note) {
        if (activity != null && context != null) {
            val builder = AlertDialog.Builder(context!!)
            val mView = layoutInflater.inflate(R.layout.dialog_note, null)
            builder.setView(mView)
            dialogNote = builder.create()
            Objects.requireNonNull(dialogNote!!.window)
                ?.setBackgroundDrawable(
                    ColorDrawable(Color.TRANSPARENT)
                )
            val imageEdit: ImageView = mView.findViewById(R.id.imageEdit)
            val imageDelete: ImageView = mView.findViewById(R.id.imageDelete)
            val editTitleNote: EditText = mView.findViewById(R.id.editTitleNote)
            val editContentNote: EditText = mView.findViewById(R.id.editContentNote)
            val buttonAdd: TextView = mView.findViewById(R.id.buttonAdd)

            buttonAdd.visibility = View.GONE
            imageEdit.visibility = View.VISIBLE
            imageDelete.visibility = View.VISIBLE
            editTitleNote.setText(t.title)
            editContentNote.setText(t.content)

            imageEdit.setOnClickListener {
                val title = editTitleNote.text.toString()
                val content = editContentNote.text.toString()
                if (title == "") {
                    Toast.makeText(
                        buttonAdd.context,
                        getString(R.string.msg_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    notebookFragmentPresenter?.editNote(t, title, content)
                }
            }
            imageDelete.setOnClickListener {
                notebookFragmentPresenter?.removeNote(t)
            }

            dialogNote?.setCancelable(true)
            dialogNote?.show()
        }
    }


    override fun onDetach() {
        super.onDetach()
        dialogNote?.let {
            it.dismiss()
        }
    }
}
