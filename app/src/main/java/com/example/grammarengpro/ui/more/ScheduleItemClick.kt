package com.example.grammarengpro.ui.more

import com.example.grammarengpro.data.model.Time

interface ScheduleItemClick {
    fun onClickIsChoose(position: Int, listTime: MutableList<Time>)
}