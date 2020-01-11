package com.aidiscern.bill.expand

import android.app.Activity
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.aidiscern.bill.R
import com.aidiscern.bill.util.GifSizeFilter
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.filter.Filter
import com.zhihu.matisse.internal.entity.CaptureStrategy

/**
 * reqCode：请求码
 */
fun Activity.selectPhoto(reqCode: Int) =  Matisse.from(this)
    .choose(MimeType.ofImage(), false)
    .countable(true)
    .capture(true)
    .captureStrategy(
        CaptureStrategy(true, "com.aidiscern.bill.fileprovider", "capture")
    )
    .maxSelectable(1)
    .addFilter(
        GifSizeFilter(
            320,
            320,
            5 * Filter.K * Filter.K
        )
    )
    .gridExpectedSize(
        resources.getDimensionPixelSize(R.dimen.grid_expected_size)
    )
    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    .thumbnailScale(0.85f)
    .imageEngine(GlideEngine())
    .showSingleMediaType(true)
    .originalEnable(true)
    .maxOriginalSize(10)
    .autoHideToolbarOnSingleTap(true)
    .forResult(reqCode)

/**
 * reqCode：请求码
 */
fun Fragment.selectPhoto(reqCode: Int) =  Matisse.from(this)
    .choose(MimeType.ofImage(), false)
    .countable(true)
    .capture(true)
    .captureStrategy(
        CaptureStrategy(true, "com.aidiscern.bill.fileprovider", "capture")
    )
    .maxSelectable(1)
    .addFilter(
        GifSizeFilter(
            320,
            320,
            5 * Filter.K * Filter.K
        )
    )
    .gridExpectedSize(
        resources.getDimensionPixelSize(R.dimen.grid_expected_size)
    )
    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
    .thumbnailScale(0.85f)
    .imageEngine(GlideEngine())
    .showSingleMediaType(true)
    .originalEnable(true)
    .maxOriginalSize(10)
    .autoHideToolbarOnSingleTap(true)
    .forResult(reqCode)


fun Activity.cropPhoto(source: Uri, destination: Uri,requestCode: Int,bitmapFormat: Bitmap.CompressFormat){
    val options = UCrop.Options()
    options.setFreeStyleCropEnabled(true)
    options.setCompressionFormat(bitmapFormat)
    UCrop.of(source, destination)
        .withOptions(options)
        .start(this,requestCode)
}
fun Fragment.cropPhoto(source: Uri, destination: Uri,requestCode: Int){
    val activity: FragmentActivity = this.activity ?: return
    val options = UCrop.Options()
    options.setFreeStyleCropEnabled(true)
    UCrop.of(source, destination)
        .withOptions(options)
        .start(activity,this,requestCode)
}