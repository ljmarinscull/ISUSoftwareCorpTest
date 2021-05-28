package com.project.acmetest.ui.workticket.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.project.acmetest.data.model.TicketObject
import com.project.acmetest.ui.workticket.screens.DetailsFragment
import com.project.acmetest.ui.workticket.screens.FinishingUpFragment
import com.project.acmetest.ui.workticket.screens.OverViewFragment
import com.project.acmetest.ui.workticket.screens.PurchasingFragment

class SectionsPagerAdapter(ticket: TicketObject, fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    private val mFragmentList: List<Fragment> = arrayListOf(
        OverViewFragment.newInstance(ticket),
        DetailsFragment.newInstance(),
        PurchasingFragment.newInstance(),
        FinishingUpFragment.newInstance()
    )
    override fun getItemCount() = 4
    override fun createFragment(position: Int) = mFragmentList[position]
}