package com.example.lyfstile

/**
 *
 * Class representing data to be passed between fragments/activities
 *
 * MUST USE SENDER!
 *
 *
 *
 */
public class Data(_sender: String,_data: String)
{
    var sender: String = _sender
    var data : String = _data
}

public interface PassData{
    fun onDataPass(data: Data)
}