package com.example.shop.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shop.R
import com.example.shop.databinding.ActivityShopItemBinding
import com.example.shop.domain.ShopItem

class ShopItemActivity: AppCompatActivity(), ShopItemFragment.OnEditingFinishedListener {

    private lateinit var binding: ActivityShopItemBinding

    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parseIntent()
        binding = ActivityShopItemBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState == null) {
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw IllegalArgumentException("Unknown screen mode: $screenMode")
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.shop_item_container, fragment)
            .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw IllegalStateException("Param screenMode is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw IllegalArgumentException("Unknown screenMode: $mode")
        }
        screenMode = mode
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw IllegalStateException("Param shopItemId is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "EXTRA_SCREEN_MODE"
        private const val EXTRA_SHOP_ITEM_ID = "EXTRA_SHOP_ITEM_ID"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            }
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
                putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            }
        }
    }

    override fun onEditingFinished() {
        finish()
    }
}