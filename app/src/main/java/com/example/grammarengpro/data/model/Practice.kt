package com.example.grammarengpro.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Practice(
    @SerializedName("id")
    @Expose
    var idPractice: String?,
    @SerializedName("image")
    @Expose
    var imageUrl: String?,
    @SerializedName("title")
    @Expose
    var title: String?,
    @SerializedName("timeCreate")
    @Expose
    var timeCreate: Timestamp?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readParcelable<Timestamp>(Timestamp::class.java.classLoader)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(idPractice)
        writeString(imageUrl)
        writeString(title)
        writeParcelable(timeCreate, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Practice> = object : Parcelable.Creator<Practice> {
            override fun createFromParcel(source: Parcel): Practice = Practice(source)
            override fun newArray(size: Int): Array<Practice?> = arrayOfNulls(size)
        }
    }
}