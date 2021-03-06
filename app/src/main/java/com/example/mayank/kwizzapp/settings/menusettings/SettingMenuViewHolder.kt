package com.example.mayank.kwizzapp.settings.menusettings

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.mayank.kwizzapp.R
import com.example.mayank.kwizzapp.bankdetail.BankDetailFragment
import com.example.mayank.kwizzapp.policies.PoliciesFragment
import com.example.mayank.kwizzapp.profile.ProfileFragment
import com.example.mayank.kwizzapp.viewmodels.SettingVm
import net.rmitsolutions.mfexpert.lms.helpers.showDialog
import net.rmitsolutions.mfexpert.lms.helpers.switchToFragment
import net.rmitsolutions.mfexpert.lms.helpers.switchToFragmentBackStack
import org.jetbrains.anko.find
import org.jetbrains.anko.toast

class SettingMenuViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {

    fun bindView(context: Context, settingMenuVm: SettingVm.SettingMenuVm, position: Int){
        val textTitle = itemView.find<TextView>(R.id.setting_header_name)
        val imageIcon = itemView.find<ImageView>(R.id.setting_icon)

        textTitle.text = settingMenuVm.title
        imageIcon.setImageResource(settingMenuVm.imageSource)

        itemView.setOnClickListener{
            when(position){
                0 ->{
                    val profileFragment = ProfileFragment()
                    context.switchToFragmentBackStack(profileFragment)
                }
                1 ->{
                    val bankDetailFragment = BankDetailFragment()
                    context.switchToFragmentBackStack(bankDetailFragment)
                }
                2 ->{
                    val policiesFragment = PoliciesFragment()
                    context.switchToFragmentBackStack(policiesFragment)
                }
            }
        }
    }
}