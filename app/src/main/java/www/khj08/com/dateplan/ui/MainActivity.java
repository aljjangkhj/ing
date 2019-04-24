package www.khj08.com.dateplan.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
//import android.graphics.Typeface;
import android.media.ExifInterface;
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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import www.khj08.com.dateplan.BaseActivity;
import www.khj08.com.dateplan.R;
import www.khj08.com.dateplan.common.log;
import www.khj08.com.dateplan.popup.MainSideMenuDialog;
import www.khj08.com.dateplan.popup.Popup;
import www.khj08.com.dateplan.utils.Util;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class MainActivity extends BaseActivity /*implements NavigationView.OnNavigationItemSelectedListener*/ {

    private final int GALLERY_CODE = 1112;

    public SideMenuBar menubar_sidemenu = null;

    private InterstitialAd interstitialAd;
    private RewardedVideoAd mRewardedVideoAd;
    private Bitmap myBitmap;

    private int mRefid,finalresult,finalResultTime,sqltime,savebackground,mainHour,mainMinute;
    private String img01,img02,mainImage,mRefResultMoney,mCurrentPhotoPath;

    private TextView resultmoney,resultTime,moenyset,timeset,manName,womanName,mainText,MainDdayText,nav_textView,firstname1,secondname1,firstname2,secondname2;
    private ImageButton imageButton01,imageButton02,imageButton_nav;
    private ImageView imageView01,imageView02,imageView_nav;

    private Uri photoURI,albumURI,mImageCaptureUri;
    private SQLiteDBManager mSQLiteDBManager;

    private String mainMoney = "0";
    private LinearLayout btn_main_menu,constraintlayout,write_diary_btn,list_diary_btn,d_day_btn,account_btn, setting_btn;
    private Button get_admob;

    private int imagebtn01 = 0;
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_CAMERA = 2;
    private static final int REQUEST_IMAGE_CROP = 3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_autoscreen(1);
        //권한체크
        checkPermission();

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        backPressCloseSystem = new BackPressCloseSystem(this);
        AdView mAdView = (AdView) findViewById(R.id.adView);
        //광고테스트
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        String today = Util.yyyyMMdd();

//        AdView mAdView2 = (AdView)findViewById(R.id.adView2);
//        AdRequest adRequest2 = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).build();
//        mAdView2.loadAd(adRequest2);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "등록되어있지 않습니다.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
*/
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/
        myMainImage();//사진이벤트
        TextClickEvent();//메인클릭이벤트
        mybackgroundcolor();//배경화면설정
        menubar_sidemenu = (SideMenuBar) findViewById(R.id.menubar_sidemenu);
        btn_main_menu = (LinearLayout)findViewById(R.id.btn_main_menu);
        write_diary_btn = (LinearLayout)findViewById(R.id.write_diary_btn);
        list_diary_btn = (LinearLayout)findViewById(R.id.list_diary_btn);
        d_day_btn = (LinearLayout)findViewById(R.id.d_day_btn);
        account_btn = (LinearLayout)findViewById(R.id.account_btn);
        setting_btn = (LinearLayout)findViewById(R.id.setting_btn);
        mainText = (TextView) this.findViewById(R.id.main_textview01);
        MainDdayText = (TextView) this.findViewById(R.id.main_dday_text);
        firstname1 = (TextView)findViewById(R.id.first_name_tv);
        firstname2 = (TextView)findViewById(R.id.first_name_tv2);
        secondname1 = (TextView)findViewById(R.id.second_name_tv);
        secondname2 = (TextView)findViewById(R.id.second_name_tv2);
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
        constraintlayout = (LinearLayout) this.findViewById(R.id.main_layout);
        get_admob = (Button) this.findViewById(R.id.get_admob);
        nav_textView = (TextView) findViewById(R.id.nav_header_layout_TextView);

        this.mainText.setText(MySharedPreferencesManager.getStartLoveDay(this));
//        this.MainDdayText.setText(MySharedPreferencesManager.getDdaySave(this));
        this.manName.setText(MySharedPreferencesManager.getPic01(this));
        this.womanName.setText(MySharedPreferencesManager.getPic02(this));
        this.nav_textView.setText(MySharedPreferencesManager.getMainEdit(MainActivity.this));

        firstname1.setText(MySharedPreferencesManager.getPic01(mContext));
        firstname2.setText(MySharedPreferencesManager.getPic01(mContext));
        secondname1.setText(MySharedPreferencesManager.getPic02(mContext));
        secondname2.setText(MySharedPreferencesManager.getPic02(mContext));
//        firstname1.setTypeface(typeface);
//        firstname2.setTypeface(typeface);
//        secondname1.setTypeface(typeface);
//        secondname2.setTypeface(typeface);
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

        if (a == tday) {
            this.MainDdayText.setText("D+" + (tt - tt2) + " ~잉ing");
        } else {
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
                mainHour = hour;
                mainMinute = minute;

                resultTime.setText(hour + "시간 " + minute + "분");
                finalresult += Integer.parseInt(mRefResultMoney);
                String numberstr = nf.format(finalresult);
                mainMoney = numberstr;
                resultmoney.setText(numberstr + "원");
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
                Toast.makeText(this, "총 비용은 " + mRefResultmoney, Toast.LENGTH_SHORT).show();
            }
        }*/

        get_admob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                setFullAd();
                menubar_sidemenu.hide();
                loadRewardedVideoAd();
            }
        });

        btn_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menubar_sidemenu.show();
            }
        });

        write_diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToActivity(new Intent(mContext,DatePickerActivity.class));
                menubar_sidemenu.hide();
            }
        });

        list_diary_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToActivity(new Intent(mContext, DateListView.class));
                menubar_sidemenu.hide();
            }
        });

        d_day_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToActivity(new Intent(mContext, D_Day_Activity.class));
                menubar_sidemenu.hide();
            }
        });

        account_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToActivity(new Intent(mContext, CalcResult.class));
                menubar_sidemenu.hide();
            }
        });

        setting_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MoveToActivity(new Intent(mContext, setActivity.class));
                menubar_sidemenu.hide();
            }
        });



        nav_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MainSideMenuDialog mainSideMenuDialog = new MainSideMenuDialog(mContext);
                mainSideMenuDialog.OK_Click = new MainSideMenuDialog.onClick() {
                    @Override
                    public void onClick() {
                        MySharedPreferencesManager.setMainEdit(mainSideMenuDialog.getDialogString(), MainActivity.this);
                        nav_textView.setText(mainSideMenuDialog.getDialogString());
                    }
                };
                mainSideMenuDialog.show();
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setIcon(R.mipmap.ic_launcher_plan);
//                builder.setTitle("딱! 한!줄!만~");
//                builder.setView(dialogView);
//                builder.setPositiveButton("명언 남기기", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        EditText firstName = (EditText) dialogView.findViewById(R.id.shortEdit);
//                        String shortName = firstName.getText().toString();
//                        MySharedPreferencesManager.setMainEdit(shortName, MainActivity.this);
//                        nav_textView.setText(shortName);
//
//                    }
//                });
//                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//                AlertDialog dialog = builder.create();
//                dialog.show();
            }
        });
    }

    private void checkPermission(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {

            } else {
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.GET_ACCOUNTS}, 1);
            }
        }
    }

    private void setFullAd(){
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getString(R.string.full_ad_key));
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                if (interstitialAd.isLoaded()) {
                    interstitialAd.show();
                }
            }
        });
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
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        //View myView = navigationView.inflateHeaderView(R.layout.nav_header_main);
//        View myView = navigationView.getHeaderView(0);
        imageButton_nav = (ImageButton) findViewById(R.id.nav_header_layout_imageButton);
        imageView_nav = (ImageView) findViewById(R.id.nav_header_layout_imageView);
        Bitmap bitmap = StringToBitMap(MySharedPreferencesManager.getMainPicture(this));
        imageView_nav.setImageBitmap(bitmap);

//        nav_btn = (Button) findViewById(R.id.nav_header_layout_Btn);

        imageButton_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imagebtn01 = 2;
                Popup popup = new Popup(mContext,MySharedPreferencesManager.getPic01(mContext)+","+MySharedPreferencesManager.getPic02(mContext)+"의 사진 선택하기","사랑하는 사람의 사진을 올려보세요!","취소","앨범선택");
                popup.OK_Click = new Popup.onClick() {
                    @Override
                    public void onClick() {
                        doTakeAlbumAction();
                    }
                };
                popup.show();
//                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        doTakePhotoAction();
//                    }
//                };
//                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        doTakeAlbumAction();
//                    }
//                };
//                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                };
//                new AlertDialog.Builder(MainActivity.this).setTitle("업로드할 사진을 선택합니다.")
//                        .setMessage("사랑하는 사람의 사진을 올려보세요!")
//                        .setIcon(R.mipmap.plancamera)
//                        .setPositiveButton("사진 촬영", cameraListener)
//                        .setNegativeButton("앨범 선택", albumListener)
//                        .setNeutralButton("취소", cancelListener).show();
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
        String imageFileName = "JPEG_" + timeStamp + ".jpg";
//        File storageDir = getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
//        File image = File.createTempFile(imageFileName,".jpg",storageDir);

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = "file:" + image.getAbsolutePath();

        File imageFile = null;
        File storageDir = new File(Environment.getExternalStorageDirectory() + "/Pictures","잉ing");

        if (!storageDir.exists()){
            storageDir.mkdirs();
        }

        imageFile = new File(storageDir, imageFileName);
        mCurrentPhotoPath = imageFile.getAbsolutePath();

        return imageFile;
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
                    log.vlog(2, "카메라에러: " + e);
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

//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
//        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        startActivityForResult(intent, PICK_FROM_ALBUM);

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
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
            case GALLERY_CODE:
            {
//                sendPicture(data.getData()); //갤러리에서 가져오기
                if (resultCode == Activity.RESULT_OK){
                    if (data.getData() != null){
                        try{
                            File albumFile = null;
                            albumFile = createImageFile();
                            photoURI = data.getData();
                            albumURI = Uri.fromFile(albumFile);
                            cropImage();
                        }catch (Exception e){
                            log.vlog(2,"KHJ gallery error: " + e.getMessage());
                        }
                    }
                }
                break;
            }
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
                try {
                    //Uri에서 이미지 이름을 얻어온다.
                    //String name_Str = getImageNameToUri(data.getData());
                    //이미지 데이터를 비트맵으로 받아온다.
                    //배치해놓은 ImageView에 set
                    //Toast.makeText(getBaseContext(), "name_Str : "+name_Str , Toast.LENGTH_SHORT).show();
                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                    if (imagebtn01 == 0) {
                        setImageBitmaps(image_bitmap, imageView01);
                        img01 = BitMapToString(image_bitmap);
                        MySharedPreferencesManager.setManPicture(img01, this);
                    }
                    if (imagebtn01 == 1) {
                        setImageBitmaps(image_bitmap, imageView02);
                        img02 = BitMapToString(image_bitmap);
                        MySharedPreferencesManager.setWomanPicture(img02, this);
                    }
                    if (imagebtn01 == 2) {
                        setImageBitmaps(image_bitmap, imageView_nav);
                        mainImage = BitMapToString(image_bitmap);
                        MySharedPreferencesManager.setMainPicture(mainImage, this);
                    }

                    // 임시 파일 삭제
                    File f = new File(mImageCaptureUri.getPath());
                    if (f.exists()) {
                        f.delete();
                    }

                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                break;
            }

            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(mImageCaptureUri, "image/*");
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
            case REQUEST_IMAGE_CROP:
                if (resultCode == Activity.RESULT_OK){
                    galleryAddPic();
                }
                break;
        }
    }

    private void galleryAddPic(){
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);

        File f = new File(mCurrentPhotoPath);

        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        sendBroadcast(mediaScanIntent);
        Uri uri = Uri.fromFile(new File(mCurrentPhotoPath));

        sendPicture(getImageContentUri(mContext,mCurrentPhotoPath));
    }

    private void cropImage(){
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        cropIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        cropIntent.setDataAndType(photoURI, "image/*");
        cropIntent.putExtra("aspectX",1);
        cropIntent.putExtra("aspectY",1);
        cropIntent.putExtra("scale",true);
        cropIntent.putExtra("output",albumURI);
        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }

    @Override
    public void onBackPressed() {

        if (menubar_sidemenu.opened)
        {
            menubar_sidemenu.hide();
        }
        else
        {
            ShowPopup_APPEND();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //2018.03.28
    @Override
    public void onResume(){
        super.onResume();
        this.mainText.setText(MySharedPreferencesManager.getStartLoveDay(this));
        this.manName.setText(MySharedPreferencesManager.getPic01(this));
        this.womanName.setText(MySharedPreferencesManager.getPic02(this));
        firstname1.setText(MySharedPreferencesManager.getPic01(mContext));
        firstname2.setText(MySharedPreferencesManager.getPic01(mContext));
        secondname1.setText(MySharedPreferencesManager.getPic02(mContext));
        secondname2.setText(MySharedPreferencesManager.getPic02(mContext));
        this.nav_textView.setText(MySharedPreferencesManager.getMainEdit(MainActivity.this));
//        Calendar calendar = Calendar.getInstance();
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH);
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        calendar.set(year, month + 1, day);
//        long tday = calendar.getTimeInMillis() / 86400000;
        long a = MySharedPreferencesManager.getToday(this);
        long b = MySharedPreferencesManager.getLoveDay(this);
//        if (a == tday) {
//            this.MainDdayText.setText("D+" + (a - b) + " ~잉ing");
//        } else {
//            if (b == 0) {
//                this.MainDdayText.setText("만나기 시작한 날을 설정해 주세요.");
//            } else {
//                this.MainDdayText.setText("D+" + (tday - b) + " ~잉ing");
//            }
//        }
        String today = Util.yyyyMMdd();
        long saveDateDday = Util.diffOfDate(MySharedPreferencesManager.getLoveStartDay(mContext),today) + 1;
        if (b == 0) {
            this.MainDdayText.setText("만나기 시작한 날을 설정해 주세요.");
        } else {
            this.MainDdayText.setText("D+" + saveDateDday + " ~잉ing");
        }

//        mSQLiteDBManager = SQLiteDBManager.getInstance(this);
        int ResultHour = 0;
        int ResultMoney = 0;
        int moneySet = 0;
        int timeSet = 0;
        int tiemHour = 0;
        int timeMinute = 0;

        Cursor c = null;
        SQLiteDatabase mDatabase = mContext.openOrCreateDatabase("Diary.db", Context.MODE_PRIVATE, null);
        c = mDatabase.rawQuery(" SELECT finalminute,resultmoney FROM Diary",  null);
        if (c != null) {
            while (c.moveToNext()) {
                ResultHour = c.getInt(0);
                ResultMoney = c.getInt(1);

                moneySet += ResultMoney;

                timeSet += ResultHour;

                tiemHour = timeSet / 60;
                timeMinute = timeSet % 60;

                log.vlog(2,"KHJ4 ResultHour: " + ResultHour);
                log.vlog(2,"KHJ4 ResultMoney: " + ResultMoney);
            }
            NumberFormat nf = NumberFormat.getNumberInstance();
            String numberstr = nf.format(moneySet);
            resultmoney.setText(numberstr + "원");
            resultTime.setText(tiemHour + "시간 " + timeMinute + "분");
            log.vlog(2, "KHJ4 ResultHour2: " + ResultHour);
            log.vlog(2, "KHJ4 timeMinute2: " + tiemHour + "시간 " + timeMinute + "분");
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

                        constraintlayout.setBackgroundDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.main_image));
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
                        firstname1.setTextColor(Color.WHITE);
                        firstname2.setTextColor(Color.WHITE);
                        secondname1.setTextColor(Color.WHITE);
                        secondname2.setTextColor(Color.WHITE);
//                        nav_textView.setTextColor(Color.BLACK);

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
//                        nav_textView.setTextColor(Color.BLACK);
                        firstname1.setTextColor(Color.BLACK);
                        firstname2.setTextColor(Color.BLACK);
                        secondname1.setTextColor(Color.BLACK);
                        secondname2.setTextColor(Color.BLACK);
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
                        firstname1.setTextColor(Color.WHITE);
                        firstname2.setTextColor(Color.WHITE);
                        secondname1.setTextColor(Color.WHITE);
                        secondname2.setTextColor(Color.WHITE);
//                        nav_textView.setTextColor(Color.BLACK);
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
                        firstname1.setTextColor(Color.BLACK);
                        firstname2.setTextColor(Color.BLACK);
                        secondname1.setTextColor(Color.BLACK);
                        secondname2.setTextColor(Color.BLACK);
//                        nav_textView.setTextColor(Color.BLACK);
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

    public void mybackgroundcolor(){
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        View myView = navigationView.getHeaderView(0);
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
        constraintlayout = (LinearLayout) this.findViewById(R.id.main_layout);
//        nav_textView = (TextView) myView.findViewById(R.id.nav_header_layout_TextView);
        firstname1 = (TextView)findViewById(R.id.first_name_tv);
        firstname2 = (TextView)findViewById(R.id.first_name_tv2);
        secondname1 = (TextView)findViewById(R.id.second_name_tv);
        secondname2 = (TextView)findViewById(R.id.second_name_tv2);

        savebackground = MySharedPreferencesManager.getSaveColor(this);
        if (savebackground == 0){

        }
        else{
            if(R.color.color2 == savebackground){
                constraintlayout.setBackgroundDrawable( ContextCompat.getDrawable(MainActivity.this, R.drawable.main_image));
                resultmoney.setTextColor(Color.WHITE);
                resultTime.setTextColor(Color.WHITE);
                moenyset.setTextColor(Color.WHITE);
                timeset.setTextColor(Color.WHITE);
                manName.setTextColor(Color.WHITE);
                womanName.setTextColor(Color.WHITE);
                mainText.setTextColor(Color.WHITE);
                MainDdayText.setTextColor(Color.WHITE);
                firstname1.setTextColor(Color.WHITE);
                firstname2.setTextColor(Color.WHITE);
                secondname1.setTextColor(Color.WHITE);
                secondname2.setTextColor(Color.WHITE);
//                nav_textView.setTextColor(Color.BLACK);
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
//                nav_textView.setTextColor(Color.BLACK);
                firstname1.setTextColor(Color.BLACK);
                firstname2.setTextColor(Color.BLACK);
                secondname1.setTextColor(Color.BLACK);
                secondname2.setTextColor(Color.BLACK);
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
                firstname1.setTextColor(Color.WHITE);
                firstname2.setTextColor(Color.WHITE);
                secondname1.setTextColor(Color.WHITE);
                secondname2.setTextColor(Color.WHITE);
//                nav_textView.setTextColor(Color.BLACK);
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
//                nav_textView.setTextColor(Color.BLACK);
                firstname1.setTextColor(Color.BLACK);
                firstname2.setTextColor(Color.BLACK);
                secondname1.setTextColor(Color.BLACK);
                secondname2.setTextColor(Color.BLACK);
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
                Popup popup = new Popup(mContext,MySharedPreferencesManager.getPic01(mContext)+"의 사진 선택하기","사랑하는 사람의 사진을 올려보세요!","취소","앨범선택");
                popup.OK_Click = new Popup.onClick() {
                    @Override
                    public void onClick() {
                        doTakeAlbumAction();
                    }
                };
                popup.show();
//                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        doTakePhotoAction();
//                    }
//                };
//                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        doTakeAlbumAction();
//                    }
//                };
//                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                };
//                new AlertDialog.Builder(MainActivity.this)
//                        .setIcon(R.mipmap.plancamera)
//                        .setTitle("업로드할 사진을 선택합니다.")
//                        .setMessage("사랑하는 사람의 사진을 올려보세요!")
//                        .setPositiveButton("사진 촬영", cameraListener)
//                        .setNegativeButton("앨범 선택", albumListener)
//                        .setNeutralButton("취소", cancelListener).show();
//
            }
        });
        imageButton02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagebtn01 = 1;
                Popup popup = new Popup(mContext,MySharedPreferencesManager.getPic02(mContext)+"의 사진 선택하기","사랑하는 사람의 사진을 올려보세요!","취소","앨범선택");
                popup.OK_Click = new Popup.onClick() {
                    @Override
                    public void onClick() {
                        doTakeAlbumAction();
                    }
                };
                popup.show();
//                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        doTakePhotoAction();
//                    }
//                };
//                DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        doTakeAlbumAction();
//                    }
//                };
//                DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                };
//                new AlertDialog.Builder(MainActivity.this)
//                        .setIcon(R.mipmap.plancamera)
//                        .setTitle("업로드할 사진을 선택합니다.")
//                        .setMessage("사랑하는 사람의 사진을 올려보세요!")
//                        .setPositiveButton("사진 촬영", cameraListener)
//                        .setNegativeButton("앨범 선택", albumListener)
//                        .setNeutralButton("취소", cancelListener).show();
            }
        });
    }
    private void sendPicture(Uri imgUri) {

        String imagePath = getRealPathFromURI(imgUri); // path 경로
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int exifOrientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        int exifDegree = exifOrientationToDegrees(exifOrientation);

        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);//경로를 통해 비트맵으로 전환
//        ivImage.setImageBitmap(rotate(bitmap, exifDegree));//이미지 뷰에 비트맵 넣기
        if (imagebtn01 == 0) {
            setImageBitmaps(bitmap, imageView01);
            img01 = BitMapToString(bitmap);
            MySharedPreferencesManager.setManPicture(img01, this);
        }else if (imagebtn01 == 1){
            setImageBitmaps(bitmap, imageView02);
            img02 = BitMapToString(bitmap);
            MySharedPreferencesManager.setWomanPicture(img02, this);
        }else if (imagebtn01 == 2){
            setImageBitmaps(bitmap, imageView_nav);
            mainImage = BitMapToString(bitmap);
            MySharedPreferencesManager.setMainPicture(mainImage, this);
        }

        File f = new File(imagePath);
        if (f.exists()) {
            f.delete();
        }
    }
    public static Uri getImageContentUri(Context context, String absPath) {

        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                , new String[] { MediaStore.Images.Media._ID }
                , MediaStore.Images.Media.DATA + "=? "
                , new String[] { absPath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            return Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI , Integer.toString(id));

        } else if (!absPath.isEmpty()) {
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, absPath);
            return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } else {
            return null;
        }
    }

//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_camera) {
//            //   Toast.makeText(this, "일기쓰기", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, DatePickerActivity.class);
//            MoveToActivity(intent);
//
//        } else if (id == R.id.nav_gallery) {
//            //  Toast.makeText(this, "D - DAY", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, D_Day_Activity.class);
//            MoveToActivity(intent);
//
//        } else if (id == R.id.nav_slideshow) {
//            //Toast.makeText(this, "일기 목록", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, DateListView.class);
//            MoveToActivity(intent);
//
//        } else if (id == R.id.nav_calc) {
//            //Toast.makeText(this, "가계부", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, CalcResult.class);
//            MoveToActivity(intent);
//
//        } /*else if (id == R.id.nav_massage) {
//            Toast.makeText(this, "대화하기", Toast.LENGTH_SHORT).show();
//
//        }*/ else if (id == R.id.nav_set) {
//            //Toast.makeText(this, "설정", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(MainActivity.this, setActivity.class);
//            MoveToActivity(intent);
//        }
//
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }

    private String getRealPathFromURI(Uri contentUri) {
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if(cursor.moveToFirst()){
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }

        return cursor.getString(column_index);
    }
    private int exifOrientationToDegrees(int exifOrientation) {
        if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_90) {
            return 90;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_180) {
            return 180;
        } else if (exifOrientation == ExifInterface.ORIENTATION_ROTATE_270) {
            return 270;
        }
        return 0;
    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(mContext);
        mRewardedVideoAd.setRewardedVideoAdListener(new RewardedVideoAdListener() {
            @Override
            public void onRewardedVideoAdLoaded() {
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }
            }

            @Override
            public void onRewardedVideoAdOpened() {

            }

            @Override
            public void onRewardedVideoStarted() {

            }

            @Override
            public void onRewardedVideoAdClosed() {

            }

            @Override
            public void onRewarded(RewardItem rewardItem) {

            }

            @Override
            public void onRewardedVideoAdLeftApplication() {

            }

            @Override
            public void onRewardedVideoAdFailedToLoad(int i) {

            }
        });
        mRewardedVideoAd.loadAd(getString(R.string.reward_ad_key), new AdRequest.Builder().build());
    }
}