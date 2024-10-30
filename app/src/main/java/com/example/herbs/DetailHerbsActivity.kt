package com.example.herbs

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import java.io.File

class DetailHerbsActivity : AppCompatActivity(), View.OnClickListener {

    // Entry point of the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Enables edge-to-edge layout

        // Set the content view before accessing views
        setContentView(R.layout.activity_detail_herbs)

        // Retrieve views
        val tvName = findViewById<TextView>(R.id.tv_item_name)
        val tvDescription = findViewById<TextView>(R.id.tv_item_description)
        val imgPhoto = findViewById<ImageView>(R.id.img_item_photo)

        // Retrieve intent extras for herb details
        val herbsName = intent.getStringExtra("HERBS_NAME")
        val herbsDescription = intent.getStringExtra("HERBS_DESCRIPTION")
        val herbsPhoto = intent.getStringExtra("HERBS_PHOTO")

        // Load individual extras if they are available
        if (herbsName != null && herbsDescription != null && herbsPhoto != null) {
            tvName.text = herbsName
            tvDescription.text = herbsDescription
            Glide.with(this)
                .load(herbsPhoto) // Load herb photo using Glide
                .into(imgPhoto)
        }

        // Load data from Parcelable only if extras were not passed
        val herbs = intent.getParcelableExtra<Herbs>("key_herb")
        if (herbsName == null && herbs != null) {
            tvName.text = herbs.name
            tvDescription.text = herbs.description
            Glide.with(this)
                .load(herbs.photo) // Load herb photo from Parcelable
                .into(imgPhoto)
        }

        // Handle system insets for edge-to-edge layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize and set up the share button
        val btnShare: Button = findViewById(R.id.btn_share)
        btnShare.setOnClickListener(this)
    }

    // Function to share the herb article
    private fun shareArticle() {
        val tvName = findViewById<TextView>(R.id.tv_item_name)
        val tvDescription = findViewById<TextView>(R.id.tv_item_description)
        val imgPhoto = findViewById<ImageView>(R.id.img_item_photo)

        // Convert image to bitmap
        imgPhoto.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(imgPhoto.drawingCache)
        imgPhoto.isDrawingCacheEnabled = false

        // Save bitmap to temporary file
        val file = File(cacheDir, "shared_image.png")
        val fileOutputStream = file.outputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
        fileOutputStream.flush()
        fileOutputStream.close()

        // Get URI for the file using FileProvider
        val imageUri = FileProvider.getUriForFile(this, "com.example.herbs.fileprovider", file)

        // Prepare text for sharing
        val shareText = "Name: *${tvName.text}*\n\nDescription: ${tvDescription.text}"

        // Create and start the share intent
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            putExtra(Intent.EXTRA_STREAM, imageUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            type = "image/*"
        }

        startActivity(Intent.createChooser(shareIntent, "Share via")) // Launch the share dialog
    }

    // Handle click events for buttons
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_share -> {
                shareArticle() // Call the shareArticle function
            }
        }
    }
}
