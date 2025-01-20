package com.mka.airbilinest.ui.right

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.mka.airbilinest.R
import com.mka.airbilinest.databinding.FragmentAboutBinding
import android.net.Uri
import com.mka.airbilinest.AppHelpActivity
import com.mka.airbilinest.FaQActivity
import com.mka.airbilinest.ManualActivity
import com.mka.airbilinest.SettingsActivity


class RightFragment : Fragment() {

    private var _binding: FragmentAboutBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val manualBtn: Button = root.findViewById(R.id.btn_manual)
        manualBtn.setOnClickListener{
            val i = Intent(context, ManualActivity::class.java)
            startActivity(i)
        }

        val helpBtn: Button = root.findViewById(R.id.btn_help)
        helpBtn.setOnClickListener{
            val i = Intent(context, AppHelpActivity::class.java)
            startActivity(i)
        }

        val faqBtn: Button = root.findViewById(R.id.btn_faq)
        faqBtn.setOnClickListener{
            val i = Intent(context, FaQActivity::class.java)
            startActivity(i)
        }

        val settingsBtn: Button = root.findViewById(R.id.btn_settings)
        settingsBtn.setOnClickListener{
            val i = Intent(context, SettingsActivity::class.java)
            startActivity(i)
        }



        val insta_btn: ImageButton = root.findViewById(R.id.insta_btn)
        insta_btn.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/medika.karya.airlangga/"))
            startActivity(i)
        }

        val linkedin_btn: ImageButton = root.findViewById(R.id.linkedin_btn)
        linkedin_btn.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://id.linkedin.com/company/pt-medika-karya-airlangga"))
            startActivity(i)
        }

        val youtube_btn: ImageButton = root.findViewById(R.id.youtube_btn)
        youtube_btn.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/@airbilinestofficial8761"))
            startActivity(i)
        }

        val website_btn: ImageButton = root.findViewById(R.id.website_btn)
        website_btn.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://medikakaryaairlangga.com/"))
            startActivity(i)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}