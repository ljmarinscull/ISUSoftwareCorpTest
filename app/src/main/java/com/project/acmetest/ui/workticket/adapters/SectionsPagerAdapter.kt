package com.project.acmetest.ui.workticket.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.ui.workticket.screens.DetailsFragment
import com.project.acmetest.ui.workticket.screens.FinishingUpFragment
import com.project.acmetest.ui.workticket.screens.OverViewFragment
import com.project.acmetest.ui.workticket.screens.PurchasingFragment

class SectionsPagerAdapter(ticket: TicketObject, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {
    private val mFragmentList: List<Fragment> = arrayListOf(
        OverViewFragment.newInstance(ticket),
        DetailsFragment.newInstance(),
        PurchasingFragment.newInstance(),
        FinishingUpFragment.newInstance()
    )

    override fun getItem(position: Int) = mFragmentList[position]


    override fun getCount() = 4
}