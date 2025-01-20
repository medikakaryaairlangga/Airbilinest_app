package com.mka.airbilinest.ui.center


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.mka.airbilinest.BaseFragment
import com.mka.airbilinest.ControlActivity
import com.mka.airbilinest.R
import com.mka.airbilinest.databinding.FragmentAirbilinestBinding


class CenterFragment : BaseFragment() {

    private var _binding: FragmentAirbilinestBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAirbilinestBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val control_btn: Button = root.findViewById(R.id.btn_control)
        control_btn.setOnClickListener{
            val intent = Intent(activity, ControlActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
