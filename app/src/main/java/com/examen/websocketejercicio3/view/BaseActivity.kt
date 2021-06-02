package com.examen.websocketejercicio3.view

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.examen.websocketejercicio3.view.fragments.ChatFragment

abstract class BaseActivity : AppCompatActivity(){


    var mFragment: Fragment? = null
    var mFragmentLayoutId: Int = 0

    fun loadFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.add(mFragmentLayoutId, fragment)
        fragmentTransaction.commit()
    }

    fun replaceFragment(fragment: Fragment) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(mFragmentLayoutId, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
