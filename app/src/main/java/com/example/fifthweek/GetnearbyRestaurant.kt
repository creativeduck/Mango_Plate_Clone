package com.example.fifthweek

import android.os.AsyncTask
import android.util.Log
import com.example.fifthweek.ui.MAPnURL
import com.google.android.gms.maps.GoogleMap
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.Buffer

class GetnearbyRestaurant : AsyncTask<MAPnURL, String, String>() {
    lateinit var mMap : GoogleMap
    lateinit var url : String
    lateinit var inputStream : InputStream
    lateinit var bufferedReader : BufferedReader
    lateinit var stringBuilder: StringBuilder
    lateinit var data : String

    override fun doInBackground(vararg obj: MAPnURL): String {
        mMap = obj[0].map
        url = obj[0].url


        try {
            val myurl : URL = URL(url)
            val httpURLConnection = myurl.openConnection() as HttpURLConnection
            httpURLConnection.connect()
            inputStream = httpURLConnection.inputStream
            bufferedReader = BufferedReader(InputStreamReader(inputStream))

            var line : String? = null
            stringBuilder = StringBuilder()

            line = bufferedReader.readLine()
            while (line != null) {
                stringBuilder.append(line)
                line = bufferedReader.readLine()
            }

            data = stringBuilder.toString()
            Log.d("HTTP_URL_CONNEXTION", "${data}")
        } catch (e: MalformedURLException) {
            Log.e("HTTP_URL_CONNEXTION", "${e.printStackTrace()}")
        } catch (e: IOException) {
            e.printStackTrace()
        }



        return data
    }


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)


    }

}