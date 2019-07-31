package com.example.drawexample.ui


import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.print.PrintHelper
import com.example.drawexample.PaintView
import com.example.drawexample.R
import com.example.drawexample.base.BaseActivity
import javax.inject.Inject


class MainActivity : BaseActivity(), MainContract.View {
    @Inject
    lateinit var presenter: MainContract.Presenter
    private val paintView by bindView<PaintView>(R.id.paintView)
    private val btnSave by bindView<Button>(R.id.btn_save)
    private val btnUpload by bindView<Button>(R.id.btn_upload)
    private val btnClear by bindView<Button>(R.id.btn_clear)
    private val btnPrint by bindView<Button>(R.id.btn_print)

    override fun clearCanvas() {
        paintView.clear()
    }

    override fun onShowToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun getDrawingArea(): Bitmap {
        val bitmap = Bitmap.createBitmap(paintView.width, paintView.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        paintView.draw(canvas)
        return bitmap
    }

    override fun printImage(image: Bitmap) {
        this.also {
            PrintHelper(it).apply {
                scaleMode = PrintHelper.SCALE_MODE_FIT
            }.also { printHelper ->
                printHelper.printBitmap("Print Drawing Area Test", image)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        presenter.onAttach(this)
        initMainActivity()
    }

    private fun initMainActivity() {
        btnSave.setOnClickListener {
            presenter.onSaveImageToAlbum()
        }

        btnClear.setOnClickListener {
            presenter.onClearCanvas()
        }

        btnUpload.setOnClickListener {
            presenter.onUploadImage()
        }

        btnPrint.setOnClickListener {
            presenter.onPrintImage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_blue -> paintView.setPaintColor(Color.BLUE)
            R.id.menu_black -> paintView.setPaintColor(Color.BLACK)
            else -> paintView.setPaintColor(Color.BLACK)
        }
        return super.onOptionsItemSelected(item)
    }

}
