package com.firstzoom.milelog.ui;

import static android.app.Activity.RESULT_OK;

import static com.firstzoom.milelog.util.AppConstants.TAG;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firstzoom.milelog.R;
import com.firstzoom.milelog.databinding.FragmentImportBinding;
import com.firstzoom.milelog.model.Trip;
import com.firstzoom.milelog.util.AppConstants;
import com.firstzoom.milelog.util.AppUtil;
import com.firstzoom.milelog.util.RealPathUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ImportFragment extends Fragment {
    private static final int FILE_SELECT_CODE =45 ;
    FragmentImportBinding mBinding;
    private TripViewModel mViewModel;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding=FragmentImportBinding.inflate(inflater,container,false);
        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TripViewModel.class);
        mBinding.buttonBrowse.setOnClickListener(view1 -> {
            showFileChooser();
        });
        mBinding.importButton.setOnClickListener(view1 -> {
String fileName="/storage/emulated/0/Android/data/com.firstzoom.milelog/files/Documents/tripJson/TripJson8962065447572925163.txt";
            //loadJSONArray(getContext(),fileName);
            fillWithStartingData(getContext(),fileName);
        });

        mBinding.exportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getTrips();
            }
        });
    }
    private void getTrips() {
        //displayLoader();
        mViewModel.getTripList().observe(getViewLifecycleOwner(), tripList -> {
          //  hideLoader();
            if(tripList==null){
                //Show snackbar
            }
            else{
                //snackbar
                Gson gson = new Gson();
                Type type = new TypeToken<List<Trip>>(){}.getType();
                String stdJson = gson.toJson(tripList, type);
                //TODO: date
                writeToStorage("TripJson", stdJson);
            }
        });
    }
    private void writeToStorage( String fileName, String jsonContent) {
        File file = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "tripJson");
        if (!file.exists()) {
            file.mkdir();
        }
        try {
            // File mFile = new File(file, fileName);
            File mFile = File.createTempFile(
                    fileName,  /* prefix */
                    ".txt",         /* suffix */
                    file      /* directory */
            );
            Log.d(TAG, "path of export" + mFile.getAbsolutePath());
            FileWriter writer = new FileWriter(mFile);
            writer.append(jsonContent);
            writer.flush();
            writer.close();
            Log.d(TAG, "exist " + mFile.exists());
        } catch (Exception e) {
            Log.d(TAG, " exc" + e.getLocalizedMessage());
            e.printStackTrace();

        }
    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(getContext(), "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    // Get the path
                    String path = RealPathUtil.getRealPath(getContext(), uri);
                    mBinding.editTextPath.setText(path);
                    Log.d(TAG, "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private static JSONArray loadJSONArray(Context context,String filename) {
        StringBuilder builder = new StringBuilder();
        FileInputStream fIn=null;
        // InputStream in = context.getResources().openRawResource(R.raw.contact_list);
       // File file=new File("/storage/emulated/0/Android/data/com.niharika.android.offlinenetworkrepo/files/Documents/exportRepoUserJson/RepoJson3797239566153308362.txt");
        File file=new File(filename);
        try {
            fIn = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(fIn));
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            JSONObject json = new JSONObject(builder.toString());
            Log.d(TAG,"Load json"+json.getJSONArray(""));

            return json.getJSONArray("");

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG,e.getMessage());
        }
        return null;
    }
    private static void fillWithStartingData(Context context,String filename) {
        //RepoDao dao = getDatabase(context).repoDao();
        ArrayList<Trip> list=new ArrayList<>();
        JSONArray trips = loadJSONArray(context,filename);

        try {

            for (int i = 0; i < trips.length(); i++) {
                JSONObject tripsJSONObject = trips.getJSONObject(i);

                String contactName = tripsJSONObject.getString("latitudeStart");
                //String phoneNumber = contact.getString("description");
                //long j=dao.insert(new BaseRepo(contactName,phoneNumber));
                Log.d(TAG,"Trip latStart "+contactName);
                // dao.insert(new Contact(contactName, phoneNumber));
            }

        } catch (JSONException e) {

        }
    }
}