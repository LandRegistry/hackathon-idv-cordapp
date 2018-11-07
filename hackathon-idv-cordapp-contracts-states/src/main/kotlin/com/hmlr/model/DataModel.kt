package com.hmlr.model

import net.corda.core.identity.Party
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class TrustObject(val token: String, val provider: Provider)

@CordaSerializable
data class Provider(val party: Party?, val partyUrl: String?)

@CordaSerializable
data class Individual(val trustObjects: List<TrustObject>,
                      val forename: String,
                      val surname: String)
