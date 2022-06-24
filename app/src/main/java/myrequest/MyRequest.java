package myrequest;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequest {
    private Context context;
    private RequestQueue queue;

    public MyRequest(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }

    public void register(String pseudo,String mail,String mdp1,String mdp2, registerCallback callback){

        String url = "http://192.168.56.1/mobile/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Map<String , String> errors=new HashMap<>();
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        //inscription reussi
                        callback.onSuccess("Inscription reussi");
                    }else{
                        //JSONObject obj = new JSONObject(response);
                        JSONObject messages = json.getJSONObject("message");
                        if (messages.has("pseudo")){
                            errors.put("pseudo", messages.getString("pseudo"));
                        }
                        if (messages.has("mail")){
                            errors.put("mail", messages.getString("mail"));
                        }
                        if (messages.has("mdp")){
                            errors.put("mdp", messages.getString("mdp"));
                        }
                        callback.inputError(errors);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                //Log.d("APP",response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("APP","ERROR =" + error);
                if(error instanceof NetworkError){
                    callback.onError("connexion impossible");
                }else if (error instanceof VolleyError){
                    callback.onError("une erreur s'est produite!");
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("pseudo",pseudo);
                map.put("mail",mail);
                map.put("mdp1",mdp1);
                map.put("mdp2",mdp2);
                return map;
                //return super.getParams();
            }
        };
        queue.add(request);
    }
    public interface registerCallback{

        void onSuccess(String message);
        void inputError(Map<String,String> errors);
        void onError(String message);
    }

    public void connexion(String mail,String password, LoginCallback callback){

        String url = "http://192.168.56.1/mobile/login.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Log.d("APP",response);
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error){
                        //connexion reussi
                        String id = json.getString("id");
                        String mail = json.getString("mail");

                        callback.onSuccess(id,mail);
                    }else{
                        callback.onError(json.getString("message"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //Log.d("APP","ERROR =" + error);
                if(error instanceof NetworkError){
                    callback.onError("connexion impossible");
                }else if (error instanceof VolleyError){
                    callback.onError("une erreur s'est produite!");
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("mail",mail);
                map.put("password",password);
                return map;
                //return super.getParams();
            }
        };
        queue.add(request);
    }

    public interface LoginCallback{

        void onSuccess(String id,String mail);
        void onError(String message);
    }
}
