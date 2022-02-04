package com.example.lyfstile

/**
 *
 * Class representing data to be passed between fragments/activities
 *
 * MUST USE SENDER!
 *
 * CAUTION WITH USE!!! WILL BE CHANGING!!!
 *
 */
public class Data(_sender: String,_data: Map<String,String>)
{
    var sender: String = _sender

    var data: Map<String, String> = _data

public fun appendData(key : String, value : String)
{
    data.map { key to value }

}

 public fun getAll(): Set<Map.Entry<String, String>> {
     return data.entries
 }
public fun getData(key : String): String {

    return data.getValue(key)
}

}

public interface PassData{
    fun onDataPass(data: Data)
}