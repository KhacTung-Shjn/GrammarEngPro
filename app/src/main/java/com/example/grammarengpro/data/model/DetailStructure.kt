package com.example.grammarengpro.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DetailStructure(
    @SerializedName("detail")
    @Expose
    var listDetail: List<String>?,
    @SerializedName("example")
    @Expose
    var listExample: List<String>?,
    @SerializedName("note")
    @Expose
    var listNote: List<String>?,
    @SerializedName("title")
    @Expose
    var title: String?,
    @SerializedName("timeCreate")
    @Expose
    var timeCreate: Timestamp?,
    @SerializedName("idStruct")
    @Expose
    var idStruct: String?

) : Parcelable {
    constructor(source: Parcel) : this(
        source.createStringArrayList(),
        source.createStringArrayList(),
        source.createStringArrayList(),
        source.readString(),
        source.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeStringList(listDetail)
        writeStringList(listExample)
        writeStringList(listNote)
        writeString(title)
        writeParcelable(timeCreate, 0)
        writeString(idStruct)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DetailStructure> =
            object : Parcelable.Creator<DetailStructure> {
                override fun createFromParcel(source: Parcel): DetailStructure =
                    DetailStructure(source)

                override fun newArray(size: Int): Array<DetailStructure?> = arrayOfNulls(size)
            }
    }
}