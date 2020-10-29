package com.example.grammarengpro.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.grammarengpro.data.model.FragmentController

object AppUtils {
    fun addFragmentToActivity(
        fragmentManager: FragmentManager,
        fragmentControllers: List<FragmentController>,
        frameId: Int,
        beginPosition: Int
    ): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()
        for (i in fragmentControllers.indices) {
            if (i != beginPosition) {
                fragmentTransaction.add(
                    frameId,
                    fragmentControllers[i].fragment,
                    fragmentControllers[i].tag
                ).hide(fragmentControllers[i].fragment)
            }
        }
        fragmentTransaction.add(
            frameId,
            fragmentControllers[beginPosition].fragment,
            fragmentControllers[beginPosition].tag
        )
        fragmentTransaction.commit()
        return fragmentControllers[beginPosition].fragment
    }

    fun switchFragmentActivity(
        fragmentManager: FragmentManager,
        currentFragment: Fragment,
        mustActiveFragment: Fragment
    ): Fragment {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.hide(currentFragment)
        fragmentTransaction.show(mustActiveFragment)
        fragmentTransaction.commit()
        return mustActiveFragment
    }

    fun replaceFragment(
        fragmentManager: FragmentManager,
        frameMain: Int,
        fragment: Fragment?,
        isBackStack: Boolean,
        TAG: String?
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(frameMain, fragment!!, TAG)
        if (isBackStack) {
            fragmentTransaction.addToBackStack(TAG)
        }
        fragmentTransaction.commit()
    }
}