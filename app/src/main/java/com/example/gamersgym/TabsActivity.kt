package com.example.gamersgym

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout

class TabsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tabs)

        val viewpager: ViewPager = findViewById(R.id.view_pager)
        val tabs: TabLayout = findViewById(R.id.tabs)

        viewpager.adapter = MyPagerAdapter(supportFragmentManager)
        tabs.setupWithViewPager(viewpager)
        viewpager.setCurrentItem(2, true)

        tabs.getTabAt(0)?.setIcon(R.drawable.training)
        tabs.getTabAt(1)?.setIcon(R.drawable.exercises)
        tabs.getTabAt(2)?.setIcon(R.drawable.profile)
        tabs.getTabAt(3)?.setIcon(R.drawable.acvievements)
        tabs.getTabAt(4)?.setIcon(R.drawable.tasks)

        when (intent.getStringExtra("tab")?.toInt()) {
            0 -> {
                viewpager.setCurrentItem(0, true)
            }
            1 -> viewpager.setCurrentItem(1, true)
            2 -> viewpager.setCurrentItem(2, true)
            3 -> viewpager.setCurrentItem(3, true)
            4 -> viewpager.setCurrentItem(4, true)
            else -> {
                viewpager.setCurrentItem(2, true)
            }
        }
    }
}
