package com.example.grammarengpro.ui.detail_grammar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.grammarengpro.R
import com.example.grammarengpro.base.BaseFragment
import com.example.grammarengpro.data.model.DetailStructure
import com.example.grammarengpro.data.model.Grammar
import com.example.grammarengpro.data.model.Structure
import com.example.grammarengpro.utils.Const
import kotlinx.android.synthetic.main.fragment_detail_grammar.*

class DetailGrammarFragment : BaseFragment(), DetailGrammarFragmentContact.View,
    View.OnClickListener {

    private var presenter: DetailGrammarFragmentPresenter? = null
    private var grammar: Grammar? = null
    private var imageBack: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail_grammar, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            grammar = it.getParcelable(Const.KEY_GRAMMAR)
        }
        presenter?.getListStruct(grammar?.idGrammar)
        imageBack = activity?.findViewById(R.id.imageBack)
        imageBack?.setOnClickListener(this)
    }

    override fun initPresenter() {
        presenter = DetailGrammarFragmentPresenter(this)
    }

    companion object {
        val TAG: String = DetailGrammarFragment::class.java.simpleName
        private var instance: DetailGrammarFragment? = null
        fun getInstance(grammar: Grammar?): DetailGrammarFragment {
            instance ?: synchronized(this) {
                instance ?: DetailGrammarFragment().also {
                    instance = it
                }
            }
            val bundle = Bundle()
            bundle.putParcelable(Const.KEY_GRAMMAR, grammar)
            instance?.arguments = bundle
            return instance!!
        }

    }

    override fun successGetListDetail(listDetail: MutableList<DetailStructure>) {
        listDetail.reverse()
        rcvListStruct.setHasFixedSize(true)
        val adapter = DetailAdapter(listDetail)
        rcvListStruct.adapter = adapter
    }

    override fun onClick(v: View?) {
        if (fragmentManager?.backStackEntryCount!! > 0) {
            fragmentManager?.popBackStackImmediate()
        } else {
            activity?.finish();
        }
    }
}
