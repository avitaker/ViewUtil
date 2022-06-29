package com.avinashdavid.viewutil

import android.app.Dialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayout

fun FragmentActivity.restart() {
    finish()
    ContextCompat.startActivity(this, intent, null)
}

fun FragmentActivity.widthPixels() : Int {
    val display = windowManager.defaultDisplay
    val outMetrics = DisplayMetrics()
    display.getMetrics(outMetrics)

    return outMetrics.widthPixels
}

fun FragmentActivity.dismissKeyboard(){
    val imm: InputMethodManager? = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
}

fun Context.openApp(packageName: String) : Boolean {
    val m = packageManager
    try {
        val i: Intent = m.getLaunchIntentForPackage(packageName)
            ?: return false
        //throw new ActivityNotFoundException();
        i.addCategory(Intent.CATEGORY_LAUNCHER)
        startActivity(i)
//        val intent = Intent(Intent.ACTION_VIEW);
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setData(Uri.parse("market://details?id=$packageName"));
//        startActivity(intent);
        return true
    } catch (e: ActivityNotFoundException) {
        Log.e(this.packageName, "Exception opening app", e)
        val intent = Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse("market://details?id=$packageName"));
        startActivity(intent);
        return false
    }
}

fun Context.openPlayStoreForPackageName(packageName: String) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    intent.setData(Uri.parse("market://details?id=$packageName"))
    startActivity(intent)
}

fun Context.dpToPx(dp: Float) = (dp * resources.displayMetrics.density).toInt()

fun View.makeGone() = this.apply { visibility = View.GONE }

fun View.makeVisible() = this.apply { visibility = View.VISIBLE }

fun View.makeInvisible() = this.apply { visibility = View.INVISIBLE }

fun ImageView.setResourceDrawable(@DrawableRes drawableId: Int, @ColorRes colorId: Int) {
    val drawable = ContextCompat.getDrawable(context, drawableId)
    setImageDrawable(drawable)
    setColorFilter(ContextCompat.getColor(context, colorId))
}

fun Fragment.lockRotation() {
    val orientationToSet = when (activity?.resources?.configuration?.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        }
        else -> {
            ActivityInfo.SCREEN_ORIENTATION_NOSENSOR
        }
    }
    activity?.requestedOrientation = orientationToSet
}

fun Fragment.unlockRotation() {
    activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
}

fun Dialog.setExpanded() {
    setOnShowListener { dialog ->
        val bottomSheet = findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}

fun Dialog.setExpandedAndPreventDismissalByFling() {
    setOnShowListener { dialog ->
        val bottomSheet = findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet)

        val behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.apply {
            skipCollapsed = true
            state = BottomSheetBehavior.STATE_EXPANDED
            addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_DRAGGING) {
                        behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {
                }
            })
        }
    }
}

fun NavController.replaceDestination(@IdRes navigationDestinationId: Int) {
    popBackStack()
    navigate(navigationDestinationId)
}

fun EditText.setTextIfNotTheSame(newText: String?) {
    when {
        text == null && newText == null -> {
            return
        }
        text == null -> {
            setText(newText)
        }
        text?.toString() != newText -> {
            setText(newText)
        }
    }
}

fun View.showPopup(
    @MenuRes menuId: Int,
    menuItemClickListener: PopupMenu.OnMenuItemClickListener
){
    val popup = PopupMenu(context, this)
    popup.apply {
        setOnMenuItemClickListener(menuItemClickListener)
        inflate(menuId)
        show()
    }
}

fun TabLayout.decorateTabsAsActionItems(addBlankTab: Boolean = true, onTabClick: (TabLayout.Tab?) -> Unit) {
    if (addBlankTab) {
        val tab = newTab()
        addTab(tab)
        selectTab(tab)
    }
    setSelectedTabIndicatorColor(
        ContextCompat.getColor(
            context,
            com.google.android.material.R.color.design_default_color_background
        )
    )
    addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(tab: TabLayout.Tab?) {
            onTabClick(tab)
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabSelected(tab: TabLayout.Tab?) {
            onTabClick(tab)
        }
    })
}

fun Chip.chipBackgroundColor(@ColorRes colorId: Int) {
    chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(context, colorId))
}

fun Drawable.sizeTo(widthPx: Int, heightPx: Int, resources: Resources) : Drawable {
    getBitmapFromVectorDrawable().let { bitmap ->
        return BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, widthPx, heightPx, true))
    }
}

fun Drawable.getBitmapFromVectorDrawable() : Bitmap {
    val bitmap = Bitmap.createBitmap(
        intrinsicWidth,
        intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    draw(canvas)

    return bitmap
}