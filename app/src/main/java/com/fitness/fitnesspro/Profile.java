package com.fitness.fitnesspro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile extends AppCompatActivity {
    LinearLayout personalinfo, experience;
    TextView personalinfobtn, reviewbtn, userLocation,edit;
    private FirebaseFirestore fStore;
    private String Username, gender, bDay, userEmail;
    private TextView signOut;
    private String preferred = "";
    private CircleImageView profileImage;
    private static final int PICK_IMAGE = 1;
    private FirebaseAuth mFirebaseAuth;
    private GoogleSignInClient mGoogleSignInClient;

    private String kg = "";
    private TextView email;

    private LoginManager loginManager;
    private FusedLocationProviderClient fusedLocationProvider;
    private Uri fireBaseUri;
    private String cm = "";
    Uri imageUri;
    private String feet, inch, lb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);
        loginManager = LoginManager.getInstance();
        edit = findViewById(R.id.edit);
        signOut = findViewById(R.id.signOutButton);
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.email);
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        personalinfo = findViewById(R.id.personalinfo);
        experience = findViewById(R.id.layout);
        fStore = FirebaseFirestore.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        userLocation = findViewById(R.id.location);
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        personalinfobtn = findViewById(R.id.personalinfobtn);


        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.VISIBLE);


        execute();

        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);


                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));



            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.logOut();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(Profile.this, SignUpActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                signOut();

            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Profile.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void execute() {
        TextView name = findViewById(R.id.userName);
        TextView aboutMe = findViewById(R.id.aboutMe);
        fStore.collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getEmail())
                .get().addOnCompleteListener(task -> {
            if (task.isSuccessful() && task.getResult() != null) {
                Username = task.getResult().getString("name");
                gender = task.getResult().getString("gender");
                bDay = task.getResult().getString("birthday");
                preferred = task.getResult().getString("preferred data Type");
                userEmail = task.getResult().getString("email");
                if (preferred == null) {
                    Intent goBackIntent = new Intent(Profile.this, MainActivity.class);
                    startActivity(goBackIntent);
                    finish();
                    return;
                } else if (preferred.equals("Kg/Cm")) {
                    kg = task.getResult().getString("kg");
                    cm = task.getResult().getString("cm");
                } else {
////                     feet inch lb
                    lb = task.getResult().getString("lb");
                    inch = task.getResult().getString("inch");
                    feet = task.getResult().getString("feet");

                }
                if (Username != null) {
                    name.setText(Username);
                    fireBaseUri = FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl();

                    email.setText(userEmail);
                } else {
                    Toast.makeText(Profile.this, "name is  null", Toast.LENGTH_SHORT).show();
                }

                if (preferred.equals("Kg/Cm")) {
                    aboutMe.setText("Gender: " + gender + "\nName " + Username + "\nKg: " + kg + "\ncm: " + cm +"\nPreferred Data Type: " + preferred);
                } else {
                    aboutMe.setText("Gender: " + gender + "\nName " + Username + "\nlb: " + lb + "\ninch: " + inch + "\nFeet: " + feet+"\nPreferred data type: " + preferred);
                }
                if (ActivityCompat.checkSelfPermission(Profile.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(Profile.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            } else {
                //deal with error
            }
        });

        profileImage = (CircleImageView) findViewById(R.id.profileImage);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(gallery, "select Picture"), PICK_IMAGE);
            }
        });


    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProvider.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(Profile.this, Locale.getDefault());
                    Locale.getDefault();
                    try {
                        List<Address> address = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );
                        userLocation.setText(Html.fromHtml(
                                "<font color=''><b> </b><br></font>"
                                        + address.get(0).getLocality()
                        ));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
          try{

              Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
              profileImage.setImageBitmap(bitmap);
              String bitmapToString = convertBitmapToString(bitmap);
              Log.d("TAG",bitmapToString);

          }catch (IOException e){
              e.printStackTrace();
          }
        }
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(Profile.this, SignUpActivity.class));
                    }
                });
    }

    public static String convertBitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        String result = Base64.encodeToString(byteArray, Base64.DEFAULT);
        return result;
    }
}

