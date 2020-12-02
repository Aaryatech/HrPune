package com.ats.hrpune.constant;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;


import com.ats.hrpune.inerfaces.InterfaceApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Constants {

    //public static final String BASE_URL ="http://192.168.2.16:8093/";
    //public static final String BASE_URL = "http://132.148.143.124:8080/HrManagementSystem/";
   // public static final String BASE_URL = "http://ifbthrms.infrabeat.com:8181/HrManagementSystem/";
  //  public static final String BASE_URL = "http://107.180.91.43:8080/HrEsayWebApi/";
    public static final String BASE_URL = "http://107.180.88.121:8080/HrEsayWebApi/";
    public static final String PDF_URL = "http://107.180.88.121:8080/HrEasy/pdfForReport?url=/pdf/generatedPayrollPdf/";

   // public static final String BASE_URL = "http://107.180.91.43:8080/HrEsayWebApi/";

   // public static final String BASE_URL = "http://172.20.10.9:8094/";
 //  public static final String BASE_URL = "http://192.168.43.7:8094/";

    public static final String userName = "aaryatech";

    public static final String password = "Aaryatech@1cr";

   // public static final String DOC_URL = "http://ifbthrms.infrabeat.com:8181/hr/";
    public static final String DOC_URL = "";
   // public static final String IMAGE_URL = "http://ifbthrms.infrabeat.com:8181/hr/";
    public static final String IMAGE_URL = "http://107.180.88.121:8080/hrdocument/mixDoc/";


    public static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Accept", "application/json")
                            .method(original.method(), original.body())
                            .build();

                    Response response = chain.proceed(request);
                    return response;
                }
            })
            .readTimeout(10000, TimeUnit.SECONDS)
            .connectTimeout(10000, TimeUnit.SECONDS)
            .writeTimeout(10000, TimeUnit.SECONDS)
            .build();


    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create()).build();

    public static InterfaceApi myInterface = retrofit.create(InterfaceApi.class);

    public static boolean isOnline(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conMgr.getActiveNetworkInfo();

        if (netInfo == null || !netInfo.isConnected() || !netInfo.isAvailable()) {
            Toast.makeText(context, "No Internet Connection ! ", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }


}
