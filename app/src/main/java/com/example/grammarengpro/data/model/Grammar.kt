package com.example.grammarengpro.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Grammar(
    @SerializedName("id")
    @Expose
    var idGrammar: String?,
    @SerializedName("title")
    @Expose
    var title: String?,

    @SerializedName("image")
    @Expose
    var image: String?,

    @SerializedName("timeCreate")
    @Expose
    var timeCreate: Timestamp?,

    @SerializedName("Structure")
    @Expose
    var structure: List<String>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        source.createStringArrayList()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(idGrammar)
        writeString(title)
        writeString(image)
        writeParcelable(timeCreate, 0)
        writeStringList(structure)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Grammar> = object : Parcelable.Creator<Grammar> {
            override fun createFromParcel(source: Parcel): Grammar = Grammar(source)
            override fun newArray(size: Int): Array<Grammar?> = arrayOfNulls(size)
        }
    }
}