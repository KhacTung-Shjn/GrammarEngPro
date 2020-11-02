package com.example.grammarengpro.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailExam(
    @SerializedName("title")
    @Expose
    var numberQuestion: String?,
    @SerializedName("answers")
    @Expose
    var result: String?,
    @SerializedName("detail")
    @Expose
    var listAnswer: List<String>?,
    @SerializedName("timeCreate")
    @Expose
    var timeCreate: Timestamp?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.createStringArrayList(),
        source.readParcelable<Timestamp>(Timestamp::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(numberQuestion)
        writeString(result)
        writeStringList(listAnswer)
        writeParcelable(timeCreate, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DetailExam> = object : Parcelable.Creator<DetailExam> {
            override fun createFromParcel(source: Parcel): DetailExam = DetailExam(source)
            override fun newArray(size: Int): Array<DetailExam?> = arrayOfNulls(size)
        }
    }
}