package com.chan.chanprotobuf

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        protoCheck()
    }

    private fun protoCheck() {
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