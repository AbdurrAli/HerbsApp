package com.example.herbs

import android.os.Parcel
import android.os.Parcelable

// Data class representing a Herb with its properties
data class Herbs(
    val name: String,         // Name of the herb
    val description: String,  // Description of the herb
    val photo: String         // URL or path to the photo of the herb
) : Parcelable {
    // Constructor to create a Herbs object from a Parcel
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",  // Read name from Parcel
        parcel.readString() ?: "",  // Read description from Parcel
        parcel.readString() ?: ""   // Read photo from Parcel
    )

    // Writes the object's data to the provided Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)          // Write name to Parcel
        parcel.writeString(description)   // Write description to Parcel
        parcel.writeString(photo)         // Write photo to Parcel
    }

    // Describe the contents of the Parcelable
    override fun describeContents(): Int {
        return 0 // No special objects contained
    }

    // Companion object to create instances of the Parcelable
    companion object CREATOR : Parcelable.Creator<Herbs> {
        // Creates a new instance of Herbs from a Parcel
        override fun createFromParcel(parcel: Parcel): Herbs {
            return Herbs(parcel)
        }

        // Creates a new array of Herbs
        override fun newArray(size: Int): Array<Herbs?> {
            return arrayOfNulls(size)
        }
    }
}
