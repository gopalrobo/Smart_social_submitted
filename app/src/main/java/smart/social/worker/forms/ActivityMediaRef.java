package smart.social.worker.forms;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.IOException;

import smart.social.worker.R;

public class ActivityMediaRef extends AppCompatActivity {
    private String filePath = null;
    private PhotoView imgPreview;


    private void previewMedia(boolean paramBoolean, String name) {
        if (paramBoolean) {
            this.imgPreview.setVisibility(View.VISIBLE);
            this.imgPreview.setImageResource(getResources().getIdentifier("smart.audiology.master:drawable/" + name, null, null));
            return;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    protected void onCreate(@Nullable Bundle paramBundle) {
        super.onCreate(paramBundle);
        setContentView(R.layout.activity_media_ref);
        imgPreview = ((PhotoView) findViewById(R.id.previewimg));
        imgPreview.setOnMatrixChangeListener(new MatrixChangeListener());
        imgPreview.setOnPhotoTapListener(new PhotoTapListener());
        imgPreview.setOnSingleFlingListener(new SingleFlingListener());

        Intent localIntent = getIntent();
        this.filePath = localIntent.getStringExtra("filePath");

        boolean bool = localIntent.getBooleanExtra("isImage", true);
        if (this.filePath != null) {
            previewMedia(bool, filePath);
            getSupportActionBar().setTitle(filePath);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            return;
        } else {
            try {
                openPDF();
            } catch (Exception e) {
                Log.e("Exception", e.toString());
            }
        }
        Toast.makeText(getApplicationContext(), "Sorry, file path is missing!", Toast.LENGTH_LONG).show();
    }


    private class PhotoTapListener implements OnPhotoTapListener {

        @Override
        public void onPhotoTap(ImageView view, float x, float y) {
            float xPercentage = x * 100f;
            float yPercentage = y * 100f;

            /// showToast(String.format(PHOTO_TAP_TOAST_STRING, xPercentage, yPercentage, view == null ? 0 : view.getId()));
        }
    }

    private void showToast(CharSequence text) {
//        if (mCurrentToast != null) {
//            mCurrentToast.cancel();
//        }

        //   mCurrentToast = Toast.makeText(SimpleSampleActivity.this, text, Toast.LENGTH_SHORT);
        // mCurrentToast.show();
    }

    private class MatrixChangeListener implements OnMatrixChangedListener {

        @Override
        public void onMatrixChanged(RectF rect) {
            // mCurrMatrixTv.setText(rect.toString());
        }
    }

    private class SingleFlingListener implements OnSingleFlingListener {

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            // Log.d("PhotoView", String.format(FLING_LOG_STRING, velocityX, velocityY));
            return true;
        }
    }

    public String changeCabs(String name) {
        StringBuilder sb = new StringBuilder(name);
        sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        return sb.toString();
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void openPDF() throws IOException {

        //open file in assets

        AssetManager assetManager = getAssets();
        AssetFileDescriptor assetFileDescriptor =
                assetManager.openFd(filePath + ".pdf");
        ParcelFileDescriptor fileDescriptor =
                assetFileDescriptor.getParcelFileDescriptor();

        //open file from sdcard
        /*
        String targetPdf = "/sdcard/test.pdf";
        File file = new File(targetPdf);

        ParcelFileDescriptor fileDescriptor = null;
        fileDescriptor = ParcelFileDescriptor.open(
                file, ParcelFileDescriptor.MODE_READ_ONLY);
        */

        //min. API Level 21
        PdfRenderer pdfRenderer = null;
        pdfRenderer = new PdfRenderer(fileDescriptor);

        final int pageCount = pdfRenderer.getPageCount();
        Toast.makeText(this,
                "pageCount = " + pageCount,
                Toast.LENGTH_LONG).show();

        //Display page 0
        PdfRenderer.Page rendererPage = pdfRenderer.openPage(1);
        int rendererPageWidth = rendererPage.getWidth();
        int rendererPageHeight = rendererPage.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(
                rendererPageWidth,
                rendererPageHeight,
                Bitmap.Config.ARGB_8888);
        rendererPage.render(bitmap, null, null,
                PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);

        imgPreview.setImageBitmap(bitmap);
        rendererPage.close();

        pdfRenderer.close();

        assetFileDescriptor.close();
    }

}
/* Location:           D:\Gopal\downloads\Apk decompile java\dex2jar-0.0.9.15\classes_dex2jar.jar
 * Qualified Name:     smart.breed.contest.ActivityMediaShepherd
 * JD-Core Version:    0.6.0
 */