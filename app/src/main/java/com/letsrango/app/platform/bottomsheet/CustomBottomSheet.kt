package com.letsrango.app.platform.bottomsheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.letsrango.app.feature.home.presentation.HomeActivity


class CustomBottomSheet(context: Context) : BottomSheetDialog(context) {
    companion object {
        fun newInstance(activity: HomeActivity): BottomSheetDialog {
            return CustomBottomSheet(activity)
        }
    }
}