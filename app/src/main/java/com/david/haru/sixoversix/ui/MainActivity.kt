package com.david.haru.sixoversix.ui

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.david.haru.sixoversix.R
import com.david.haru.sixoversix.databinding.ActivityMainBinding
import com.david.haru.sixoversix.util.currentDestinationId

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.CAMERA)
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    val nav: NavController by lazy {
        findNavController(R.id.nav_host_fragment_content_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var navController: NavController =
            Navigation.findNavController(this, R.id.nav_host_fragment_content_main)


        checkForPermission()

        val viewModel =
            ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.initOrientationEventListener(this)

        viewModel.firstCondition.observe(this) { firstCondition ->
            if (nav.currentDestinationId == R.id.CameraFragment) {
                if (!firstCondition) {
                    nav.popBackStack()
                }
            }

            if (nav.currentDestinationId == R.id.OrientationFragment) {
                if (firstCondition) {
                    checkForPermission()
                }
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }


    private fun checkForPermission() {
        if (allPermissionsGranted()) {

        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
}