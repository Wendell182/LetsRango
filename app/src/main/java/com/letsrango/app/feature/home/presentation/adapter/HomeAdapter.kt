package com.letsrango.app.feature.home.presentation.adapter

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.os.StrictMode
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.letsrango.app.feature.home.data.service.model.CardDataItem
import com.letsrango.app.feature.home.presentation.HomeActivity
import com.letsrango.app.platform.bottomsheet.CustomBottomSheet
import com.letsrango.app.platform.extensions.getScreenHeight
import com.letsrango.app.platform.extensions.getScreenWidth
import com.letsrango.app.platform.extensions.parseMoney
import com.letsrango.app.platform.util.cache.CacheUtil
import kotlinx.android.synthetic.main.bottomsheet_cart.*
import kotlinx.android.synthetic.main.bottomsheet_cart.view.*
import kotlinx.android.synthetic.main.bottomsheet_pic.*
import kotlinx.android.synthetic.main.bottomsheet_pic.view.*
import kotlinx.android.synthetic.main.card_layout.view.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import kotlin.random.Random
import kotlinx.android.synthetic.main.card_layout.view.picPrato as picPrato1


class HomeAdapter(private var context: Context, private var list: ArrayList<CardDataItem>) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    var mActivity: HomeActivity = context as HomeActivity
    lateinit var cacheUtil: CacheUtil

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAdapter.ViewHolder {
        view = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }


    override fun onBindViewHolder(holder: HomeAdapter.ViewHolder, position: Int) {
        var item = list[position]
        holder.btnAdd.setText(item.btnText)
        holder.txtDesc.setText(item.desc)
        holder.txtPrice.setText(item.price.parseMoney())
        holder.txtTitle.setText(item.title)
        Glide.with(context)
            .load(item.picture)
            .centerCrop()
            .into(holder.picPrato)

        holder.btnAdd.setOnClickListener {
            openBottomSheet(item)
        }

        holder.picPrato.setOnClickListener {
            openBottomSheetPic(item)
        }

        holder.txtTitle.setOnClickListener {
            openBottomSheetPic(item)
        }

        holder.txtDesc.setOnClickListener {
            openBottomSheetPic(item)
        }

    }

    fun shareBitmap(pic: Bitmap, text: String, bottomSheet: BottomSheetDialog) {
        bottomSheetShare.parentView.addView(ProgressBar(mActivity).apply {
            this.isIndeterminate = true
            this.id = Random.nextInt() + 2000
            this.tag = "loading"
            this.layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                this.leftMargin = mActivity.getScreenWidth() / 2
                this.topMargin = mActivity.getScreenHeight() / 2
            }
        })
        "btnShared".toFloatActionButton().visibility = View.VISIBLE
        var builder = StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/*"
        val stream = ByteArrayOutputStream()
        i.putExtra(
            Intent.EXTRA_STREAM,
            getLocalBitmapUri(pic, mActivity)
        )


        try {
            mActivity.startActivityForResult(Intent.createChooser(i, text), 500)
        } catch (ex: ActivityNotFoundException) {
            ex.printStackTrace()
        }
    }

    fun clearProgressBar() {
            try{
                if (bottomSheetShare.parentView.findViewWithTag<ProgressBar>("loading")!= null) {
                    bottomSheetShare.parentView.removeView(
                        bottomSheetShare.parentView.findViewWithTag<ProgressBar>(
                            "loading"
                        )
                    )

                } else {
                    mActivity.recreate()
                }

            }catch (e:Exception){
                mActivity.recreate()
            }

    }

    fun String.toFloatActionButton(): MaterialButton {
        return bottomSheetShare.parentView.findViewWithTag<MaterialButton>(this)
    }

    fun getLocalBitmapUri(bmp: Bitmap, context: Context): Uri? {
        var bmpUri: Uri? = null
        try {
            val file = File(
                context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                "share_image_" + System.currentTimeMillis() + ".png"
            )
            val out = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out)
            out.close()
            bmpUri = Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bmpUri
    }


    fun openBottomSheet(item: CardDataItem) {

        var bottomSheet = CustomBottomSheet.newInstance(context as HomeActivity)

        val contentView = View.inflate(context as HomeActivity, R.layout.bottomsheet_cart, null)
        bottomSheet.setContentView(contentView)
        var mBehavior = BottomSheetBehavior.from(contentView.parent as View);
        mBehavior.peekHeight = ((context as HomeActivity).getScreenHeight() * 0.9).toInt()
        contentView.textPergunta.text =
            Html.fromHtml("Quantos <strong>" + item.title + "</strong> deseja adicionar? ")
        bottomSheet.show()
        var txtQt = contentView.textQuantia
        var btnPlus = contentView.btnMais
        var btnLess = contentView.btnMenos
        var btnOk = contentView.btnOk

        cacheUtil = CacheUtil().newInstance(mActivity)

        var qt = cacheUtil.getItemQt(item.id) ?: 0


        txtQt.text = qt.toString()


        btnPlus.setOnClickListener() {
            btnLess.isEnabled = true
            qt = qt.plus(1)
            bottomSheet.btnOk.setText("Ok")
            cacheUtil.setItemQt(item.id, qt)
            txtQt.text = qt.toString()
        }

        btnLess.setOnClickListener() {
            qt = qt.minus(1)
            if (qt > 0) {
                bottomSheet.btnOk.setText("Ok")
                btnLess.isEnabled = true
                cacheUtil.setItemQt(item.id, qt)
                txtQt.text = qt.toString()
            } else {
                btnLess.isEnabled = false
                bottomSheet.btnOk.setText("Remover")
                cacheUtil.setItemQt(item.id, qt)
                txtQt.text = qt.toString()
            }
        }

        bottomSheet.btnOk.setOnClickListener() {

            bottomSheet.dismissWithAnimation = true
            bottomSheet.dismiss()
            mActivity.getCart()
        }

    }

    var bottomSheetShare = CustomBottomSheet.newInstance(mActivity)

    lateinit var contentView: View

    @SuppressLint("ResourceType")
    fun openBottomSheetPic(item: CardDataItem) {
        var picBitmap: Bitmap? = null
        //var bottomSheet = CustomBottomSheet.newInstance(mActivity)
        contentView = View.inflate(mActivity, R.layout.bottomsheet_pic, null)
        bottomSheetShare.setContentView(contentView)
        var mBehavior = BottomSheetBehavior.from(contentView.parent as View);
        mBehavior.peekHeight = mActivity.getScreenHeight()
        Glide.with(mActivity)
            .asBitmap()
            .load(item.picture)
            .centerCrop()
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    picBitmap = resource
                    val d = BitmapDrawable(mActivity.resources, resource)

                    bottomSheetShare.PicIm.setImageDrawable(d)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                }
            })


        val btnShare = MaterialButton(mActivity)
        btnShare.id = Random.nextInt() + 1021
        btnShare.tag = "btnShared"
        btnShare.setIconResource(R.drawable.ic_share_black_24dp)
        btnShare.setIconTintResource(R.color.white)
        btnShare.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        //btnShare.strokeColor = ColorStateList.valueOf(R.color.colorAccent)
        btnShare.setTextColor((ContextCompat.getColor(context, R.color.white)))
        btnShare.setPadding(20,8,20,8)
        btnShare.cornerRadius = 250
        btnShare.setText("Compartilhar")
        btnShare.setOnClickListener() {
            shareBitmap(
                picBitmap!!,
                item.title + " só " + item.price.parseMoney(),
                bottomSheetShare
            )
        }
        val layoutNew = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT

            )
        btnShare.layoutParams = layoutNew.apply {
            topMargin = (mActivity.getScreenHeight() *0.02f).toInt()
            leftMargin = (mActivity.getScreenWidth() * 0.02f).toInt()
        }
        bottomSheetShare.parentView.addView(btnShare)
        bottomSheetShare.show()
    }


    inner class ViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        internal var txtTitle = itemView.textTitle as TextView
        internal var picPrato = itemView.picPrato1 as ImageView
        internal var txtPrice = itemView.textPrice as TextView
        internal var txtDesc = itemView.textDesc as TextView
        internal var btnAdd = itemView.btnAdd as MaterialButton
    }

    lateinit var view: View
}