package com.hmlr.model

import net.corda.core.identity.Party
import net.corda.core.serialization.CordaSerializable

@CordaSerializable
data class TrustObject(val token: String)

@CordaSerializable
data class Provider(val party: Party?, val partyUrl: String?)

