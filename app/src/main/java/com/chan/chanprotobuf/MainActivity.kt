package com.chan.chanprotobuf

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.chan.chanprotobuf.util.ZPlatformLayout
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.protobuf.util.JsonFormat
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

    private fun convertUsingProtoBuf(data: JSONObject) {
        Log.d("ChanLog", "Proto Check")
        //val chanData = ChanDataProto.ChanData.newBuilder().mergeFrom(data.readBytes()).build()
        try {
            val builder = ChanDataProto.ChanData.newBuilder()
            JsonFormat.parser().ignoringUnknownFields().merge(data.toString(), builder)
            val chanData = builder.build()
            Log.d("ChanLog", "layoutId: ${chanData.layout}, type: ${chanData.type}, meta: ${chanData.meta}")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
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

    private fun convertUsingProtoBuf() {
        // building PhoneNumber objects
        val phoneHome = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49123456")
                .setType(AddressBookProtos.Person.PhoneType.HOME)
                .build()
        val phoneMobile = AddressBookProtos.Person.PhoneNumber.newBuilder()
                .setNumber("+49654321")
                .setType(AddressBookProtos.Person.PhoneType.MOBILE)
                .build()

        // building a Person object using phone data
        val person = AddressBookProtos.Person.newBuilder()
                .setId(1)
                .setName("Mohsen")
                .setEmail("info@mohsenoid.com")
                .addAllPhones(listOf(phoneHome, phoneMobile))
                .build()

        // building an AddressBook object using person data
        val addressBook = AddressBookProtos.AddressBook.newBuilder()
                .addAllPeople(listOf(person))
                .build()

        // finally this is how you get serialized ByteArray
        val bytes = addressBook.toByteArray()

        // You can deserialize AddressBook bytes
        val myAddressBook = AddressBookProtos.AddressBook.parseFrom(bytes)
    }
}