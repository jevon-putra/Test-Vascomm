package com.jop.testvascomm.view.home.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.jop.testvascomm.R
import com.jop.testvascomm.data.model.Hospital
import com.jop.testvascomm.data.model.MenuEvent
import com.jop.testvascomm.data.model.Product
import com.jop.testvascomm.databinding.FragmentHomeBinding
import com.jop.testvascomm.ui.BaseFragment
import com.jop.testvascomm.utils.SizeUtil
import com.jop.testvascomm.utils.SpacingItemColumnAdapter
import com.jop.testvascomm.view.home.adapter.FilterAdapter
import com.jop.testvascomm.view.home.adapter.HospitalAdapter
import com.jop.testvascomm.view.home.adapter.MenuEventAdapter
import com.jop.testvascomm.view.home.adapter.ProductAdapter

class HomeFragment : BaseFragment(), MenuEventAdapter.MenuEventAdapterListener, ProductAdapter.ProductAdapterListener, FilterAdapter.FilterAdapterListener, HospitalAdapter.HospitalAdapterListener, MenuProvider {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var menuEventAdapter: MenuEventAdapter
    private lateinit var filterAdapter: FilterAdapter
    private lateinit var productAdapter: ProductAdapter
    private lateinit var hospitalAdapter: HospitalAdapter
    private lateinit var badgeDrawable: BadgeDrawable
    private var listOfMenuEvent: MutableList<MenuEvent> = mutableListOf()
    private var listOfProduct: MutableList<Product> = mutableListOf()
    private var listOfHospital: MutableList<Hospital> = mutableListOf()
    private var listOfFilter: MutableList<String> = mutableListOf("All Product", "Layanan Kesehatan", "Alat Kesehatan", "Layanan Kesehatan 1", "Alat Kesehatan 2")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(!::menuEventAdapter.isInitialized){
            menuEventAdapter = MenuEventAdapter(requireContext(), this)
            menuEventAdapter.setData(listOfMenuEvent)
        }

        if(!::productAdapter.isInitialized) {
            productAdapter = ProductAdapter(this)
            productAdapter.setData(listOfProduct)
        }

        if(!::hospitalAdapter.isInitialized) {
            hospitalAdapter = HospitalAdapter(this)
            hospitalAdapter.setData(listOfHospital)
        }

        if(!::filterAdapter.isInitialized) {
            filterAdapter = FilterAdapter(this)
            filterAdapter.setData(listOfFilter)
            filterAdapter.setSelectedFilter(listOfFilter[0])
        }

        if(!::badgeDrawable.isInitialized){
            badgeDrawable = BadgeDrawable.create(requireContext()).apply {
                backgroundColor = ContextCompat.getColor(requireContext(), R.color.red)
                horizontalOffset = 20
                verticalOffset = 10
            }
        }

        setupToolbar()
        setupUI()
    }

    private fun setupUI() {
        binding.apply {
            rvMenuEvent.adapter = menuEventAdapter
            rvFilter.adapter = filterAdapter
            rvProduct.adapter = productAdapter
            rvHospital.adapter = hospitalAdapter

            tvSeeMore.setOnClickListener { setupButtonSelector(true) }

            tvPackage.setOnClickListener { setupButtonSelector(false) }

            val decoration = SpacingItemColumnAdapter(2, SizeUtil.pxFromDp(8f), false)
            val gridLayout  = object : GridLayoutManager(requireContext(), 2){
                override fun canScrollHorizontally(): Boolean {
                    return false
                }
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }

            rvProduct.addItemDecoration(decoration)
            rvProduct.layoutManager = gridLayout

            btnLogout.setOnClickListener {
                appData.setToken("")
                mainAct.navController.navigate(R.id.action_homeFragment_to_loginFragment)
            }

            ivTwitter.setOnClickListener { intentUrl("https://x.com/") }

            ivFacebook.setOnClickListener { intentUrl("https://facebook.com/") }

            ivInstagram.setOnClickListener { intentUrl("https://instagram.com/") }

            tvProfile.setOnClickListener {
                val bundle = Bundle().apply { putString("type", "profile") }
                mainAct.navController.navigate(R.id.action_homeFragment_to_profileFragment, bundle)
            }

            tvSetting.setOnClickListener {
                val bundle = Bundle().apply { putString("type", "setting") }
                mainAct.navController.navigate(R.id.action_homeFragment_to_profileFragment, bundle)
            }
        }
    }

    @OptIn(ExperimentalBadgeUtils::class)
    private fun setupToolbar(){
        mainAct.setSupportActionBar(binding.toolbar.toolbar)
        mainAct.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val drawerToggle = ActionBarDrawerToggle(mainAct, binding.drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        binding.drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.apply {
            toolbar.toolbar.title = ""
            ivClose.setOnClickListener{ binding.drawer.closeDrawers() }
        }

        BadgeUtils.attachBadgeDrawable(badgeDrawable, binding.toolbar.toolbar, R.id.nav_notification)
    }

    private fun setupButtonSelector(isSeeMore: Boolean){
        binding.apply {
            if (isSeeMore){
                tvSeeMore.setBackgroundResource(R.drawable.bg_green_rounded)
                tvPackage.setBackgroundResource(R.drawable.bg_white_rounded)
            } else {
                tvSeeMore.setBackgroundResource(R.drawable.bg_white_rounded)
                tvPackage.setBackgroundResource(R.drawable.bg_green_rounded)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        if(listOfMenuEvent.isEmpty()){
            listOfMenuEvent.addAll(
                listOf(
                    MenuEvent(
                        title = "Solusi, <b>Kesehatan Anda</b>",
                        message = "Update informasi seputar kesehatan semua bisa disini !",
                        isButton = true,
                        isIllustrationInLeft = false,
                        illustration = R.drawable.img_calendar,
                        background = R.drawable.bg_health,
                        textButton = "Selengkapnya"
                    ),
                    MenuEvent(
                        title = "Layanan Khusus",
                        message = "Tes Covid 19, Cegah Corona Sedini Mungkin",
                        isButton = false,
                        isIllustrationInLeft = false,
                        illustration = R.drawable.img_vaccine,
                        background = R.color.white,
                        textButton = "Daftar Tes"
                    ),
                    MenuEvent(
                        title = "Track Pemeriksaan",
                        message = "Kamu dapat mengecek progress pemeriksaanmu disini",
                        isButton = false,
                        isIllustrationInLeft = true,
                        illustration = R.drawable.img_search,
                        background = R.color.white,
                        textButton = "Track"
                    )
                )
            )
        }

        if(listOfProduct.isEmpty()){
            listOfProduct.addAll(
                listOf(
                    Product(
                        productName = "Suntik Steril",
                        star = 5,
                        price = 10000.0
                    ),
                    Product(
                        productName = "Stetoskop",
                        star = 4,
                        price = 15000.0
                    ),
                    Product(
                        productName = "Pisau Bedah",
                        star = 5,
                        price = 18500.0
                    ),
                    Product(
                        productName = "Sarung Tangan Steril",
                        star = 2,
                        price = 19500.0
                    )
                )
            )
        }

        if(listOfHospital.isEmpty()){
            listOfHospital.addAll(
                listOf(
                    Hospital(
                        hospitalName = "PCR Swab Test (Drive Thru) Hasil 1 Hari Kerja",
                        price = 1400000.0,
                        location = "Lenmarc Surabaya",
                        address = "Dukuh Pakis, Surabaya",
                        image = R.drawable.img_sample_hospital_1
                    ),
                    Hospital(
                        hospitalName = "Vaksin Delta Variant",
                        price = 50000.0,
                        location = "Royal Plaza Surabaya",
                        address = "Ketintang, Surabaya",
                        image = R.drawable.img_sample_hospital_2
                    )
                )
            )
        }
    }

    private fun intentUrl(url: String){
        val i = Intent(Intent.ACTION_VIEW)
        i.setData(Uri.parse(url))
        startActivity(i)
    }

    override fun onClickMenuEvent(menuEvent: MenuEvent) {
        Log.i("ON CLIK MENU EVENT", "CLICKED")
    }

    override fun onClickProduct(product: Product) {
        Log.i("ON CLIK PRODUCT", "CLICKED")
    }

    override fun onClickFilter(filter: String) {
        filterAdapter.setSelectedFilter(filter)
    }

    override fun onClickHospital(hospital: Hospital) {
        Log.i("ON CLIK HOSPITAL", "CLICKED")
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            android.R.id.home -> {
                binding.drawer.openDrawer(GravityCompat.START)
                true
            }
            else -> false
        }
    }
}