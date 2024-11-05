package com.shopping.project.Utils

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Point
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.visible(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun visibleMultipleViews(vararg views: View) {
    views.forEach {
        it.visible()
    }
}

fun View.gone() {
    visibility = View.GONE
}

fun goneMultipleViews(vararg views: View) {
    views.forEach {
        it.gone()
    }
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun invisibleMultipleViews(vararg views: View) {
    views.forEach {
        it.invisible()
    }
}

fun View.setBackgroundInt(backgroundId: Int) {
    this.background = ContextCompat.getDrawable(this.context, backgroundId)
}

fun View.showSnackBar(message: String) {
    Snackbar.make(
        this, message,
        Snackbar.LENGTH_LONG
    ).show()
}

fun View.getDeepChildOffset(mainParent: ViewGroup, parent: ViewParent, accumulatedOffset: Point) {
    val parentGroup: ViewGroup = parent as ViewGroup
    accumulatedOffset.x += left
    accumulatedOffset.y += top
    if (parentGroup == mainParent) return
    parentGroup.getDeepChildOffset(mainParent, parentGroup.parent, accumulatedOffset)
}

fun View.getBitmap(): Bitmap {
    val b = Bitmap.createBitmap(
        width,
        height,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(b)
    this.draw(canvas)
    return b
}
