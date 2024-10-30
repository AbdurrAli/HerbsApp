package com.example.herbs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener {
    // RecyclerView for displaying the list of herbs
    private lateinit var rvHerbs: RecyclerView

    // List to hold the herbs data
    private val list = ArrayList<Herbs>()

    // Profile image view and its URL
    private lateinit var imageProfileView: CircleImageView
    private val imageProfileUrl = "https://w.wallhaven.cc/full/vq/wallhaven-vq268p.jpg"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Thread.sleep(3000)
        // Install Splashscreen
        installSplashScreen()

        // Enable edge-to-edge layout for the activity
        enableEdgeToEdge()

        // Handle system window insets for padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize and set click listener for the profile image
        imageProfileView = findViewById(R.id.img_profile)
        imageProfileView.setOnClickListener(this)

        // Load the profile image using Glide
        Glide.with(this)
            .load(imageProfileUrl)
            .into(imageProfileView)

        // Initialize RecyclerView and set up the list of herbs
        rvHerbs = findViewById(R.id.rv_herbs)
        rvHerbs.setHasFixedSize(true)
        list.addAll(getListHerbs()) // Populate the list with data
        showRecyclerList() // Display the list in RecyclerView
    }

    // Retrieve the list of herbs from resources
    private fun getListHerbs(): ArrayList<Herbs> {
        val herbsNames = resources.getStringArray(R.array.data_name)
        val herbsDescriptions = resources.getStringArray(R.array.data_description)
        val herbsPhotos = resources.getStringArray(R.array.data_photo)

        val minSize = minOf(herbsNames.size, herbsDescriptions.size, herbsPhotos.size)
        val herbs = ArrayList<Herbs>()

        // Populate the herbs list with data
        for (i in 0 until minSize) {
            val herb = Herbs(
                name = herbsNames[i],
                description = herbsDescriptions[i],
                photo = herbsPhotos[i]
            )
            herbs.add(herb)
        }
        return herbs
    }

    // Set up the RecyclerView with a LinearLayoutManager and adapter
    private fun showRecyclerList() {
        rvHerbs.layoutManager = LinearLayoutManager(this)
        val listHerbsAdapter = ListHerbsAdapter(list)
        rvHerbs.adapter = listHerbsAdapter
    }

    // Handle click events for the activity
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_profile -> {
                // Start ProfileActivity when the profile image is clicked
                val profileIntent = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(profileIntent)
            }
        }
    }
}
