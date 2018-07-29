package in.dux.p016ccv2.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by root on 1/19/18.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "key notes";
    //PreferenceManager.getDefaultSharedPreferences()

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String CURENTLY_ENROLLED_COURSE = "CurrentlyEnrolledCourse";
    private static final String ENROLLED_COURSE = "EnrolledCourse";
    private static final String ENROLLED_YEAR = "EnrolledYear";
    private static final String ADMOB_ID = "id";
    private static final String ADMOB_ID_DUX = "dux_id";

    public PrefManager(Context context) {
        this._context = context;
        pref = PreferenceManager.getDefaultSharedPreferences(_context);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public SharedPreferences getPref() {
        return pref;
    }

    public void setCurentlyEnrolledCourse(String curentlyEnrolledCourse) {
        editor.putString(CURENTLY_ENROLLED_COURSE,curentlyEnrolledCourse);
        editor.commit();
    }

    public void setEnrolledCourse(String enrolledCourse) {
        editor.putString(ENROLLED_COURSE,enrolledCourse);
        editor.commit();
    }

    public void setEnrolledYear(String enrolledYear) {
        editor.putString(ENROLLED_YEAR,enrolledYear);
        editor.commit();
    }

    public String getCurentlyEnrolledCourse() {
        return pref.getString(CURENTLY_ENROLLED_COURSE,"bsc1");
    }

    public String getEnrolledCourse() {
        return pref.getString(ENROLLED_COURSE,"bsc");
    }

    public String getEnrolledYear() {
        return pref.getString(ENROLLED_YEAR,"1st");
    }

    public void setAdmobId(String id) {
        editor.putString(ADMOB_ID,id);
        editor.commit();
    }
    public void setAdmobIdDux(String id) {
        editor.putString(ADMOB_ID_DUX,id);
        editor.commit();
    }

    public String getAdmobId() {
        return pref.getString(ADMOB_ID,"id");
    }
    public String getAdmobIdDux() {
        return pref.getString(ADMOB_ID_DUX,"id");
    }
}
