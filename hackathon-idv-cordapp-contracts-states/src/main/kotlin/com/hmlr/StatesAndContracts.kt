package com.hmlr

import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party
import net.corda.core.transactions.LedgerTransaction
import com.hmlr.*
import com.hmlr.model.Provider
import com.hmlr.model.TrustObject

// ************
// * Contract *
// ************
class TemplateContract : Contract {
    companion object {
        // Used to identify our contract when building a transaction.
        const val ID = "com.hmlr.TemplateContract"
    }
    
    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    override fun verify(tx: LedgerTransaction) {
        // Verification logic goes here.
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Action : Commands
    }
}

// *********
// * State *
// *********
data class TrustRequestState(val requestor: Party,
                             val provider: Provider,
                             val context: String) : ContractState {
    override val participants: List<AbstractParty> = listOf()
}

data class TrustState(val requestor: Party,
                      val provider: Party,
                      val data: TrustObject,
                      val validated: Boolean) : ContractState {
    override val participants: List<AbstractParty> = listOf()
}
