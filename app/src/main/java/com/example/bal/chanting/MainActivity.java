package com.example.bal.chanting;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.PowerManager;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;

public class MainActivity extends AppCompatActivity {
    BackgroundMusicService bms = new BackgroundMusicService();
    BackgroundMusicService StartMusic = new BackgroundMusicService();
    BackgroundMusicService Thunder = new BackgroundMusicService();
    Context ctx = getBaseContext();
    ImageView Greyish;
    Button FeedbackButton;
    float Galpha = 0.0f;
    boolean shownFeedbackMenu = false;
    int page = 0;
    ConstraintLayout FeedbackLayout;
    ImageView Hview, Cview, Iview, Sview, IHview, ICview, IIview, ISview;
    String FirstLine = "", SecondLine = "";
    Bitmap OAdvImg;
    String Ad2;
    public void SetBackBtnFunc()
    {
        pl.droidsonroids.gif.GifTextView Hback = findViewById(R.id.backbtngif);
        Hback.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                setContentView(R.layout.activity_main);
                FeedbackLayout.setX(screenwidth);
                FeedbackLayout.setVisibility(VISIBLE);
                StartLayout();
                page = 0;
                bms.Stop();
                //Background Start Page Music
                StartMusic = new BackgroundMusicService(MainActivity.this, R.raw.peace);
                StartMusic.Play();
                StartMusic.SetLooping(true);
                Adno = 1; Flineno = 0; Slineno = 1;
            }
        });
    }
    //New Version Ad
    private Runnable NewVerAdvertisement = new Runnable() {
        public void run() {
            //Check Internet
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if ((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL url = new URL("https://bitnbytecomputers.co.in/ChantingAdv/Version.ver");
                            HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
                            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                            final String line = br.readLine();
                            runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   if (Float.parseFloat(line) > Float.parseFloat(getVersionInfo())) {
                                       if(page==0) {
                                           ConstraintLayout NewVLayout = findViewById(R.id.NewVerLayout);
                                           NewVLayout.setVisibility(VISIBLE);
                                       }
                                   }
                               }
                            });
                        } catch (Exception ex) {
                            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
            }
        }
    };
    //get the current version number and name
    private String getVersionInfo() {
        String versionName = "";
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (Exception e) {

        }
        return versionName;
    }
    Handler custHandle = new Handler();
    ImageView ChantingImg;
    public void StartLayout() {
        //New Version Advertisement
        custHandle.postDelayed(NewVerAdvertisement, 1000);
        Button NewVCloseBtn = findViewById(R.id.NewVerClose);
        NewVCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConstraintLayout NewVLayout = findViewById(R.id.NewVerLayout);
                NewVLayout.setVisibility(INVISIBLE);
            }
        });
        //Texts
        Hview = findViewById(R.id.hinduism);
        Cview = findViewById(R.id.christianity);
        Iview = findViewById(R.id.islamic);
        Sview = findViewById(R.id.sikhism);
        FeedbackLayout = findViewById(R.id.FeedbackLayout);
        FeedbackButton = findViewById(R.id.Feedbackbtn);
        page = 0;
        Adno = 1;
        Flineno = 0; Slineno = 1;
        AdvertisementHandler.postDelayed(OAdvertisement, 0);
        //Clicking Text
        Hview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    setContentView(R.layout.chantingscreen);
                    SetBackBtnFunc();
                    page=1;
                    Adno = 1;
                    Flineno = 0; Slineno = 1;
                    StartMusic.Stop();
                    bms = new BackgroundMusicService(MainActivity.this, R.raw.hinduism);
                    bms.Play();
                    bms.SetLooping(true);
                    titling1X = 0;
                    AdvertisementHandler.removeCallbacks(OAdvertisement);
                    AdvertisementHandler.postDelayed(OAdvertisement, 10);
                    customHandler.postDelayed(Titling, 50);
                    ChantingImg = findViewById(R.id.ChantImg);
                    ChantingImg.setBackgroundResource(R.drawable.om3d);
                }
                catch(Exception ex)
                {
                    Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        Cview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=2;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.christianity);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 100);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.getLayoutParams().width = 500;
                ChantingImg.requestLayout();
                ChantingImg.setBackgroundResource(R.drawable.jesus_x3d);
            }
        });
        Iview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=3;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.islamic);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 100);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.setBackgroundResource(R.drawable.islam3d);
            }
        });
        Sview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=4;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.sikhism);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 100);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.setBackgroundResource(R.drawable.ekonkar3d);
            }
        });
        //Images
        IHview = findViewById(R.id.ihinduism);
        ICview = findViewById(R.id.ichristianity);
        IIview = findViewById(R.id.iislamic);
        ISview = findViewById(R.id.isikhism);
        page = 0;
        //Clicking Images
        IHview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=1;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.hinduism);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 50);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.setBackgroundResource(R.drawable.om3d);
            }
        });
        ICview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=2;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.christianity);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 100);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.setBackgroundResource(R.drawable.jesus_x3d);
            }
        });
        IIview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=3;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.islamic);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 100);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.setBackgroundResource(R.drawable.islam3d);
            }
        });
        ISview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.chantingscreen);
                SetBackBtnFunc();
                Adno = 1;
                Flineno = 0; Slineno = 1;
                page=4;
                StartMusic.Stop();
                bms = new BackgroundMusicService(MainActivity.this, R.raw.sikhism);
                bms.Play();
                bms.SetLooping(true);
                titling1X = 0;
                AdvertisementHandler.removeCallbacks(OAdvertisement);
                AdvertisementHandler.postDelayed(OAdvertisement, 10);
                customHandler.postDelayed(Titling, 100);
                ChantingImg = findViewById(R.id.ChantImg);
                ChantingImg.setBackgroundResource(R.drawable.ekonkar3d);
            }
        });
        final EditText FFNAMEText = (EditText) findViewById(R.id.Ffname);
        final EditText FLNAMEText = (EditText) findViewById(R.id.Flname);
        final EditText FEMAILText = (EditText) findViewById(R.id.Femail);
        final EditText FCOMMENTText = (EditText) findViewById(R.id.Fcomment);
        FeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting Feedback TextView Keyboard settings
                Greyish = findViewById(R.id.GreyBack);
                FFNAMEText.setText("");
                FLNAMEText.setText("");
                FEMAILText.setText("");
                FCOMMENTText.setText("");
                FFNAMEText.requestFocus();
                //Disabling Back
                Hview.setEnabled(false);
                Cview.setEnabled(false);
                Iview.setEnabled(false);
                Sview.setEnabled(false);

                IHview.setEnabled(false);
                ICview.setEnabled(false);
                IIview.setEnabled(false);
                ISview.setEnabled(false);
                FeedbackButton.setEnabled(false);
                Galpha = 0.0f;
                Greyish.setX(0);
                Greyish.setY(0);
                FeedbackX = screenwidth;
                FeedbackLayout.setX(FeedbackX);
                FeedbackLayout.setVisibility(VISIBLE);
                customHandler.postDelayed(FeedbackBox, 1);
                Button Sendbtn = (Button) findViewById(R.id.Fsendbtn);
                Sendbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sendEmail();
                    }
                });
            }
        });
        Button FeedbackCloseButton = (Button) findViewById(R.id.FeedClose);
        FeedbackCloseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View vA = getCurrentFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                assert imm != null && vA != null;
                imm.hideSoftInputFromWindow(vA.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                customHandler.postDelayed(FeedbackBox, 1);
            }
        });
    }

    int FeedbackX = 0;
    private Runnable FeedbackBox = new Runnable() {
        public void run() {
            if (shownFeedbackMenu == false) {
                if (FeedbackX >= 20) {
                    FeedbackX -= 10;
                    if (Galpha < 0.8)
                        Galpha += 0.02;
                    Greyish.setAlpha(Galpha);
                    FeedbackLayout.setX(FeedbackX);
                    customHandler.postDelayed(FeedbackBox, 1);
                } else {
                    FeedbackLayout.setX(0);
                    shownFeedbackMenu = true;
                }
            } else {
                if (FeedbackX <= screenwidth - 20) {
                    FeedbackX += 10;
                    if (Galpha > 0.0)
                        Galpha -= 0.02;
                    Greyish.setAlpha(Galpha);
                    FeedbackLayout.setX(FeedbackX);
                    customHandler.postDelayed(FeedbackBox, 1);
                } else {
                    FeedbackLayout.setX(screenwidth);
                    shownFeedbackMenu = false;
                    //Disabling Back
                    Hview.setEnabled(true);
                    Cview.setEnabled(true);
                    Iview.setEnabled(true);
                    Sview.setEnabled(true);
                    IHview.setEnabled(true);
                    ICview.setEnabled(true);
                    IIview.setEnabled(true);
                    ISview.setEnabled(true);
                    FeedbackButton.setEnabled(true);
                }
            }
        }
    };
    Handler customHandler = new Handler();
    Handler AdvertisementHandler = new Handler();
    ImageView SPHinduism = null, SPChristianity = null, SPIslamic = null, SPSikhism = null;
    ImageView SPC1 = null, SPC2 = null, SPC3 = null; //Coronavirus Pics
    TextView CoronaTV = null;  //Coronavirus Text
    TextView Info_TextView = null, GodIsOneView = null;
    ImageView Lightning = null, Lightning2 = null, StartPageBack = null;
    float Hy = 100, HWidth = 1, HHeight = 1;
    float Cy = 1, CWidth = 1, CHeight = 1;
    float Iy = 1, IWidth = 1, IHeight = 1;
    float Sy = 1, SWidth = 1, SHeight = 1;
    int Lightimg = 1, lighttimes = 0;
    TextView Titling1;
    float titling1X = 0;
    //Start Page Items
    ImageView Power1, Power2;
    //Hinduism Titling Layout components
    ConstraintSet Hset = null;
    ConstraintLayout HconstraintLayout = null;
    int info_TextY = 0, screenheight = 0, textviewtouched = 0, screenwidth = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        screenheight = height;
        int width = displayMetrics.widthPixels;
        screenwidth = width;
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.info_page);
        Info_TextView = findViewById(R.id.Info_text);
        GodIsOneView = findViewById(R.id.GodIsOne);
        Info_TextView.setY(height);
        Info_TextView.setX(20);
        GodIsOneView.setY(height);
        GodIsOneView.setX(20);
        GodIsOneView.setX(0);
        GodIsOneView.setWidth(screenwidth);
        info_TextY = height;
        customHandler.postDelayed(InfoMovingUp, 10);
        ConstraintLayout InfoClayout = (ConstraintLayout) findViewById(R.id.InfoConsLayout);
        InfoClayout.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == android.view.MotionEvent.ACTION_DOWN) {
                    textviewtouched = 1;
                } else if (event.getAction() == android.view.MotionEvent.ACTION_UP) {
                    textviewtouched = 0;
                    customHandler.postDelayed(InfoMovingUp, 10);
                }
                return true;
            }
        });
    }

    private void StartPageShowUpSetup() {
        SPC1 = findViewById(R.id.C1);
        SPC2 = findViewById(R.id.C2);
        SPC3 = findViewById(R.id.C3);
        SPHinduism = findViewById(R.id.hinduismpic);
        CoronaTV = findViewById(R.id.corons);
        Lightning = findViewById(R.id.lightning);
        Lightning.setImageResource(R.drawable.lightl);
        Lightning2 = findViewById(R.id.lightning2);
        Lightning2.setImageResource(R.drawable.lightl);
        StartPageBack = findViewById(R.id.back);
        Power1 = findViewById(R.id.power1);
        Power2 = findViewById(R.id.power2);
        //Titling Views
        Titling1 = findViewById(R.id.titling);
        //Background Start Page Music
        StartMusic = new BackgroundMusicService(MainActivity.this, R.raw.peace);
        StartMusic.Play();
        StartMusic.SetLooping(true);
        customHandler.postDelayed(ScreenOn, 240000);
        startService(new Intent(getBaseContext(), BackgroundMusicService.class));
        customHandler.postDelayed(HPicSize, 60);
    }

    private Runnable InfoMovingUp = new Runnable() {
        public void run() {
            if (info_TextY >= -Info_TextView.getHeight()) {
                if (textviewtouched == 0) {
                    info_TextY -= 8;
                    Info_TextView.setY(info_TextY);
                    GodIsOneView.setY(info_TextY);
                    customHandler.postDelayed(InfoMovingUp, 10);
                }
            } else {
                setContentView(R.layout.start_page);
                StartPageShowUpSetup();
            }
        }
    };
    //Online Advertisement
    int Adno = 1, Flineno = 0, Slineno = 1;
    int lineno = 0;
    private Runnable OAdvertisement = new Runnable() {
        public void run() {
            //Check Internet
            ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            if((connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED))
                    {
                new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try
                        {
                            URL url = new URL("https://bitnbytecomputers.co.in/ChantingAdv/Text.txt");
                            HttpsURLConnection uc = (HttpsURLConnection) url.openConnection();
                            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                            String line="";
                            while ((line = br.readLine()) != null)
                            {
                                if(lineno==Flineno)
                                    FirstLine = line;
                                else if(lineno==Slineno)
                                    SecondLine = line;
                                lineno++;
                            }
                            //Onine Image
                            URL urlConnection = new URL("https://bitnbytecomputers.co.in/ChantingAdv/Ad"+Adno+".png");
                            HttpURLConnection connection = (HttpURLConnection) urlConnection
                                    .openConnection();
                            connection.setConnectTimeout(6000);
                            connection.setDoInput(true);
                            connection.connect();
                            InputStream input = connection.getInputStream();
                            OAdvImg = BitmapFactory.decodeStream(input);

                            AdSettings(lineno, Flineno, Slineno, Adno);
                            runOnUiThread(new Runnable(){
                                public void run() {
                                    if(page==0) {
                                        TextView OAdvHeadView = findViewById(R.id.OAdvHead);
                                        TextView OAdvTextView = findViewById(R.id.OAdvMainText);
                                        OAdvHeadView.setText(FirstLine);
                                        OAdvTextView.setText(SecondLine);
                                        ImageView AdView = findViewById(R.id.AdvImage);
                                        AdView.setImageBitmap(OAdvImg);
                                        ConstraintLayout conslayout = findViewById(R.id.OAdvLayout);
                                        conslayout.setVisibility(VISIBLE);
                                    }
                                    else
                                    {
                                        TextView OAdvHeadView = findViewById(R.id.OAdvHeadI);
                                        TextView OAdvTextView = findViewById(R.id.OAdvDetailI);
                                        OAdvHeadView.setText(FirstLine);
                                        OAdvTextView.setText(SecondLine);
                                        ImageView AdView = findViewById(R.id.OAdvImgI);
                                        AdView.setImageBitmap(OAdvImg);
                                        ConstraintLayout conslayout = findViewById(R.id.OAdvLayoutI);
                                        conslayout.setVisibility(VISIBLE);
                                    }
                                }
                            });

                        } catch (Exception e)
                        {
                            Toast.makeText(getApplicationContext(), "Unexpected Error", Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
                AdvertisementHandler.postDelayed(OAdvertisement, 10000);
            }
        }
    };
    private void AdSettings(int NumLine, int FNum, int SNum, int Adnum)
    {
        Flineno = FNum;
        Slineno = SNum;
        Adno = Adnum;
        if(Adno<((NumLine)/2)) {
            Flineno += 2;
            Slineno += 2;
            Adno++;
        }
        else if(Adno==((NumLine)/2))
        {
            Flineno = 0;
            Slineno = 1;
            Adno=1;
        }
        lineno = 0;
    }
    //Screen Turn on
    private Runnable ScreenOn = new Runnable() {
        public void run() {
            PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(getApplicationContext().POWER_SERVICE);
            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK |
                    PowerManager.ACQUIRE_CAUSES_WAKEUP |
                    PowerManager.ON_AFTER_RELEASE, "appname::WakeLock");

            //acquire will turn on the display
            wakeLock.acquire();
            //release will release the lock from CPU, in case of that, screen will go back to sleep mode in defined time bt device settings
            wakeLock.release();
            customHandler.postDelayed(ScreenOn, 240000);
        }
    };
    private Runnable Titling = new Runnable() {
        @Override
        public void run() {
            if (page == 1 || page==2 || page==3 || page==4) {
                titling1X++;
                Hset = new ConstraintSet();
                HconstraintLayout = findViewById(R.id.CSLayout);
                Hset.clone(HconstraintLayout);
                Hset.setHorizontalBias(R.id.titling, titling1X / 100);
                Hset.applyTo(HconstraintLayout);
                if (titling1X == 125)
                    titling1X = 0;
                customHandler.postDelayed(Titling, 200);
            }
        }
    };
    //Lightning Image Changing
    private Runnable changeImage = new Runnable() {
        @Override
        public void run() {
            if (Lightimg == 1) {
                Lightning.setImageResource(R.drawable.light);
                Lightning2.setImageResource(R.drawable.light);
                StartPageBack.setColorFilter(setBrightness(30));
                Thunder = new BackgroundMusicService(MainActivity.this, R.raw.thunder);
                Thunder.Play();
                Lightimg = 2;
            } else if (Lightimg == 2) {
                Lightning.setImageResource(R.drawable.lightl);
                Lightning2.setImageResource(R.drawable.lightl);
                StartPageBack.setColorFilter(setBrightness(0));
                Lightimg = 1;
            }
            lighttimes++;
            if (lighttimes < 4)
                customHandler.postDelayed(changeImage, 1000);
            else {
                customHandler.postDelayed(WhiteLight, 100);
                Thunder = new BackgroundMusicService(MainActivity.this, R.raw.thunder);
                Thunder.Play();
            }
        }
    };
    int brightness = 10;
    private Runnable WhiteLight = new Runnable() {
        public void run() {
            //Changing brightness of all
            brightness += 10;
            SPC1.setAlpha(0.0f);
            SPC2.setAlpha(0.0f);
            SPC3.setAlpha(0.0f);
            SPC1.setImageAlpha(0);
            SPC2.setImageAlpha(0);
            SPC3.setImageAlpha(0);
            CoronaTV.setAlpha(0.0f);
            Lightning.setColorFilter(setBrightness(brightness));
            Lightning2.setColorFilter(setBrightness(brightness));
            Power1.setColorFilter(setBrightness(brightness));
            Power2.setColorFilter(setBrightness(brightness));
            SPHinduism.setColorFilter(setBrightness(brightness));
            SPChristianity.setColorFilter(setBrightness(brightness));
            SPIslamic.setColorFilter(setBrightness(brightness));
            SPSikhism.setColorFilter(setBrightness(brightness));
            StartPageBack.setColorFilter(setBrightness(brightness));
            if (brightness <= 250)
                customHandler.postDelayed(WhiteLight, 100);
            else {
                setContentView(R.layout.activity_main);
                StartLayout();
            }
        }
    };
    //Hinduism Pic
    private Runnable HPicSize = new Runnable() {
        public void run() {
            Hy -= 1;
            HWidth -= 0.01;
            HHeight -= 0.01;
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout constraintLayout = findViewById(R.id.layout);
            set.clone(constraintLayout);
            set.setVerticalBias(R.id.hinduismpic, Hy / 100);
            set.setScaleX(R.id.hinduismpic, HWidth);
            set.setScaleY(R.id.hinduismpic, HHeight);
            set.applyTo(constraintLayout);
            if (Hy == 85) {
                SPChristianity = findViewById(R.id.christianitypic);
                if (SPChristianity.getVisibility() == INVISIBLE) {
                    SPChristianity.setVisibility(VISIBLE);
                    customHandler.postDelayed(CPicSize, 60);
                }
            } else if (Hy == 65) {
                SPIslamic = findViewById(R.id.islamicpic);
                if (SPIslamic.getVisibility() == INVISIBLE) {
                    SPIslamic.setVisibility(VISIBLE);
                    customHandler.postDelayed(IPicSize, 60);
                }
            } else if (Hy == 45) {
                SPSikhism = findViewById(R.id.sikhismpic);
                if (SPSikhism.getVisibility() == INVISIBLE) {
                    SPSikhism.setVisibility(VISIBLE);
                    customHandler.postDelayed(SPicSize, 60);
                }
            }
            if (Hy > 20) {
                customHandler.postDelayed(HPicSize, 60);
            } else {
                SPC1.setAlpha(0.85f);
                SPC1.setImageAlpha(85);
                SPC2.setAlpha(0.85f);
                SPC2.setImageAlpha(85);
                SPC3.setAlpha(0.85f);
                SPC3.setImageAlpha(85);
                CoronaTV.setAlpha(0.75f);
            }
        }
    };
    //Christianity Pic
    private Runnable CPicSize = new Runnable() {
        public void run() {
            Cy -= 0.01;
            CWidth -= 0.01;
            CHeight -= 0.01;
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout constraintLayout = findViewById(R.id.layout);
            set.clone(constraintLayout);
            set.setVerticalBias(R.id.christianitypic, Cy);
            set.setScaleX(R.id.christianitypic, CWidth);
            set.setScaleY(R.id.christianitypic, CHeight);
            set.applyTo(constraintLayout);
            if (Cy > 0.2) {
                customHandler.postDelayed(CPicSize, 60);
            } else {
                SPC1.setAlpha(0.70f);
                SPC1.setImageAlpha(70);
                SPC2.setAlpha(0.70f);
                SPC2.setImageAlpha(70);
                SPC3.setAlpha(0.70f);
                SPC3.setImageAlpha(70);
                CoronaTV.setAlpha(0.50f);
            }
        }
    };
    //Islamic Pic
    private Runnable IPicSize = new Runnable() {
        public void run() {
            Iy -= 0.01;
            IWidth -= 0.01;
            IHeight -= 0.01;
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout constraintLayout = findViewById(R.id.layout);
            set.clone(constraintLayout);
            set.setVerticalBias(R.id.islamicpic, Iy);
            set.setScaleX(R.id.islamicpic, IWidth);
            set.setScaleY(R.id.islamicpic, IHeight);
            set.applyTo(constraintLayout);
            if (Iy > 0.2) {
                customHandler.postDelayed(IPicSize, 60);
            } else {
                SPC1.setAlpha(0.55f);
                SPC1.setImageAlpha(55);
                SPC2.setAlpha(0.55f);
                SPC2.setImageAlpha(55);
                SPC3.setAlpha(0.55f);
                SPC3.setImageAlpha(55);
                CoronaTV.setAlpha(0.25f);
            }
        }
    };
    //Sikhism Pic
    private Runnable SPicSize = new Runnable() {
        public void run() {
            Sy -= 0.01;
            SWidth -= 0.01;
            SHeight -= 0.01;
            ConstraintSet set = new ConstraintSet();
            ConstraintLayout constraintLayout = findViewById(R.id.layout);
            set.clone(constraintLayout);
            set.setVerticalBias(R.id.sikhismpic, Sy);
            set.setScaleX(R.id.sikhismpic, SWidth);
            set.setScaleY(R.id.sikhismpic, SHeight);
            set.applyTo(constraintLayout);
            if (Sy > 0.2) {
                customHandler.postDelayed(SPicSize, 60);
            } else {
                SPC1.setAlpha(0.40f);
                SPC1.setImageAlpha(40);
                SPC2.setAlpha(0.40f);
                SPC2.setImageAlpha(40);
                SPC3.setAlpha(0.40f);
                SPC3.setImageAlpha(40);
                CoronaTV.setAlpha(0.10f);
                Lightning.setVisibility(VISIBLE);
                Lightning2.setVisibility(VISIBLE);
                customHandler.postDelayed(changeImage, 1000);
            }
        }
    };

    public boolean onKeyDown(int keycode, KeyEvent keyevent) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            if (page >= 1) {
                setContentView(R.layout.activity_main);
                StartLayout();
                page = 0;
                bms.Stop();
                //Background Start Page Music
                StartMusic = new BackgroundMusicService(MainActivity.this, R.raw.peace);
                StartMusic.Play();
                StartMusic.SetLooping(true);
            } else if (page == 0)
                System.exit(0);
            return true;
        }
        return super.onKeyDown(keycode, keyevent);
    }

    protected void onUserLeaveHint() {
        bms.Stop();
        StartMusic.Stop();
        System.exit(0);
        super.onUserLeaveHint();
    }

    public static ColorMatrixColorFilter setBrightness(int fb) {
        ColorMatrix cmB = new ColorMatrix();
        cmB.set(new float[]{
                1, 0, 0, 0, fb,
                0, 1, 0, 0, fb,
                0, 0, 1, 0, fb,
                0, 0, 0, 1, 0});

        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(cmB);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(colorMatrix);
        return f;
    }

    public boolean isOnline(Context con) {
        ConnectivityManager cm =
                (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    public void sendEmail() {
        EditText FeedEmail = findViewById(R.id.Femail);
        EditText FeedFname = findViewById(R.id.Ffname);
        EditText FeedLname = findViewById(R.id.Flname);
        EditText FeedComment = findViewById(R.id.Fcomment);
        String emailFeed = FeedEmail.getText().toString();
        String firstnameFeed = FeedFname.getText().toString();
        String lastnameFeed = FeedLname.getText().toString();
        String commentFeed = FeedComment.getText().toString();

        String Subject = "(Chanting)Feedback from " + emailFeed;
        String Body = "Name:-" + firstnameFeed + " " + lastnameFeed + "\nEmail:-" + emailFeed + "\nComment:-" + commentFeed;
        JavaMailAPI mail = new JavaMailAPI(this, "nbbal2003@gmail.com", Subject, Body);
        if (isOnline(getApplicationContext())) {
            mail.execute();
        }
    }
}
