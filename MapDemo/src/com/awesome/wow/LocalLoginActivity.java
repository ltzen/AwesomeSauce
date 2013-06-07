package com.awesome.wow;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 

public class LoginActivity extends Activity {
    Button btnLogin;
    Button btnLinkToRegister;
    EditText inputEmail;
    EditText inputPassword;
    TextView loginErrorMsg;
    
 
    // JSON Response node names
    private static String KEY_SUCCESS = "success";
    private static String KEY_ERROR = "error";
    private static String KEY_ERROR_MSG = "error_msg";
    private static String KEY_UID = "uid";
    private static String KEY_NAME = "name";
    private static String KEY_EMAIL = "email";
    private static String KEY_CREATED_AT = "created_at";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
 
        // Importing all assets like buttons, text fields
        inputEmail = (EditText) findViewById(R.id.loginEmail);
        inputPassword = (EditText) findViewById(R.id.loginPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
        loginErrorMsg = (TextView) findViewById(R.id.login_error);
 
        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                
                HttpClient hc = new DefaultHttpClient();
        		//HttpPost hp = new HttpPost("goldberglinux02.tamu.edu/login.php");
                HttpPost hp = new HttpPost("http://10.0.2.2/home/prachi/login.php");
                //HttpPost hp = new HttpPost("nss324-ck.cs.tamu.edu/login.php");
        		
                
                HttpResponse hr = null;
        		HttpEntity he = null;
        		InputStream is = null;
        		InputStreamReader isr = null;
        		BufferedReader br = null;
        		StringBuilder sb = new StringBuilder();
        		
        		List<NameValuePair> nvplist = new ArrayList<NameValuePair>(2);
        		nvplist.add(new BasicNameValuePair("username", email));
        		nvplist.add(new BasicNameValuePair("pwd", password));
        		
        		try {
        			hp.setEntity(new UrlEncodedFormEntity(nvplist));
        			Log.w("Login", "Execute HTTP Post Request");
        			hr = hc.execute(hp);
        			he = hr.getEntity();
        			is = he.getContent();
        			Log.w("Login", "Response Handling");
        			
        			isr = new InputStreamReader(is, "iso-8859-1");
        			br = new BufferedReader(isr, 8);
        			
        			String line = null;
        			line = br.readLine();
        			while(line != null){
        				sb.append(line + "\n");
        				line = br.readLine();
        			}
        			is.close();
        			
        		} catch (UnsupportedEncodingException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (ClientProtocolException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (IOException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		} catch (IllegalStateException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		
        		String response = sb.toString();
        		Log.w("Login", response);
        		/*
        		//correct uname, passwd combo
        		if(response.startsWith("1")){
        			Log.w("Login", "TRUE");
        			runOnUiThread(new Runnable(){
        				public void run(){
        					Toast.makeText(ExistingAcctActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
        				}
        			});
        			//sends a message containing username and uid to the MainActivity
        			//startActivity(new Intent(this, MainActivity.class));
        			
        			Intent intent = new Intent(this, MainActivity.class);
        			intent.putExtra("android.wellness.MESSAGE", response);
        			startActivity(intent);			
        		}
        		
        		//incorrect passwd
        		else if(response.startsWith("2")){
        			Log.w("Login", "FALSE");
        			
        			note.setText("Incorrect password");
        			note.setVisibility(View.VISIBLE);
        		}
        		
        		//uname does not exist
        		else if(response.startsWith("3")){
        			Log.w("Login", "FALSE");
        			
        			note.setText("Unknown username");
        			note.setVisibility(View.VISIBLE);
        		}
        		
        		//something went wrong, please try again
        		else if(response.startsWith("4")){
        			Log.w("Login", "FALSE");
        			
        			note.setText("Something went wrong, please check your input.");
        			note.setVisibility(View.VISIBLE);
        		}
        		
        		//unable to query database
        		else if(response.startsWith("5")){
        			Log.w("Login", "FALSE");
        			
        			note.setText("Sorry, unable to query database.");
        			note.setVisibility(View.VISIBLE);
        		}
        		
        		//we received garbage
        		else{
        			Log.w("Login", "FALSE");
        			
        			note.setText("Error, unable to login.");
        			note.setVisibility(View.VISIBLE);
        		}
        		*/
                
                /*
                UserFunctions userFunction = new UserFunctions();
                JSONObject json = userFunction.loginUser(email, password);
 
                // check for login response
                try {
                    if (json != null && json.getString(KEY_SUCCESS) != null) {
                        loginErrorMsg.setText("");
                        String res = json.getString(KEY_SUCCESS);
                        if(Integer.parseInt(res) == 1){
                            // user successfully logged in
                            // Store user details in SQLite Database
                            DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                            JSONObject json_user = json.getJSONObject("user");
 
                            // Clear all previous data in database
                            userFunction.logoutUser(getApplicationContext());
                            db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));                        
 
                            // Launch Dashboard Screen
                            Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
 
                            // Close all views before launching Dashboard
                            dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(dashboard);
 
                            // Close Login Screen
                            finish();
                        }else{
                            // Error in login
                            loginErrorMsg.setText("Incorrect username/password");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
                */
                
            }
        });
 
        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
 
            public void onClick(View view) {
               /* Intent i = new Intent(getApplicationContext(),
                        RegisterActivity.class);
                startActivity(i);
                finish();*/
            }
        });
    }
}

