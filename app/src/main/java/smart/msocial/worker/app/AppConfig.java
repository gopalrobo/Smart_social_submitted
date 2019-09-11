package smart.msocial.worker.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import smart.msocial.worker.R;
import smart.msocial.worker.field.FiledWork;

public class AppConfig {

    private static Font catFont = new Font(Font.FontFamily.HELVETICA, 18,
            Font.BOLD);
    //Key values
    public static final String studentIdKey = "studentIdKey";
    public static final String staffIdKey = "staffIdKey";
    public static final String isStaff = "isStaffKey";
    public static final String tittle = "tittleKey";
    public static final String loginKey = "loginKey";

    public static final String mypreference = "mypref";

    public static final String URL = "http://mssw.in";

    public static String iplocalfarmer = "192.168.43.233/coconut/farmer";
    public static String iplocalpro = "192.168.43.233/coconut/product";
    public static String iplocalplot = "192.168.43.233/coconut/plot";
    public static String iplocalpop = "192.168.43.233/coconut/pop";
    public static String iplocalanimal = "192.168.43.233/coconut/animal";
    public static String iplocalservice = "192.168.43.233/coconut/service";
    public static String iplocalprofile = "192.168.43.233/coconut/profile";
    public static String iplocaltree = "192.168.43.233/coconut/tree";


    public static String ipcloud = "coconutfpo.smartfpo.com/msocial";

    public static String ipsms = "192.168.43.233/android_sms/msg91";

    // Server user login url
    public static String URL_GET_ALL_SHEET = "http://" + ipcloud + "/get_all_datasheet.php";
    public static String URL_GET_ALL_CCSHEET = "http://" + ipcloud + "/get_all_ccsheet.php";
    public static String URL_REGISTER = "http://" + ipcloud + "/create_vrp.php";
    public static String URL_LOGIN = "http://" + ipcloud + "/get_vrp_login.php";
    public static String URL_UPDATE = "http://" + ipcloud + "/update_vrp.php";
    public static String URL_FARMER_DETAIL = "http://" + ipcloud + "/get_vrp_details.php";

    public static String URL_ALL_PROFILE = "http://" + ipcloud + "/get_all_profile.php";
    public static String URL_ALL_VRPS = "http://" + ipcloud + "/get_all_vrps.php";
    public static String URL_PROFILE_CREATE = "http://" + ipcloud + "/create_profile.php";
    public static String URL_PROFILE_UPDATE = "http://" + ipcloud + "/update_profile.php";

    public static String URL_CREATE_GROUP = "http://" + ipcloud + "/create_group_project.php";
    public static String URL_GET_GROUP = "http://" + ipcloud + "/get_group_project.php";

    public static String URL_CREATE_OBSERVISIT = "http://" + ipcloud + "/create_observation_visit.php";
    public static String URL_GET_OBSERVISIT = "http://" + ipcloud + "/get_observation_visit.php";

    public static String URL_CREATE_FIELD_WORK = "http://" + ipcloud + "/create_field_work.php";
    public static String URL_GET_FIELD_WORK = "http://" + ipcloud + "/get_field_work.php";

    public static String URL_CREATE_ASSIGNMENT = "http://" + ipcloud + "/create_assignment.php";
    public static String URL_GET_ASSIGNMENT = "http://" + ipcloud + "/get_assignment.php";

    public static String URL_CREATE_RESEARCH = "http://" + ipcloud + "/create_research.php";
    public static String URL_GET_RESEARCH = "http://" + ipcloud + "/get_research.php";

    public static String URL_CREATE_PLACEMENT = "http://" + ipcloud + "/create_placement.php";
    public static String URL_GET_PLACEMENT = "http://" + ipcloud + "/get_placement.php";

    // server URL configuration
    public static final String URL_REQUEST_SMS = "http://" + ipcloud + "/request_sms.php";
    public static final String URL_VERIFY_OTP = "http://" + ipcloud + "/verify_otp.php";

    public static final String URL_GET_SUBJECTS = "http://" + ipcloud + "/new/get_subjects.php";
    public static final String URL_ALL_FIELDWORK = "http://" + ipcloud + "/new/get_student_ff.php";
    public static final String URL_GET_STUDENTS = "http://" + ipcloud + "/new/get_students.php";
    public static final String URL_CREATE_STAFF = "http://" + ipcloud + "/new/create_staff.php";
    public static final String URL_GET_ALL_STAFF = "http://" + ipcloud + "/new/get_staffs.php";
    public static final String URL_GET_ALL_OV = "http://" + ipcloud + "/new/get_all_ov.php";
    public static final String URL_GET_ALL_ASSIGNMENT = "http://" + ipcloud + "/new/get_all_assignment.php";
    public static final String URL_GET_ALL_CP = "http://" + ipcloud + "/new/get_all_cp.php";
    public static final String URL_GET_ALL_BP = "http://" + ipcloud + "/new/get_all_bp.php";
    public static final String URL_GET_ALL_FW = "http://" + ipcloud + "/new/get_all_fw.php";
    public static final String URL_GET_ALL_CC = "http://" + ipcloud + "/new/get_all_cc.php";
    public static final String URL_GET_ALL_IS = "http://" + ipcloud + "/new/get_all_is.php";
    public static final String URL_GET_ALL_SP = "http://" + ipcloud + "/new/get_all_sp.php";
    public static final String URL_GET_ALL_GP = "http://" + ipcloud + "/new/get_all_gp.php";
    public static final String URL_GET_ALL_RE = "http://" + ipcloud + "/new/get_all_re.php";
    public static final String URL_LOGIN_STAFF = "http://" + ipcloud + "/new/get_teacher_login.php";

    // SMS provider identification
    // It should match with your SMS gateway origin
    // You can use  MSGIND, TESTER and ALERTS as sender ID
    // If you want custom sender Id, approve MSG91 to get one
    public static final String SMS_ORIGIN = "CSFARM";

    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";
    public static String URL_ALL_OFFICER = "http://" + ipcloud + "/get_all_officer.php";
    public static String URL_ALL_SOFFICER = "http://" + ipcloud + "/get_all_sofficer.php";


    private static final Map<String, Integer> attendances = new HashMap<String, Integer>() {{

        put("Informal Exposure Walk", R.string.InformalExposureWalk);

        put("Historical Timeline", R.string.HistoricalTimeline);

        put("Gender Analysis", R.string.GenderAnalysis);

        put("Village Resource Mapping (Village, water and soil maps)", R.string.VillageMapping);

        put("Transect Walk", R.string.VillageTransectWalk);

        put("Seasonal Analysis", R.string.SeasonalAnalysis);

        put("Watershed Resources Analysis (forest, agriculture and other resources)", R.string.ForestResourcesAnalysis);

        put("Livelihood Analysis", R.string.LivelihoodAnalysis);

        put("Institutional Analysis", R.string.InstitutionalAnalysis);

        put("S.W.O.T. Analysis", R.string.SWOTAnalysis);

        put("Wealth Ranking", R.string.WealthRanking);
    }};

    public static final Integer getProcess(String name) {
        return attendances.get(name);
    }

    public static final String YOUTUBE_API_KEY = "AIzaSyCG25IwSEZcJuF5Te7kko9XawkHaEJ48Ws";


    public static void styleToast(String msg, Context context) {
        Toast toast = Toast.makeText(context,
                msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
        TextView tv = (TextView) toast.getView().findViewById(android.R.id.message);
        toast.getView().setBackgroundResource(R.drawable.shape_toast);
        toast.show();
    }


    public static void setDateEditText(final EditText editText, AppCompatActivity appCompatActivity) {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH); // Note: zero based!
        int day = now.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
                try {
                    String dateString = String.valueOf(day) + "-" + String.valueOf(month + 1) + "-" + String.valueOf(year);
                    SimpleDateFormat sdfSource = new SimpleDateFormat("dd-MM-yy");
                    Date date = sdfSource.parse(dateString);
                    SimpleDateFormat resultSource = new SimpleDateFormat("E, dd-MMM-yy");
                    editText.setText(resultSource.format(date));
                } catch (Exception e) {
                    Log.e("Exception", e.toString());
                }
            }
        }, year, month, day);
        datePickerDialog.setYearRange(1917, 2030);
        datePickerDialog.setCloseOnSingleTapDay(true);
        datePickerDialog.show(appCompatActivity.getSupportFragmentManager(), "3000");
    }

    public static void addMetaData(Document document) {
        document.addTitle("My first PDF");
        document.addSubject("Using iText");
        document.addKeywords("Java, PDF, iText");
        document.addAuthor("Lars Vogel");
        document.addCreator("Lars Vogel");
    }

    public static void addContent(Document document, ArrayList<FiledWork> sheets, Context context) throws Exception {

        PdfPTable table1 = new PdfPTable(1);
        table1.setWidthPercentage(100);
        table1.setWidths(new int[]{1});
        for (int i = 0; i < sheets.size(); i++) {
            table1.addCell(createTextCell("Field Work Sheet " + String.valueOf(i + 1),
                    catFont));
            LinkedHashMap<String, String> tempResult = sheets.get(i).toMap();
            for (String key : tempResult.keySet()) {
                table1.addCell(createTextCell(key,
                        new Font(Font.FontFamily.HELVETICA, 15,
                                Font.BOLD)));
                table1.addCell(createTextCell(tempResult.get(key),
                        new Font(Font.FontFamily.HELVETICA, 14,
                                Font.NORMAL)));
            }

            table1.setKeepTogether(true);
        }
        document.add(table1);

    }

    public static PdfPCell createTextCell(String text, Font font) throws DocumentException, IOException {
        PdfPCell cell = new PdfPCell();
        Paragraph p = new Paragraph(text, font);
        p.setAlignment(Element.ALIGN_LEFT);
        cell.addElement(p);
        cell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        cell.setBorder(Rectangle.NO_BORDER);
        return cell;
    }
}
