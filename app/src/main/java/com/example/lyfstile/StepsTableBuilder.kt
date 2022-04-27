package com.example.lyfstile

class StepsTableBuilder {
    private var email : String ?= null
    private var steps : String ?= null

    public fun setData(email : String?) : StepsTableBuilder
    {
        this.email = email
        return this
    }

    fun setPassword(steps: String?) : StepsTableBuilder
    {
        this.steps = steps
        return this
    }


    public fun createStepsTable(): StepsEntity {return StepsEntity(email as String, steps as String,"0","0","0")}
}