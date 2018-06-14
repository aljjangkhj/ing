package www.khj08.com.dateplan.ui;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ImageButton imageButton01;
    private ImageButton imageButton02;
    private ImageView imageView01;
    private ImageView imageView02;
    private Button nav_btn;
    private int imagebtn01 = 0;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private String img01;
    private String img02;
    private String mainImage;
    private Bitmap myBitmap;
    private int selectpick;
    private Uri mImageCaptureUri;
    private TextView resultmoney;
    private TextView resultTime;
    private TextView moenyset;
    private TextView timeset;
    private TextView manName;
    private TextView womanName;
    private TextView mainText;
    private TextView MainDdayText;
    private TextView nav_textView;
    private SQLiteDBManager mSQLiteDBManager;
    private String mRefResultMoney;
    private int mRefid;
    private int finalresult;
    private BackPressCloseSystem backPressCloseSystem;
    private int finalResultTime;
    private int sqltime;
    private ImageButton imageButton_nav;
    private ImageView imageView_nav;
    private ConstraintLayout constraintlayout;
    private int mRefBackground = 0;
    private String strdrawable;
    private Bitmap bitmap;
    private int savebackground;
    String mCurrentPhotoPath;
    Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_autoscreen(1);
        //권한체크
        checkPermission();
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){ //사용자의 안드로이드 OS버전이 마시멜로우 이상인지 체크. 맞다면 IF문 내부의 소스코드 작동.
            Log.v("mylog","if=1");
            int permissionResult = checkSelfPermission(Manifest.permission.CALL_PHONE); // 해당 퍼미션 체크.
            Log.v("mylog","permissionResult: "+ permissionResult);
            if(permissionResult == PackageManager.PERMISSION_DENIED){ // 해당 퍼미션 권한여부 체크.
                *//*
                 * 해당 권한이 거부된 적이 있는지 유무 판별 해야함.
                 * 거부된 적이 있으면 true, 거부된 적이 없으면 false 리턴
                 *//*
                Log.v("mylog","if=2");
                if(shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)){ // 거부된 적이 있으면 해당 권한을 사용할 때 상세 내용을 설명. 거부한 적 있으면 true 리턴.
                    Log.v("mylog","if=3");
                    android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(MainActivity.this);
                    dialog.setTitle("권한이 필요합니다.")
                            .setMessage("이 기능을 사용하기 위해서는 단말기의 \"카메라\"권한이 필요합니다. 계속 하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                                        requestPermissions(new String[]{Manifest.permission.CALL_PHONE}, 1000);
                                    }
                                }
                            })
                            .setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Toast.makeText(MainActivity.this, "기능을 취소했습니다", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .create()
                            .show();
                }else{ // 거부된 적이 없으면 설명 없이 해당 권한을 요청.
                    Log.v("mylog","else=1");
                }
            }else{ // 사용자가 권한을 승락함. 바로 실행.
                Log.v("mylog","else=2");
            }
        }else{//마시멜로우 미만 버전 즉시실행
            Log.v("mylog","else=3");
        }*/
        //int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR);
        /*String[] permissions = new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            for(String permission : permissions){
                int result = PermissionChecker.checkSelfPermission(this, permission);

                if(result == PermissionChecker.PERMISSION_GRANTED){

                }else{
                    doRequestPermission();
                }
            }
        }*/


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        backPressCloseSystem = new BackPressCloseSystem(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        //광고테스트
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        /*AdView mAdView2 = (AdView)findViewById(R.id.adView2);
        AdRequest adRequest2 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
        mAdView2.loadAd(adRequest2);*/
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "등록되어있지 않습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        myMainImage();//사진이벤트
        TextClickEvent();//메인클릭이벤트
        mybackgroundcolor();//배경화면설정
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/BMJUA_ttf.ttf");//폰트
        mainText = (TextView) this.findViewById(R.id.main_textview01);
        MainDdayText = (TextView) this.findViewById(R.id.main_dday_text);
        resultTime = (TextView) this.findViewById(R.id.resultTime);
        resultmoney = (TextView) this.findViewById(R.id.resultMoney);
        manName = (TextView) this.findViewById(R.id.manName);
        womanName = (TextView) this.findViewById(R.id.womanName);
        moenyset = (TextView) this.findViewById(R.id.moneyset_text);
        timeset = (TextView) this.findViewById(R.id.timeset_text);
        imageView01 = (ImageView) this.findViewById(R.id.man01);
        imageView02 = (ImageView) this.findViewById(R.id.woman2);
        imageButton01 = (ImageButton) findViewById(R.id.man);
        imageButton02 = (ImageButton) this.findViewById(R.id.woman);
        constraintlayout = (ConstraintLayout) this.findViewById(R.id.main_layout);


        //setTextfont(moenyset);
        this.moenyset.setTypeface(typeface);
        this.timeset.setTypeface(typeface);
        this.mainText.setTypeface(typeface);
        this.MainDdayText.setTypeface(typeface);
        this.resultTime.setTypeface(typeface);
        this.resultmoney.setTypeface(typeface);
        this.manName.setTypeface(typeface);
        this.womanName.setTypeface(typeface);
        this.nav_textView.setTypeface(typeface);

        this.mainText.setText(MySharedPreferencesManager.getStartLoveDay(this));
//        this.MainDdayText.setText(MySharedPreferencesManager.getDdaySave(this));
        this.manName.setText(MySharedPreferencesManager.getPic01(this));
        this.womanName.setText(MySharedPreferencesManager.getPic02(this));
        this.nav_textView.setText(MySharedPreferencesManager.getMainEdit(MainActivity.this));


        Bitmap bitmap = StringToBitMap(MySharedPreferencesManager.getManPicture(this));
        this.imageView01.setImageBitmap(bitmap);
        Bitmap bitmap2 = StringToBitMap(MySharedPreferencesManager.getWomanPicture(this));
        this.imageView02.setImageBitmap(bitmap2);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month + 1, day);
        long tday = calendar.getTimeInMillis() / 86400000;
        long a = MySharedPreferencesManager.getToday(this);
        long b = MySharedPreferencesManager.getLoveDay(this);
        Date mdate = new Date();
        long tt = mdate.getTime() / 86400000;
        Date mdate2 = new Date();
        mdate2.setYear(MySharedPreferencesManager.getYear(this)-1900);
        mdate2.setMonth(MySharedPreferencesManager.getMonth(this)-1);
        mdate2.setDate(MySharedPreferencesManager.getDay(this)-1);
        long tt2 = mdate2.getTime() / 86400000;

        // Log.v("mylog","오늘날짜시간: "+a);
        //  Log.v("mylog","사용자 설정 날짜시간: "+b);
        if (a == tday) {
            //Log.v("mylog","d-day: "+(a-b));
            this.MainDdayText.setText("D+" + (tt - tt2) + " ~잉ing");
        } else {
            // Log.v("mylog","dday:"+(tday-b));
            if (b == 0) {
                this.MainDdayText.setText("만나기 시작한 날을 설정해 주세요.");
            } else {
                this.MainDdayText.setText("D+" + (tt - tt2) + " ~잉ing");
            }
        }
        /*int myHour = MySharedPreferencesManager.getHour01(this);
        int myMinute = MySharedPreferencesManager.getMinute01(this);
        this.resultTime.setText(myHour+"시간"+myMinute+"분");*/
        mSQLiteDBManager = SQLiteDBManager.getInstance(this);
        String[] columns = new String[]{"_id", "resultmoney", "finalminute"};
        Cursor c = mSQLiteDBManager.query(columns, null, null, null, null, null);
        if (c != null) {
            while (c.moveToNext()) {
                NumberFormat nf = NumberFormat.getNumberInstance();
                mRefid = c.getInt(0);
                mRefResultMoney = c.getString(1);
                sqltime = c.getInt(2);
                finalResultTime += sqltime;
                int hour = finalResultTime / 60;
                int minute = finalResultTime % 60;

                resultTime.setText(hour + "시간 " + minute + "분");
                finalresult += Integer.parseInt(mRefResultMoney);
                String numberstr = nf.format(finalresult);
                resultmoney.setText(numberstr + "원");
                //Log.v("mylog","finalresult: "+ finalresult);
                //Log.v("mylog", "date " + mRefDate);
                // Toast.makeText(DateListView.this, "저장된 날짜는: " + mRefDate, Toast.LENGTH_SHORT).show();
            }
        }
        /*String [] strarray = {String.valueOf(iid)};
        for(int i = 0; i <= strarray.length; i++ ){
            selection = "_id='"+i+"'";
        }
        String[] columns02 = new String[]{"_id","resultmoney"};
        Cursor c02 = mSQLiteDBManager.query(columns02, selection, null, null, null, null);
        if (c02 != null) {
            while (c02.moveToNext()) {
                int iid = c02.getInt(0);
                String mRefResultmoney = c02.getString(1);
                Log.v("mylog","비용 불러오기: "+ mRefResultmoney);
                Toast.makeText(this, "총 비용은 " + mRefResultmoney, Toast.LENGTH_SHORT).show();
                //Log.v("mylog", "date " + mRefDate);
                // Toast.makeText(DateListView.this, "저장된 날짜는: " + mRefDate, Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    /*//권한획득
    private void doRequestPermission(){
        String [] permissions = new String []{ Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

        ArrayList<String> notGrantedPermissions = new ArrayList<>();
        for(String perm : permissions){
            if(!PermissionUtils.hasPermissions(this, perm)){
                notGrantedPermissions.add(perm);
            }
        }
        ActivityCompat.requestPermissions(this,notGrantedPermissions.toArray(new String[] {}), PermissionUtils.MUST_HAVE_REQUEST_CODE);
    }*/
    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 1:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }
                break;
            default:
                break;
        }
    }

    public void myMainImage() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        //View myView = navigationView.inflateHeaderView(R.layout.nav_header_main);
        View myView = navigationView.getHeaderView(0);
        imageButton_nav = (ImageButton) myView.findViewById(R.id.nav_header_layout_imageButton);
        imageView_nav = (ImageView) myView.findViewById(R.id.nav_header_layout_imageView);
        Bitmap bitmap = StringToBitMap(MySharedPreferencesManager.getMainPicture(this));
        imageView_nav.setImageBitmap(bitmap);

        nav_btn = (Button) myView.findViewById(R.id.nav_header_layout_Btn);
        nav_textView = (TextView) myView.findViewById(R.id.nav_header_layout_TextView);

        nav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();
                final View dialogView = layoutInflater.inflate(R.layout.edittext_nav_dialog, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setIcon(R.mipmap.ic_launcher_plan);
                builder.setTitle("딱! 한!줄!만~");
                builder.setView(dialogView);
                builder.setPositiveButton("명언 남기기", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText firstName = (EditText) dialogView.findViewById(R.id.shortEdit);
                        String shortName = firstName.getText().toString();
                        MySharedPreferencesManager.setMainEdit(shortName, MainActivity.this);
                        nav_textView.setText(shortName);

                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        imageButton_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagebtn01 = 2;
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(MainActivity.this).setTitle("업로드할 사진을 선택합니다.")
                        .setMessage("사랑하는 사람의 사진을 올려보세요!")
                        .setIcon(R.mipmap.plancamera)
                        .setPositiveButton("사진 촬영", cameraListener)
                        .setNegativeButton("앨범 선택", albumListener)
                        .setNeutralButton("취소", cancelListener).show();
            }
        });
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    //이미지파일 Bitmap을 String함수로 변환
    private static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    private File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        return image;
    }

    public void doTakePhotoAction() {

        String state = Environment.getExternalStorageState();
        if(Environment.MEDIA_MOUNTED.equals(state)){
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if(intent.resolveActivity(getPackageManager()) != null){
                File photoFile = null;
                try{
                    photoFile = createImageFile();
                }catch (IOException e){
                    Log.v("mylog", "카메라에러: " + e);
                }
                if(photoFile != null){
                    //String mPath = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
                    // 임시로 사용할 파일의 경로를 생성
//        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
//        mImageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        photoURI = FileProvider.getUriForFile(this, getApplicationContext().getPackageName()+".fileprovider", photoFile);
                        mImageCaptureUri = photoURI;
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                        Log.v("mylog","photoURI: "+ photoURI.toString());
                        Log.v("mylog","photoFile: "+ photoFile.toString());
                    } else {
                        mImageCaptureUri = Uri.fromFile(photoFile);
                        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    }
                    //intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    // 특정기기에서 사진을 저장못하는 문제가 있어 다음을 주석처리 합니다.
                    intent.putExtra("return-data", true);
                    startActivityForResult(intent, PICK_FROM_CAMERA);
                }
            }
        }
    }

    private void doTakeAlbumAction() {
        // 앨범 호출

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    //이미지 버튼이 2개이므로 전역변수로 선언
    public static void setImageBitmaps(Bitmap bitmap, ImageView imageButton) {
        imageButton.setImageBitmap(bitmap);
    }
    public String getImageNameToUri(Uri data){

        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/")+1);
        return imgName;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case CROP_FROM_CAMERA: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                final Bundle extras = data.getExtras();
                if (extras != null) {
                    myBitmap = extras.getParcelable("data");
                    if (imagebtn01 == 0) {
                        setImageBitmaps(myBitmap, imageView01);
                        img01 = BitMapToString(myBitmap);
                        MySharedPreferencesManager.setManPicture(img01, this);
                    }
                    if (imagebtn01 == 1) {
                        setImageBitmaps(myBitmap, imageView02);
                        img02 = BitMapToString(myBitmap);
                        MySharedPreferencesManager.setWomanPicture(img02, this);
                    }
                    if (imagebtn01 == 2) {
                        setImageBitmaps(myBitmap, imageView_nav);
                        mainImage = BitMapToString(myBitmap);
                        MySharedPreferencesManager.setMainPicture(mainImage, this);
                    }
                }
                // 임시 파일 삭제
                File f = new File(mImageCaptureUri.getPath());
                if (f.exists()) {
                    f.delete();
                }
                break;
            }
            case PICK_FROM_ALBUM: {
                // 이후의 처리가 카메라와 같으므로 일단  break없이 진행합니다.
                // 실제 코드에서는 좀더 합리적인 방법을 선택하시기 바랍니다.
                mImageCaptureUri = data.getData();
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
                Log.v("mylog","mImageCaptureUri: " + mImageCaptureUri);
                intent.putExtra("crop","true");
                intent.putExtra("outputX", true);
                intent.putExtra("outputY", true);
                intent.putExtra("aspectX", 1);//1
                intent.putExtra("aspectY", 1);//1
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_CAMERA);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        backPressCloseSystem.onBackPressed();
        /*if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //2018.03.28
    @Override
    public void onStart(){
        super.onStart();
        Log.v("mylog","onStart()");
        this.mainText.setText(MySharedPreferencesManager.getStartLoveDay(this));
        this.manName.setText(MySharedPreferencesManager.getPic01(this));
        this.womanName.setText(MySharedPreferencesManager.getPic02(this));
        this.nav_textView.setText(MySharedPreferencesManager.getMainEdit(MainActivity.this));
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(year, month + 1, day);
        long tday = calendar.getTimeInMillis() / 86400000;
        long a = MySharedPreferencesManager.getToday(this);
        long b = MySharedPreferencesManager.getLoveDay(this);
        // Log.v("mylog","오늘날짜시간: "+a);
        //  Log.v("mylog","사용자 설정 날짜시간: "+b);
        if (a == tday) {
            //Log.v("mylog","d-day: "+(a-b));
            this.MainDdayText.setText("D+" + (a - b) + " ~잉ing");
        } else {
            // Log.v("mylog","dday:"+(tday-b));
            if (b == 0) {
                this.MainDdayText.setText("만나기 시작한 날을 설정해 주세요.");
            } else {
                this.MainDdayText.setText("D+" + (tday - b) + " ~잉ing");
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.path_Btn) {
            Intent intent = new Intent(MainActivity.this, setActivity.class);
            MoveToActivity(intent);
            return true;
        }if (id == R.id.backgroundColor_btn) {
            AlertDialog alterDialog02 = null;
            AlertDialog.Builder alert02 = new AlertDialog.Builder(this);
            DialogInterface.OnClickListener singlelistener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (which == 0) {
                        // Drawable dwable = ContextCompat.getDrawable(MainActivity.this, R.drawable.mainimage02);

                        // ColorDrawable cd = (ColorDrawable)drawable;
//                        int color = mRefBackground;
                        //                      Toast.makeText(MainActivity.this, "color: "+ color, Toast.LENGTH_SHORT).show();

                        // Drawable drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.mainimage02);

                        constraintlayout.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.mainimage02));
                        MySharedPreferencesManager.saveColor(R.color.color2, MainActivity.this);

                        //strdrawable = BitMapToString(bitmap);
                        //MySharedPreferencesManager.saveColor(strdrawable, MainActivity.this);
                        resultmoney.setTextColor(Color.WHITE);
                        resultTime.setTextColor(Color.WHITE);
                        moenyset.setTextColor(Color.WHITE);
                        timeset.setTextColor(Color.WHITE);
                        manName.setTextColor(Color.WHITE);
                        womanName.setTextColor(Color.WHITE);
                        mainText.setTextColor(Color.WHITE);
                        MainDdayText.setTextColor(Color.WHITE);
                        nav_textView.setTextColor(Color.BLACK);

                        // Toast.makeText(MainActivity.this, color+"", Toast.LENGTH_SHORT).show();
                    }
                    if (which == 1) {
                        constraintlayout.setBackgroundColor(Color.WHITE);
                        MySharedPreferencesManager.saveColor(Color.WHITE, MainActivity.this);
                        resultmoney.setTextColor(Color.BLACK);
                        resultTime.setTextColor(Color.BLACK);
                        moenyset.setTextColor(Color.BLACK);
                        timeset.setTextColor(Color.BLACK);
                        manName.setTextColor(Color.BLACK);
                        womanName.setTextColor(Color.BLACK);
                        mainText.setTextColor(Color.BLACK);
                        MainDdayText.setTextColor(Color.BLACK);
                        nav_textView.setTextColor(Color.BLACK);
                     //   Toast.makeText(MainActivity.this, Color.WHITE + "", Toast.LENGTH_SHORT).show();
                        //mRefBackground = 1;
                    }
                    if (which == 2) {
                        constraintlayout.setBackgroundColor(Color.BLACK);
                        resultmoney.setTextColor(Color.WHITE);
                        resultTime.setTextColor(Color.WHITE);
                        moenyset.setTextColor(Color.WHITE);
                        timeset.setTextColor(Color.WHITE);
                        manName.setTextColor(Color.WHITE);
                        womanName.setTextColor(Color.WHITE);
                        mainText.setTextColor(Color.WHITE);
                        MainDdayText.setTextColor(Color.WHITE);
                        nav_textView.setTextColor(Color.BLACK);
                        MySharedPreferencesManager.saveColor(Color.BLACK, MainActivity.this);
                      //  Toast.makeText(MainActivity.this, Color.BLACK + "", Toast.LENGTH_SHORT).show();
                        // mRefBackground = 2;
                    }
                    if (which == 3) {
                        int color = Color.parseColor("#FAED7D");
                        constraintlayout.setBackgroundColor(color);
                        resultmoney.setTextColor(Color.BLACK);
                        resultTime.setTextColor(Color.BLACK);
                        moenyset.setTextColor(Color.BLACK);
                        timeset.setTextColor(Color.BLACK);
                        manName.setTextColor(Color.BLACK);
                        womanName.setTextColor(Color.BLACK);
                        mainText.setTextColor(Color.BLACK);
                        MainDdayText.setTextColor(Color.BLACK);
                        nav_textView.setTextColor(Color.BLACK);
                        //  mRefBackground = 3;
                        MySharedPreferencesManager.saveColor(color, MainActivity.this);
                       // Toast.makeText(MainActivity.this, color + "", Toast.LENGTH_SHORT).show();
                    }
                }
            };
            alert02.setSingleChoiceItems(new String[]{"기본", "흰색", "검정색", "연한 노랑색"}, 0, singlelistener);

            alert02.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast.makeText(MainActivity.this, "저장되었습니다.", Toast.LENGTH_SHORT).show();
                }
            });
            alert02.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            alterDialog02 = alert02.create();
            alterDialog02.setTitle("배경 색상 변경하기");
            alterDialog02.setIcon(R.mipmap.backgroundcolor);
            alterDialog02.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            //   Toast.makeText(this, "일기쓰기", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DatePickerActivity.class);
            MoveToActivity(intent);

        } else if (id == R.id.nav_gallery) {
            //  Toast.makeText(this, "D - DAY", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, D_Day_Activity.class);
            MoveToActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            //Toast.makeText(this, "일기 목록", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, DateListView.class);
            MoveToActivity(intent);

        } else if (id == R.id.nav_calc) {
            //Toast.makeText(this, "가계부", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, CalcResult.class);
            MoveToActivity(intent);

        } /*else if (id == R.id.nav_massage) {
            Toast.makeText(this, "대화하기", Toast.LENGTH_SHORT).show();

        }*/ else if (id == R.id.nav_set) {
            //Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, setActivity.class);
            MoveToActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void mybackgroundcolor(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View myView = navigationView.getHeaderView(0);
        mainText = (TextView) this.findViewById(R.id.main_textview01);
        MainDdayText = (TextView) this.findViewById(R.id.main_dday_text);
        resultTime = (TextView) this.findViewById(R.id.resultTime);
        resultmoney = (TextView) this.findViewById(R.id.resultMoney);
        manName = (TextView) this.findViewById(R.id.manName);
        womanName = (TextView) this.findViewById(R.id.womanName);
        moenyset = (TextView) this.findViewById(R.id.moneyset_text);
        timeset = (TextView) this.findViewById(R.id.timeset_text);
        imageView01 = (ImageView) this.findViewById(R.id.man01);
        imageView02 = (ImageView) this.findViewById(R.id.woman2);
        imageButton01 = (ImageButton) findViewById(R.id.man);
        imageButton02 = (ImageButton) this.findViewById(R.id.woman);
        constraintlayout = (ConstraintLayout) this.findViewById(R.id.main_layout);
        nav_textView = (TextView) myView.findViewById(R.id.nav_header_layout_TextView);

        savebackground = MySharedPreferencesManager.getSaveColor(this);
        if (savebackground == 0){

        }
        else{
            if(R.color.color2 == savebackground){
                constraintlayout.setBackgroundDrawable( ContextCompat.getDrawable(MainActivity.this, R.drawable.mainimage02));
                resultmoney.setTextColor(Color.WHITE);
                resultTime.setTextColor(Color.WHITE);
                moenyset.setTextColor(Color.WHITE);
                timeset.setTextColor(Color.WHITE);
                manName.setTextColor(Color.WHITE);
                womanName.setTextColor(Color.WHITE);
                mainText.setTextColor(Color.WHITE);
                MainDdayText.setTextColor(Color.WHITE);
                nav_textView.setTextColor(Color.BLACK);
            }
            else if(savebackground == Color.WHITE){
                int color = Color.parseColor("#FFFFFF");
                constraintlayout.setBackgroundColor(color);
                resultmoney.setTextColor(Color.BLACK);
                resultTime.setTextColor(Color.BLACK);
                moenyset.setTextColor(Color.BLACK);
                timeset.setTextColor(Color.BLACK);
                manName.setTextColor(Color.BLACK);
                womanName.setTextColor(Color.BLACK);
                mainText.setTextColor(Color.BLACK);
                MainDdayText.setTextColor(Color.BLACK);
                nav_textView.setTextColor(Color.BLACK);
            }
            else if(savebackground == Color.BLACK){
                int color = Color.parseColor("#000000");
                constraintlayout.setBackgroundColor(color);
                resultmoney.setTextColor(Color.WHITE);
                resultTime.setTextColor(Color.WHITE);
                moenyset.setTextColor(Color.WHITE);
                timeset.setTextColor(Color.WHITE);
                manName.setTextColor(Color.WHITE);
                womanName.setTextColor(Color.WHITE);
                mainText.setTextColor(Color.WHITE);
                MainDdayText.setTextColor(Color.WHITE);
                nav_textView.setTextColor(Color.BLACK);
            }
            else if(savebackground == Color.parseColor("#FAED7D")){
                constraintlayout.setBackgroundColor(Color.parseColor("#FAED7D"));
                resultmoney.setTextColor(Color.BLACK);
                resultTime.setTextColor(Color.BLACK);
                moenyset.setTextColor(Color.BLACK);
                timeset.setTextColor(Color.BLACK);
                manName.setTextColor(Color.BLACK);
                womanName.setTextColor(Color.BLACK);
                mainText.setTextColor(Color.BLACK);
                MainDdayText.setTextColor(Color.BLACK);
                nav_textView.setTextColor(Color.BLACK);
            }
        }
    }
    private void TextClickEvent(){
        mainText = (TextView) this.findViewById(R.id.main_textview01);
        MainDdayText = (TextView) this.findViewById(R.id.main_dday_text);
        manName = (TextView) this.findViewById(R.id.manName);
        womanName = (TextView) this.findViewById(R.id.womanName);
        imageButton01 = (ImageButton) findViewById(R.id.man);
        imageButton02 = (ImageButton) this.findViewById(R.id.woman);

        MainDdayText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setActivity.class);
                MoveToActivity(intent);
            }
        });
        mainText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setActivity.class );
                MoveToActivity(intent);
            }
        });
        manName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setActivity.class );
                MoveToActivity(intent);
            }
        });
        womanName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, setActivity.class );
                MoveToActivity(intent);
            }
        });
        imageButton01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagebtn01 = 0;
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.mipmap.plancamera)
                        .setTitle("업로드할 사진을 선택합니다.")
                        .setMessage("사랑하는 사람의 사진을 올려보세요!")
                        .setPositiveButton("사진 촬영", cameraListener)
                        .setNegativeButton("앨범 선택", albumListener)
                        .setNeutralButton("취소", cancelListener).show();

            }
        });
        imageButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagebtn01 = 1;
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(R.mipmap.plancamera)
                        .setTitle("업로드할 사진을 선택합니다.")
                        .setMessage("사랑하는 사람의 사진을 올려보세요!")
                        .setPositiveButton("사진 촬영", cameraListener)
                        .setNegativeButton("앨범 선택", albumListener)
                        .setNeutralButton("취소", cancelListener).show();
            }
        });
    }
    /*public static void setGlobalFont(Context context,View view){
        if (view != null) {
            if (view instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) view;
                int len = vg.getChildCount();
                for (int i = 0; i < len; i++) {
                    View v = vg.getChildAt(i);
                    if (v instanceof TextView) {
                        ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), ""));
                    }
                    setGlobalFont(context, v);
                }
            }
        } else {
        }

    }*///MainActivity.setGlobalFont(this, getWindow().getDecorView())
}

class BackPressCloseSystem {
    private long backKeyPressedTime = 0;
    private Toast toast;
    private Activity activity;

    public BackPressCloseSystem(Activity activity) {
        this.activity = activity;
    }

    public void onBackPressed() {
        if (isAfter2Seconds()) {
            backKeyPressedTime = System.currentTimeMillis();
            // 현재시간을 다시 초기화
            toast = Toast.makeText(activity,
                    "뒤로 버튼을 한번 더 누르시면 종료됩니다.",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (isBefore2Seconds()) {
            programShutdown();
            toast.cancel();
        }
    }

    private Boolean isAfter2Seconds() {
        return System.currentTimeMillis() > backKeyPressedTime + 2000;
        // 2초 지났을 경우
    }

    private Boolean isBefore2Seconds() {
        return System.currentTimeMillis() <= backKeyPressedTime + 2000;
        // 2초가 지나지 않았을 경우
    }

    private void programShutdown() {
        activity.moveTaskToBack(true);
        activity.finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}