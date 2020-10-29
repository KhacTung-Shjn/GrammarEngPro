package com.example.grammarengpro.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.firebase.Timestamp
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Structure(
    @SerializedName("id")
    @Expose
    var id: String?,
    @SerializedName("title")
    @Expose
    var title: String?,
    @SerializedName("timeCreate")
    @Expose
    var timeCreate: Timestamp?,
    @SerializedName("Detail")
    @Expose
    var detailStructure: MutableList<DetailStructure>?
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readParcelable<Timestamp>(Timestamp::class.java.classLoader),
        source.createTypedArrayList(DetailStructure.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(id)
        writeString(title)
        writeParcelable(timeCreate, 0)
        writeTypedList(detailStructure)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Structure> = object : Parcelable.Creator<Structure> {
            override fun createFromParcel(source: Parcel): Structure = Structure(source)
            override fun newArray(size: Int): Array<Structure?> = arrayOfNulls(size)
        }
    }
}