package com.hmlr

import co.paralleluniverse.fibers.Suspendable
import com.hmlr.model.*
import net.corda.core.contracts.Command
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class RequestTrustObjectFlow(val requestor: Individual,
                             val provider: Provider,
                             val context: String): FlowLogic<Unit>() {
    override val progressTracker = ProgressTracker()


    @Suspendable
    override fun call() {
        val notary = serviceHub.networkMapCache.notaryIdentities[0]

        val outputState = TrustRequestState(requestor, provider, context)
        val cmd = Command(TemplateContract.Commands.Action(), ourIdentity.owningKey)

        val txBuilder = TransactionBuilder(notary = notary)
                .addOutputState(outputState, TemplateContract.ID)
                .addCommand(cmd)

        val signedTx = serviceHub.signInitialTransaction(txBuilder)

        subFlow(FinalityFlow(signedTx))
    }
}

@InitiatingFlow
@StartableByRPC
class ReturnTrustObjectFlow(val requestor: Individual,
                            val trustObject: TrustObject) : FlowLogic<Unit>() {

    @Suspendable
    override fun call() {
        val notary = serviceHub.networkMapCache.notaryIdentities[0]

        val outputState = TrustState(requestor, ourIdentity, trustObject, false)
        val cmd = Command(TemplateContract.Commands.Action(), ourIdentity.owningKey)

        val txBuilder = TransactionBuilder(notary = notary)
                .addOutputState(outputState, TemplateContract.ID)
                .addCommand(cmd)

        val signedTx = serviceHub.signInitialTransaction(txBuilder)

        subFlow(FinalityFlow(signedTx))
    }
}
