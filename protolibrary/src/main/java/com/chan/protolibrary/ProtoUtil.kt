package com.chan.protolibrary

import android.util.Log
import com.google.protobuf.util.JsonFormat
import org.json.JSONObject
import java.util.*

object ProtoUtil {

    fun convertUsingProtoBuf(data: JSONObject) {
        Log.d("ChanLog", "Proto Check")
        //val chanData = ChanDataProto.ChanData.newBuilder().mergeFrom(data.readBytes()).build()
        try {
            val builder = ChanDataProto.ChanData.newBuilder()
            JsonFormat.parser().ignoringUnknownFields().merge(data.toString(), builder)
            val chanData = builder.build()
            chanData.takeIf { it.hasNullableIntValue() }?.let {
                Log.d("ChanLog", "NullableIntValue: ${it.nullableIntValue}")
            }?: Log.d("ChanLog", "NullableIntValue: emptyValue")
            chanData.colorsList.forEach {
                Log.d("ChanLog", "BGColor=${it.takeIf { it.hasBgColor() }?.bgColor?:"Empty"}")
                Log.d("ChanLog", "TextColor=${it.textColor}")
                Log.d("ChanLog", "BorderColor=${it.takeIf { it.hasBorderColor() }?.borderColor?:"Empty"}")
            }
            Log.d("ChanLog", "layoutId: ${chanData.layout}, type: ${chanData.type}, meta: ${chanData.meta}")
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }
}


fun convertUsingProtoBufAnother() {
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
    val phoneNumbers = ArrayList<AddressBookProtos.Person.PhoneNumber>()
    phoneNumbers.add(phoneHome)
    phoneNumbers.add(phoneMobile)
    val person = AddressBookProtos.Person.newBuilder()
        .setId(1)
        .setName("Mohsen")
        .setEmail("info@mohsenoid.com")
        .addAllPhones(phoneNumbers)
        .build()

    // building an AddressBook object using person data
    val peoples = ArrayList<AddressBookProtos.Person>()
    peoples.add(person)
    val addressBook = AddressBookProtos.AddressBook.newBuilder()
        .addAllPeople(peoples)
        .build()

    // finally this is how you get serialized ByteArray
    val bytes = addressBook.toByteArray()

    // You can deserialize AddressBook bytes
    val myAddressBook = AddressBookProtos.AddressBook.parseFrom(bytes)
}