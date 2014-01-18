package fi.aalto.powerconsumptor;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

public class UIUtils {
	
	private static final Handler uiHandler = new Handler(Looper.getMainLooper());
	
	
	public static void runOnUiThread(Runnable r) {
		uiHandler.post(r);
	}
	
	public static AlertDialog showOkDialog(Activity context, String title, String message) {
		return showOkDialog(context, title, message, null);
	}

	public static AlertDialog showOkDialog(Activity context, String title, String message, final DialogInterface.OnDismissListener afterDismiss) {
		try {
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage(message).setCancelable(false);
			if (title != null) {
				builder.setTitle(title);
			}
			builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int id) {
					dialog.dismiss();
					if (afterDismiss != null) {
						afterDismiss.onDismiss(dialog);
					}
				}
			});
			if (!context.isFinishing()) {
				AlertDialog alert = builder.create();
				if (title == null) {
					alert.requestWindowFeature(Window.FEATURE_NO_TITLE);
				}
				alert.show();
				try {
					((TextView)alert.findViewById(android.R.id.message)).setGravity(Gravity.CENTER);
				} catch (Exception ex) {}
				return alert;
			}
		} catch (Exception e) {
			e.printStackTrace();
			//ignore
		}
		return null;
	}
	
//	private float getDip(float pix) {
//
//		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, pix, getResources().getDisplayMetrics());
//		return px;
//	}
	
	/**
	 * Returns an array of localized weekday names starting from Monday. 
	 * @param locale - Represents needed language and area codes. E.g. new Locale('fr')
	 * 				   passing null uses default system locale
	 * @return	an array containing translated week days' names
	 */
	public static String[] getLocalizedWeekdays(Locale locale, boolean shortStr) {
		DateFormatSymbols dfSymbols;
		if (locale != null) {
			dfSymbols = new DateFormatSymbols(locale);
		} else {
			dfSymbols = new DateFormatSymbols();
		}
		String[] wDays = shortStr ? dfSymbols.getShortWeekdays() : dfSymbols.getWeekdays();
		int[] days = {Calendar.MONDAY, Calendar.TUESDAY,	//order of days to appear in final array
				      Calendar.WEDNESDAY, Calendar.THURSDAY,
				      Calendar.FRIDAY, Calendar.SATURDAY, Calendar.SUNDAY};
		String[] weekDays = new String[days.length];
		for (int i = 0; i < days.length; i++) {	//map results
			weekDays[i] = wDays[days[i]];
		}
		return weekDays;
	}
	
	/**
	 * Returns an array of localized months' names starting from January.
	 * @param languageCode - Represents needed language and area codes. E.g. new Locale('fr')
	 * 						 passing null uses default system locale
	 * @return	an array containing translated months' names
	 */
	public static String[] getLocalizedMonths(Locale locale, boolean shortStr) {
		DateFormatSymbols dfSymbols;
		if (locale != null) {
			dfSymbols = new DateFormatSymbols(locale);
		} else {
			dfSymbols = new DateFormatSymbols();
		}
		String[] allMonths = shortStr ? dfSymbols.getShortMonths() : dfSymbols.getMonths();
		int[] months = {Calendar.JANUARY, Calendar.FEBRUARY, Calendar.MARCH,
						Calendar.APRIL, Calendar.MAY, Calendar.JUNE,
						Calendar.JULY, Calendar.AUGUST, Calendar.SEPTEMBER,
						Calendar.OCTOBER, Calendar.NOVEMBER, Calendar.DECEMBER};
		String[] retMonths = new String[months.length];
		for (int i = 0; i < months.length; i++) {
			retMonths[i] = allMonths[months[i]];
		}
		return retMonths;
	}
	
	public static int dpToPx(float dp, Context c) {
		return (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, c.getResources().getDisplayMetrics());
	}

	public static int getRecursiveTop(View anchor) {
		int top = anchor.getTop();
		ViewGroup parent = (ViewGroup)anchor.getParent();
		while (parent.getParent() != null && parent.getParent() instanceof ViewGroup) {
			parent = (ViewGroup)parent.getParent();
			top += parent.getTop();
		}
		return top;
	}
	
	public static int getRecursiveLeft(View anchor) {
		int top = anchor.getLeft();
		ViewGroup parent = (ViewGroup)anchor.getParent();
		while (parent.getParent() != null && parent.getParent() instanceof ViewGroup) {
			parent = (ViewGroup)parent.getParent();
			top += parent.getLeft();
		}
		return top;
	}

	public static void hideSoftKeyboard(Context context, View keyboardTrigger) {
		InputMethodManager imm = (InputMethodManager)context.getSystemService(
			      Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(keyboardTrigger.getWindowToken(),
				0);
	}

	public static String makeFragmentName(int viewId, int index) {
        return "android:switcher:" + viewId + ":" + index;
    }

	public static Bitmap getFlagBitmap(Context c, String shortCode) {
		try {
			return BitmapFactory.decodeStream(c.getAssets().open("flags/" + shortCode.toLowerCase() + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void removeFromParent(View view) {
		if (view != null && view.getParent() != null) {
			((ViewGroup)view.getParent()).removeView(view);
		}
	}

	
	public static void setLocale(String langCode, Context context) {
		Resources res = context.getResources();
	    // Change locale settings in the app.
	    DisplayMetrics dm = res.getDisplayMetrics();
	    android.content.res.Configuration conf = res.getConfiguration();
	    conf.locale = localeFromStr(langCode);
	    res.updateConfiguration(conf, dm);

	}
	
	public static Locale localeFromStr(String str) {
		Locale locale = new Locale("en");
		if (str.length() == 2) {	//e.g. "en"
	    	locale = new Locale(str.toLowerCase());
	    } else if(str.length() == 6) { //e.g. "nl-rBE"
	    	locale = new Locale(str.substring(0, 2), str.substring(4));
	    }
		return locale;
	}
	
//	public static ViewGroup getRootView(View child) {
//		ViewGroup root;
//		if (child instanceof ViewGroup) {
//			root = (ViewGroup) child;
//		} else {
//			root = (ViewGroup) child.getParent();
//		}
//		while (root.getParent() != null && root.getParent() instanceof ViewGroup) {
//			root = (ViewGroup)root.getParent();
//		}
//		return root;
//	}
}
