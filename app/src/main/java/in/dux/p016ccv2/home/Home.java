package in.dux.p016ccv2.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import in.dux.p016ccv2.Dashboard;
import in.dux.p016ccv2.Feedback;
import in.dux.p016ccv2.R;
import in.dux.p016ccv2.SelectCourse;
import in.dux.p016ccv2.ShareNotes;
import in.dux.p016ccv2.utils.MyFragment;
import ir.apend.slider.model.Slide;
import ir.apend.slider.ui.Slider;

import static in.dux.p016ccv2.utils.CheckNetworkConnection.isConnectionAvailable;


public class Home extends MyFragment {

    private RecyclerView recyclerView1;
    private RecyclerView.Adapter adapter;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Slider slider;
    public static List<Slide> slideList = new ArrayList<>();

    private ScrollView scrollView;

    private ImageView changeCourse,uploadNotes,feedback;
    private static Home instance = new Home();
    private ProgressDialog progressDialog;
    private boolean mHasInflated = false;

    public static Home getInstance() {
        return instance;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        if(!isConnectionAvailable(getContext())) {
            Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_LONG).show();
        }

        loadAdvertisement();
        scrollView = view.findViewById(R.id.homeScrollView);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        new MyAsyncTask(view,getActivity(),getContext()).execute("");

    }

    @SuppressLint("StaticFieldLeak")
    private class MyAsyncTask extends AsyncTask<String ,String ,String > {
        private View view;
        private Activity activity;
        private Context context;
        private Home home = new Home();

        public MyAsyncTask(View mView,Activity mActivity,Context mContext) {
            view = mView;
            activity = mActivity;
            context = mContext;
        }

        @Override
        protected String doInBackground(String... strings) {

            slider = view.findViewById(R.id.slider);
            slider.addSlides(slideList);

            recyclerView1 = view.findViewById(R.id.home_news_recyclerView);
            recyclerView1.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
            adapter = new NewsAndAlertMyAdapter(NewsAndAlert_ViewAll.list, new NewsAndAlertMyAdapter.RecyclerViewClickListener() {
                @Override
                public void onClick(View view, int adapterPosition) {
                    if(!isConnectionAvailable((getContext()))) {
                        Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (NewsAndAlert_ViewAll.list.get(adapterPosition).getNotificationType().equalsIgnoreCase("pdf")) {

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(NewsAndAlert_ViewAll.list.get(adapterPosition).getNotificationUrl()), "application/pdf");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            Log.d("l", "l");
                        }

                        showMyAdd(intent);

                    } else if (NewsAndAlert_ViewAll.list.get(adapterPosition).getNotificationType().equalsIgnoreCase("www")) {

                        String url = NewsAndAlert_ViewAll.list.get(adapterPosition).getNotificationUrl();
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        i.setPackage("com.android.chrome");
                        try {
                            startActivity(i);
                        } catch (ActivityNotFoundException e) {
                            // Chrome is probably not installed
                            // Try with the default browser
                            i.setPackage(null);
                            startActivity(i);
                        }
                        showMyAdd(i);
                    }
                }
            }, "home");
            recyclerView1.setAdapter(adapter);
            recyclerView1.setItemAnimator(new DefaultItemAnimator());

            ImageView ccsuWebsite = view.findViewById(R.id.ccsuWebsite);
            ccsuWebsite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isConnectionAvailable(context)) {
                        Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://www.ccsuniversity.ac.in/default.htm"));
                    startActivity(i);

                    showMyAdd(i);
                }
            });

            ImageView ccsuForm = view.findViewById(R.id.ccsuForm);
            ccsuForm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isConnectionAvailable(context)) {
                        Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://ccsuweb.in/"));
                    startActivity(i);

                    showMyAdd(i);
                }
            });

            ImageView ccsuResult = view.findViewById(R.id.ccsuResult);
            ccsuResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isConnectionAvailable(context)) {
                        Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://192.163.211.186/~ccsuresu/"));
                    startActivity(i);
                    showMyAdd(i);
                }
            });

            ImageView admitCard = view.findViewById(R.id.admitCard);
            admitCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isConnectionAvailable(context)) {
                        Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://exams.ccsuresults.com/"));
                    startActivity(i);
                    showMyAdd(i);
                }
            });

            ImageView sarkariResult = view.findViewById(R.id.sarkariResult);
            sarkariResult.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!isConnectionAvailable(context)) {
                        Toast.makeText(getContext(), "You are currently offline, Please connect to internet", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("http://www.sarkariresult.com/"));
                    startActivity(i);
                    showMyAdd(i);
                }
            });

            ImageView more = view.findViewById(R.id.moreImage);
            more.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(activity,MoreWebsites.class));
                }
            });

            CardView newsAndAlertViewAllButton = view.findViewById(R.id.viewAll);
            newsAndAlertViewAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(activity,NewsAndAlert_ViewAll.class));
                }
            });

            uploadNotes = view.findViewById(R.id.uploadnotesImageView);
            feedback = view.findViewById(R.id.feedbackImageVIew);
            changeCourse = view.findViewById(R.id.changeCourseImageView);
            uploadNotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(activity, ShareNotes.class));
                }
            });
            feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(activity, Feedback.class));
                }
            });
            changeCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(activity, SelectCourse.class));
                    activity.finish();
                }
            });


            return null;
        }
    }
}
