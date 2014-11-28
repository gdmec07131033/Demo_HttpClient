package cn.edu.gdmec.s07131033.demo_httpclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.DefaultClientConnection;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	private HttpClient client;
	private HttpResponse response;
	private Handler handler;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.textView1);
        handler = new Handler()
        {
        	@Override
        	public void handleMessage(Message msg) {
        		tv.setText((CharSequence) msg.obj);
        	}
        };
    }
    public void SUBMIT(View v) {
		new Thread()
		{
			@Override
			public void run() {
				//联网操作
		    	HttpGet httpGet = new HttpGet("http://www.baidu.com");
		    	client = new DefaultHttpClient();
		    	InputStream inputStream = null;
		    	try {
					response = client.execute(httpGet);
					
					if(response.getStatusLine().getStatusCode() == 200)
					{
						//联网成功
						HttpEntity entity = response.getEntity();
						inputStream = entity.getContent();
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
						StringBuilder buffer = new StringBuilder();
						String text = "";
						while((text = reader.readLine())!=null)
						{
							buffer.append(text);
						}
						
						Message msg = Message.obtain();
						msg.obj = buffer.toString();
						handler.sendMessage(msg);
						
						reader.close();
					}
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generate d catch block
					e.printStackTrace();
				}
			}
		}.start();
    	
    	 
    	
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
