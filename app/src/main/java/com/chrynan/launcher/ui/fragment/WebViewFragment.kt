package com.chrynan.launcher.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import com.chrynan.launcher.R
import com.chrynan.launcher.navigator.Navigator
import kotlinx.android.synthetic.main.fragment_webview.*

class WebViewFragment : BaseFragment(),
        Navigator {

    companion object {

        private const val URL_KEY = "KeyURL"

        fun newInstance(url: String) = WebViewFragment().apply {
            arguments = Bundle().apply {
                putString(URL_KEY, url)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_webview, container)

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        webView?.apply {
            webChromeClient = WebChromeClient()
            webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            loadUrl(url)
        }
    }
}