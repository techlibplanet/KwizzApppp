package com.example.mayank.kwizzapp.profile

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ImageView
import com.example.mayank.kwizzapp.KwizzApp

import com.example.mayank.kwizzapp.R
import com.example.mayank.kwizzapp.dependency.components.DaggerInjectFragmentComponent
import com.example.mayank.kwizzapp.helpers.processRequest
import com.example.mayank.kwizzapp.network.IUser
import com.example.mayank.kwizzapp.viewmodels.Users
import com.google.android.gms.common.images.ImageManager
import io.reactivex.disposables.CompositeDisposable
import net.rmitsolutions.libcam.LibCamera
import net.rmitsolutions.mfexpert.lms.helpers.*
import org.jetbrains.anko.find
import javax.inject.Inject

class ProfileFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var recyclerView: RecyclerView
    val adapter: ProfileViewAdapter by lazy { ProfileViewAdapter() }
    lateinit var modelList: MutableList<ProfileVm>
    private lateinit var profileImage :ImageView
    private lateinit var libCamera: LibCamera
    @Inject
    lateinit var userService: IUser
    private lateinit var compositeDisposable: CompositeDisposable


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        val depComponent = DaggerInjectFragmentComponent.builder()
                .applicationComponent(KwizzApp.applicationComponent)
                .build()
        depComponent.injectProfileFragment(this)

        compositeDisposable = CompositeDisposable()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        libCamera = LibCamera(activity!!)
        profileImage = view.find(R.id.user_image)
        val imageManager = ImageManager.create(activity)
        val image = activity?.getPref(SharedPrefKeys.ICON_IMAGE, "")
        imageManager.loadImage(profileImage, Uri.parse(image))

        recyclerView = view.findViewById(R.id.profile_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(DividerItemDecoration(activity, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
        modelList = mutableListOf<ProfileVm>()
        setProfileItem()
        getWinLoosePoints()
        return view
    }

    private fun setProfileItem() {
        modelList.clear()
        val firstName = activity?.getPref(SharedPrefKeys.FIRST_NAME, "")!!
        logD("First Name - $firstName")
        modelList.add(ProfileVm("First Name", firstName))
        modelList.add(ProfileVm("Last Name", activity?.getPref(SharedPrefKeys.LAST_NAME, "")!!))
        modelList.add(ProfileVm("Mobile Number", activity?.getPref(SharedPrefKeys.MOBILE_NUMBER, "")!!))
        modelList.add(ProfileVm("Email", activity?.getPref(SharedPrefKeys.EMAIL, "")!!))
        setRecyclerViewAdapter(modelList)

    }

    private fun getWinLoosePoints() {
        val profile = Users.Profile()
        profile.playerId = activity?.getPref(SharedPrefKeys.PLAYER_ID, "")
        if (profile.playerId != "") {
            compositeDisposable.add(userService.getProfileData(profile).processRequest(
                    { profileData ->
                        if (profileData.isSuccess){
                            logD("Total Win - ${profileData.totalWin}\n" +
                                    "Total Loose - ${profileData.totalLoose}")
                        }else{
                            toast(profileData.message)
                        }
                    },
                    { err ->
                        logD(err)
                    }
            ))
        }
    }

    private fun setRecyclerViewAdapter(list: List<ProfileVm>) {
        adapter.items = list
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        activity?.menuInflater?.inflate(R.menu.menu_profile, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.edit -> {
                val editProfileFragment = EditProfileFragment()
                switchToFragmentBackStack(editProfileFragment)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }



    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

    }
}
