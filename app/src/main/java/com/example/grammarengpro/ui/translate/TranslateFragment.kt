package com.example.grammarengpro.ui.translate

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.fragment_translate.*
import java.util.*

class TranslateFragment : BaseFragment(), TranslateFragmentContact.View, View.OnClickListener {

    private var presenter: TranslateFragmentPresenter? = null
    private var textTranslate: TextView? = null
    private var dialogTranslate: AlertDialog? = null
    private var textToSpeech: TextToSpeech? = null
    private var language: Locale? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_translate, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textTranslate = view.findViewById(R.id.textTranslate)
        textTranslate?.setOnClickListener(this)
        imageSpeak.setOnClickListener { speakResult() }
        imageTransaction.setOnClickListener {
            if (textLanguageFrom.text.toString() == "English") {
                imageSpeak.visibility = View.VISIBLE
                textLanguageFrom.text = "Vietnamese"
                textLanguageTo.text = "English"
            } else {
                imageSpeak.visibility = View.GONE
                textLanguageFrom.text = "English"
                textLanguageTo.text = "Vietnamese"
            }
        }
    }

    private fun speakResult() {
        textToSpeech = TextToSpeech(activity) {
            if (it == TextToSpeech.SUCCESS) {
                val result = textToSpeech!!.setLanguage(Locale.US)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    Toast.makeText(
                        context,
                        "LANG_MISSING_DATA || LANG_NOT_SUPPORTED",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        val text = textResult.text.toString()
        textToSpeech!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }

    override fun initPresenter() {
        presenter = TranslateFragmentPresenter(this)
    }

    override fun onClick(v: View?) {
        val str = editTranslate.text.toString()
        var sourceLanguage: Int? = null
        var targetLanguage: Int? = null

        if (textLanguageFrom.text.toString() == "English") {
            sourceLanguage = FirebaseTranslateLanguage.EN
            targetLanguage = FirebaseTranslateLanguage.VI
        } else {
            sourceLanguage = FirebaseTranslateLanguage.VI
            targetLanguage = FirebaseTranslateLanguage.EN
        }

        val options = FirebaseTranslatorOptions.Builder()
            .setSourceLanguage(sourceLanguage)
            .setTargetLanguage(targetLanguage)
            .build()
        val englishGermanTranslator = FirebaseNaturalLanguage.getInstance().getTranslator(options)

        englishGermanTranslator.translate(str)
            .addOnSuccessListener { translatedText ->
                textResult.text = translatedText
            }
            .addOnFailureListener { exception ->
                if (exception.message.equals("Translation model files not found. Make sure to call downloadModelIfNeeded and if that fails, delete the models and retry.")) {
                    if (activity != null && context != null) {
                        val builder = AlertDialog.Builder(context!!)
                        val mView = layoutInflater.inflate(R.layout.dialog_download_language, null)
                        builder.setView(mView)
                        dialogTranslate = builder.create()
                        Objects.requireNonNull(dialogTranslate!!.window)
                            ?.setBackgroundDrawable(
                                ColorDrawable(Color.TRANSPARENT)
                            )
                        mView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                            mView.findViewById<TextView>(R.id.tvTitleDownload).text =
                                context!!.getString(R.string.label_download)
                            mView.findViewById<ProgressBar>(R.id.progressDownload).visibility =
                                View.VISIBLE
                            mView.findViewById<Button>(R.id.btnYes).visibility = View.INVISIBLE
                            mView.findViewById<Button>(R.id.btnNo).visibility = View.INVISIBLE

                            englishGermanTranslator.downloadModelIfNeeded()
                                .addOnSuccessListener {
                                    Toast.makeText(
                                        context,
                                        context!!.getString(R.string.label_download_success),
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    dialogTranslate?.dismiss()
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(context, exception.message, Toast.LENGTH_SHORT)
                                        .show()
                                }
                        }
                        mView.findViewById<Button>(R.id.btnNo).setOnClickListener {
                            dialogTranslate?.dismiss()
                        }
                        dialogTranslate?.setCancelable(false)
                        dialogTranslate?.show()
                    }
                }
            }
    }

    override fun onDetach() {
        super.onDetach()
        dialogTranslate?.let {
            it.dismiss()
        }
        textToSpeech?.let {
            it.stop()
            it.shutdown()
        }
    }

    companion object {
        val TAG: String = TranslateFragment::class.java.simpleName
        private var instance: TranslateFragment? = null
        fun getInstance(): TranslateFragment =
            instance ?: synchronized(this) {
                instance ?: TranslateFragment().also { instance = it }
            }
    }
}
