package com.bitc.foodytripapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bitc.foodytripapp.Fragment.FeedFragment
import com.bitc.foodytripapp.Fragment.MyReviewFragment
import com.bitc.foodytripapp.Fragment.SearchFragment
import com.bitc.foodytripapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var toggle: ActionBarDrawerToggle

    lateinit var binding : ActivityMainBinding

    lateinit var auth : FirebaseAuth

    class MainFragmentPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {
        val fragments : List<Fragment>
        init {
            fragments = listOf(FeedFragment(), MyReviewFragment(), SearchFragment())
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        // 드로어 메뉴 버튼 만들기
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.open, R.string.close)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // 리뷰 추가 버튼 누를 때
        binding.reviewBtn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)

            startActivity(intent)
        }

        // 뷰페이저 - 탭 구현하기
        val adapter = MainFragmentPagerAdapter(this)
        binding.viewpager.adapter = adapter

        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            when(position) {
                0 -> tab.text = "피드"
                1 -> tab.text = "내 리뷰"
                2 -> tab.text = "맛집 검색"
            }
        }.attach()
    }

    override fun onStart() {
        super.onStart()

        auth = FirebaseAuth.getInstance()

        val headerView = binding.drawerLoginView.getHeaderView(0)
        val useridHeader : TextView = headerView.findViewById(R.id.userid)
        val welcomeHeader : TextView = headerView.findViewById(R.id.welcome)

        if (!checkAuth()) {
            useridHeader.visibility = View.GONE
            welcomeHeader.text = "로그인 하세요."
            binding.drawerLoginView.menu.setGroupVisible(R.id.login_group, false)
            binding.drawerLoginView.menu.setGroupVisible(R.id.logout_group, true)
            binding.reviewBtn.visibility = View.GONE
        } else {
            val displayName = auth.currentUser?.displayName
            useridHeader.visibility = View.VISIBLE
            useridHeader.text = "$displayName 님"
            welcomeHeader.text = "환영합니다!"
            binding.drawerLoginView.menu.setGroupVisible(R.id.login_group, true)
            binding.drawerLoginView.menu.setGroupVisible(R.id.logout_group, false)
            binding.reviewBtn.visibility = View.VISIBLE
        }

        binding.drawerLoginView.setNavigationItemSelectedListener(this)
    }

    fun checkAuth() : Boolean {
        var currentUser = auth.currentUser

        return currentUser?.let{
            currentUser.isEmailVerified
        }?:let{
            false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_login, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.login -> {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            R.id.join -> {
                startActivity(Intent(this, JoinActivity::class.java))
            }
            R.id.logout -> {
                auth.signOut()

                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        return true
    }
}