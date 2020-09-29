package com.chan.chanprotobuf

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chan.chanprotobuf.util.ZPlatformLayout
import com.chan.protolibrary.ProtoUtil.convertUsingProtoBuf
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clickButton.setOnClickListener {
            readJSONFromAsset()?.let {
                Log.d("ChanLog", "json: $it")
                //convertUsingGSon(it)
                convertUsingProtoBuf(it)
            }
            /*readInputStreamFromAsset()?.let {
                protoCheck(it)
            }*/
        }
        //protoCheck()
    }

    private fun convertUsingGSon(data: JSONObject) {
        val platformLayout = data.convertTo<ZPlatformLayout>()
        val test = "{\"data\":\"chandran\"}"

        //val gSon = Gson()
        //val platformLayout: ZPlatformLayout = Gson().fromJson(it, ZPlatformLayout::class.java)
        Log.d("ChanLog", "Value: ${platformLayout.data}")
    }

    private inline fun <reified X> Any.convertTo(): X {
        val gSon = GsonBuilder().serializeNulls().create()
        return gSon.fromJson<X>(this.toString(), object : TypeToken<X>() {}.type)
    }

    private fun Context.readJSONFromAsset(fileName: String = "JsonData.json"): JSONObject? {
        return try {
            val inputStream: InputStream = assets.open(fileName)
            //JSONObject(inputStream.bufferedReader().use{it.readText()})
            JSONObject(inputStream.bufferedReader().use { it.readText() })
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private fun Context.readInputStreamFromAsset(fileName: String = "JsonData.json"): InputStream? = run {
        try {
            assets.open(fileName)
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
    }

    private fun convertUsingProtoBufInternal(data: JSONObject) {

        /*CoroutineScope(Dispatchers.IO).launch {
            val url = URL("https://run.mocky.io/v3/226a046f-7b62-4086-b90d-f5081cbc5451")
            val urlConnection: HttpURLConnection = url.openConnection() as HttpURLConnection
            try {
                //val inputStream: InputStream = BufferedInputStream(urlConnection.inputStream)
                //Log.d("ChanLog", "NetworkResponse: ${inputStream.bufferedReader().use { it.readText() }} ")
                //val chanData = ChanDataProto.ChanData.parseFrom(urlConnection.inputStream)
                val chanData = ChanDataProto.ChanData.newBuilder().mergeFrom(urlConnection.inputStream).build()
                Log.d("ChanLog", "Value: ${chanData.platform}")
            } finally {
                urlConnection.disconnect()
            }
        }*/
        //convertUsingProtoBuf()
    }


}