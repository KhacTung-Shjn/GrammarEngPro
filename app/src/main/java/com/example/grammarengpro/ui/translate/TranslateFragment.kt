package com.example.grammarengpro.ui.translate

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.utils.showToast
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions
import kotlinx.android.synthetic.main.fragment_translate.*
import java.util.*

class TranslateFragment : BaseFragment(), TranslateFragmentContact.View, View.OnClickListener,
    TextToSpeech.OnInitListener {

    private var presenter: TranslateFragmentPresenter? = null
    private var textTranslate: TextView? = null
    private var dialogTranslate: AlertDialog? = null
    private var textToSpeech: TextToSpeech? = null
    private var language: Locale? = null
    private var speechRecognizer: SpeechRecognizer? = null
    private var isListening = true

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

    @SuppressLint("SetTextI18n", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textTranslate = view.findViewById(R.id.textTranslate)
        textTranslate?.setOnClickListener(this)
        textToSpeech = TextToSpeech(view.context, this)
        imageSpeak.setOnClickListener { speakResult() }

        //Translate
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

        //DeleteAllText
        imageDeleteAll.setOnClickListener {
            isListening = false
            speechRecognizer?.let {
                speechRecognizer?.stopListening()
            }
            imageMicro.setImageResource(R.drawable.ic_microphone)
            editTranslate.setText("")
            textResult.text = ""
            editTranslate.hint = "Enter text..."
        }

        //Recording
        if (ContextCompat.checkSelfPermission(
                view.context,
                Manifest.permission.RECORD_AUDIO
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            checkPermission()
        } else {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(view.context)
        }

        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer!!.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle) {}
            override fun onBeginningOfSpeech() {
                editTranslate.setText("")
                editTranslate.hint = "Listening..."
            }

            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray) {}
            override fun onEndOfSpeech() {}
            override fun onError(i: Int) {}
            override fun onResults(bundle: Bundle) {
                if (isListening) {
                    imageMicro.setImageResource(R.drawable.ic_microphone)
                    val data = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    editTranslate.setText(data!![0])
                }
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle) {}
        })

        imageMicro.setOnTouchListener { view, motionEvent ->
            isListening = true
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                imageMicro.setImageResource(R.drawable.ic_microphone)
                speechRecognizer!!.stopListening()
                editTranslate.setText("")
            }
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                imageMicro.setImageResource(R.drawable.ic_micro_red)
                speechRecognizer!!.startListening(speechRecognizerIntent)
            }
            false
        }
    }

    private fun speakResult() {
        var message = textResult.text.toString()
        if (message.isBlank()) message = "Please enter a message"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null, "")
        } else {
            @Suppress("DEPRECATION")
            textToSpeech!!.speak(message, TextToSpeech.QUEUE_FLUSH, null)
        }
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
            it.stop()
            it.shutdown()
        }
        speechRecognizer?.destroy()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set UK English as language for tts
            val result = textToSpeech!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initialization Failed!")
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(Manifest.permission.RECORD_AUDIO),
                    RecordAudioRequestCode
                )
            }
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RecordAudioRequestCode && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activity?.let {
                    it.showToast("Permission Granted")
                }
            }
        }
    }

    companion object {
        const val RecordAudioRequestCode = 1;
        val TAG: String = TranslateFragment::class.java.simpleName
        private var instance: TranslateFragment? = null
        fun getInstance(): TranslateFragment =
            instance ?: synchronized(this) {
                instance ?: TranslateFragment().also { instance = it }
            }
    }
}
