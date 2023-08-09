package com.team.fantasy.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;

import com.team.fantasy.APICallingPackage.Class.APIRequestManager;
import com.team.fantasy.APICallingPackage.Interface.ResponseManager;
import com.team.fantasy.R;
import com.team.fantasy.resources.StateCityInitializer;
import com.team.fantasy.utils.SessionManager;
import com.team.fantasy.databinding.ActivityUploadKycBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.team.fantasy.APICallingPackage.Class.Validations.ShowToast;
import static com.team.fantasy.APICallingPackage.Config.UPLOADDOUCMENT;
import static com.team.fantasy.APICallingPackage.Constants.UPLOADDOCUMENTTYPE;

public class UploadKYCActivity extends AppCompatActivity implements ResponseManager{

    UploadKYCActivity activity;
    Context context;
    ResponseManager responseManager;
    APIRequestManager apiRequestManager;

    SessionManager sessionManager;


    String[] permissions = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };
    int PICK_IMAGE_GALLERY  = 100;
    int PICK_IMAGE_CAMERA = 101;

    Bitmap bitmap;

    String UserName, DocNumber, DateofBirth, State;
    String PanOrAadhaar;

    List<String> stateList;
    ActivityUploadKycBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_upload_kyc);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        context = activity = this;
        initViews();
        sessionManager = new SessionManager();
        responseManager = this;
        apiRequestManager = new APIRequestManager(activity);

        PanOrAadhaar = getIntent().getStringExtra("DocType");



        if (PanOrAadhaar.equals("Pan")){
            binding.tvEtDocNameBottomText.setText(getResources().getString(R.string.as_on_pan));
            binding.tvVerifyHead.setText(getResources().getString(R.string.verify_pan));
            binding.etDocNumber.setHint(getResources().getString(R.string.pan_no));
            binding.tvEtDocNumberBottomText.setText(getResources().getString(R.string.ten_digit_pan_no));

        }
        else {
            binding.tvEtDocNameBottomText.setText(getResources().getString(R.string.as_on_adhar_card));
            binding.tvVerifyHead.setText(getResources().getString(R.string.verify_adhar));
            binding.etDocNumber.setHint(getResources().getString(R.string.adhar_no));
            binding.tvEtDocNumberBottomText.setText(getResources().getString(R.string.ten_digit_adhar_no));
        }


        binding.tvUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    checkPermissions();
                    ChooseImageDialog();
                    ShowToast(context, "Build.VERSION.SDK_INT >= 23");
                }
                else {
                    ShowToast(context, "Choose");
                    ChooseImageDialog();
                }
            }
        });

        binding.etDocDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(activity,
                        android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                        new mdateListner(), year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources()
                        .getColor(android.R.color.transparent)));
                dialog.show();
            }
        });


        binding.tvSubmitForVerification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserName = binding.etDocName.getText().toString();
                DocNumber = binding.etDocNumber.getText().toString();
                DateofBirth = binding.etDocDob.getText().toString();
                State = binding.etDocState.getText().toString();
                if (bitmap==null){
                    ShowToast(activity,getResources().getString(R.string.pls_select_img));
                }
                else if (UserName.equals("")){
                    ShowToast(activity,getResources().getString(R.string.pls_enter_name));
                }
                else if (DocNumber.equals("")){
                    ShowToast(activity,getResources().getString(R.string.pls_enter_doc_num));
                }
                else if (DateofBirth.equals("")){
                    ShowToast(activity,getResources().getString(R.string.pls_enter_dob));
                }
                else if (State.equals("")){
                    ShowToast(activity,getResources().getString(R.string.pls_enter_state_name));
                }
                else {
                    callUploadDoc(true);
                }

            }
        });



        // <!-- State selector -->
        // Initialize the state list with data from the resource file
        stateList = StateCityInitializer.getStateList(getResources());

        binding.etDocState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showStateListDialog();
            }
        });

        // <!-- State selector -->


    }


    // <!-- State selector -->
    // Method to show the state list dialog
    private void showStateListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select State");
        builder.setItems(stateList.toArray(new String[0]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Set the selected state in the EditText
                binding.etDocState.setText(stateList.get(which));
//                // Show the city list dialog based on the selected state
//                showCityListDialog(stateList.get(which));

            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


    // <!-- State selector -->
    public void initViews() {


        binding.head.tvHeaderName.setText("UPLOAD DOCUMENT");
        binding.head.imBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void callUploadDoc(boolean isShowLoader) {
        try {
            apiRequestManager.callAPI(UPLOADDOUCMENT,
                    createRequestJson(), context, activity, UPLOADDOCUMENTTYPE,
                    isShowLoader, responseManager);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    JSONObject createRequestJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("user_id", sessionManager.getUser(context).getUser_id());
            jsonObject.put("name", UserName);
            jsonObject.put("document_number", DocNumber);
            jsonObject.put("state", State);
            jsonObject.put("dob", DateofBirth);
            jsonObject.put("type", PanOrAadhaar);
            jsonObject.put("document", getStringImage(bitmap));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }



    @Override
    public void getResult(Context mContext, String type, String message, JSONObject result) {
        ShowToast(activity,""+message);
        Intent i  = new Intent(activity,MyAccountActivity.class);
        startActivity(i);
        finish();
    }



    @Override
    public void onError(Context mContext, String type, String message) {
        ShowToast(activity,""+message);
    }

    public void ChooseImageDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(activity);
        builderSingle.setTitle("Choose Image");
        final ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(activity,
                        android.R.layout.simple_list_item_1);
        arrayAdapter.add("Camera");
        arrayAdapter.add("Gallery");

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String strName = arrayAdapter.getItem(which);
                dialog.dismiss();
                if (strName.equals("Gallery")){
                    bitmap = null;
                    binding.imImagePreview.setVisibility(View.GONE);
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_GALLERY);
                }
                else{
                    bitmap = null;
                    binding.imImagePreview.setVisibility(View.GONE);
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, PICK_IMAGE_CAMERA);
                }
            }
        });
        builderSingle.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY &&
                resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.
                        getBitmap(activity.getContentResolver(), filePath);
                binding.imImagePreview.setVisibility(View.VISIBLE);
                binding.imImagePreview.setImageBitmap(bitmap);

                Log.e("Image Path", "onActivityResult: " + filePath);
            } catch (Exception e) {
                bitmap = null;
                e.printStackTrace();
            }
        }
        if (requestCode == PICK_IMAGE_CAMERA && resultCode == RESULT_OK) {
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
                binding.imImagePreview.setVisibility(View.VISIBLE);
                binding.imImagePreview.setImageBitmap(bitmap);

                Log.e("Image Path", "onActivityResult: ");
            } catch (Exception e) {
                bitmap = null;
                e.printStackTrace();
            }
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private boolean checkPermissions() {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new
                            String[listPermissionsNeeded.size()]), 100);
            return false;
        }
        else {
            ChooseImageDialog();
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ChooseImageDialog();
            } else {

            }
            return;
        }
    }

    class mdateListner implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            {int actualMonth = month+1;
                Date d = new Date(year, actualMonth,dayOfMonth);
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, dayOfMonth, 0, 0, 0);
                Date chosenDate = cal.getTime();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                String df_medium_us_str = dateFormat.format(chosenDate);
                SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
                Date date = new Date(year, month, dayOfMonth-1);
                String dayOfWeek = simpledateformat.format(date);
                binding.etDocDob.setText(df_medium_us_str );}
        }
    }
}
