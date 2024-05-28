package com.example.quiz_app

import android.os.Parcel
import android.os.Parcelable

data class PlayerScore(val playerName: String, val score: Int) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(playerName)
        parcel.writeInt(score)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlayerScore> {
        override fun createFromParcel(parcel: Parcel): PlayerScore {
            return PlayerScore(parcel)
        }

        override fun newArray(size: Int): Array<PlayerScore?> {
            return arrayOfNulls(size)
        }
    }
}