package co.heri.monzo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import info.androidhive.barcode.BarcodeReader
import android.widget.Toast
import com.google.android.gms.vision.barcode.Barcode
import android.util.SparseArray
import android.app.Activity
import android.content.Intent
import android.graphics.RectF
import android.os.Build
import android.view.WindowManager
import info.androidhive.barcode.ScannerOverlay
import kotlinx.android.synthetic.main.activity_scan.*

import info.androidhive.barcode.camera.GraphicOverlay


class ScanActivity : AppCompatActivity(), BarcodeReader.BarcodeReaderListener {

    private lateinit var barcodeReader: BarcodeReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        setContentView(R.layout.activity_scan)

        barcodeReader = (supportFragmentManager.findFragmentById(R.id.barcode_fragment)) as BarcodeReader
    }

    override fun onScanned(barcode: Barcode) {

        val scanOverlay = scan_overLay as ScannerOverlay

        val barcodeBox = RectF(barcode.boundingBox)


        val intersect = barcodeBox.intersect(scanOverlay.rectangle)

       if(intersect) {
           // play beep sound
           barcodeReader.playBeep()

           val returnIntent = Intent()
           returnIntent.putExtra("result", barcode.displayValue)
           returnIntent.putExtra("format", barcode.format)
           returnIntent.putExtra("rawValue", barcode.rawValue)
           setResult(Activity.RESULT_OK, returnIntent)
           finish()

       }

    }

    override fun onScannedMultiple(list: List<Barcode>) {

    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>) {

    }

    override fun onScanError(s: String) {

    }

    override fun onCameraPermissionDenied() {
        Toast.makeText(applicationContext, "Camera permission denied!", Toast.LENGTH_LONG).show()
    }
}
