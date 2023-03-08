package com.tasks.ecommerceapp.common

import androidx.viewpager.widget.ViewPager

abstract class EmptyViewPageListener: ViewPager.OnPageChangeListener {
    override fun onPageSelected(position: Int) {}
    override fun onPageScrollStateChanged(state: Int) {}
}