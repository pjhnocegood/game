package com.game.scheduler.web3.component;

import com.game.scheduler.airdrop.scheduler.AirdropScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.exceptions.TransactionException;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.RawTransactionManager;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.tx.response.PollingTransactionReceiptProcessor;
import org.web3j.tx.response.TransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class Web3Component {






         @Value("${web3.ethereum.rpc-url}")
         private  String WEB3_PROVIDER_URL ;

         @Value("${web3.ethereum.private-key}")
         private String PRIVATE_KEY;

         @Value("${web3.ethereum.chain-id}")
         private Long CHAIN_ID;

         long TX_END_CHECK_DURATION = 5000;
         int TX_END_CHECK_RETRY = 3;
         private static final Logger log = LoggerFactory.getLogger(Web3Component.class);
        //WEB3J를 사용하여 트랜잭션을 발생시키는 메소드


    public void sendTransaction(String functionName, String contractAddress, List<Type> params , List<TypeReference<?>> returnTypes) throws InterruptedException, IOException, TransactionException {
        Web3j web3j = Web3j.build(new HttpService(WEB3_PROVIDER_URL));
        Credentials credentials = Credentials.create(PRIVATE_KEY);
        Function function = new Function(functionName,
                params,
                returnTypes);

        String txData = FunctionEncoder.encode(function);

        TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor
                (web3j, TX_END_CHECK_DURATION, TX_END_CHECK_RETRY);
        TransactionManager manager = new RawTransactionManager(web3j, credentials, CHAIN_ID, receiptProcessor);
        ContractGasProvider gasProvider = new DefaultGasProvider();

        String txHash = manager.sendTransaction(
                gasProvider.getGasPrice(functionName),
                gasProvider.getGasLimit(functionName),
                contractAddress,
                txData,
                BigInteger.ZERO
        ).getTransactionHash();


        if(txHash != null){
            TransactionReceipt receipt = receiptProcessor.waitForTransactionReceipt(txHash);
            log.info("txHash : {}",txHash);
            log.info("receipt : {}",receipt.toString());

        }


    }

    public BigInteger getTransactionCount(String address) throws ExecutionException, InterruptedException {
        Web3j web3j = Web3j.build(new HttpService(WEB3_PROVIDER_URL));
        EthGetTransactionCount result = new EthGetTransactionCount();
        result = web3j.ethGetTransactionCount(address,
                        DefaultBlockParameter.valueOf("latest"))
                .sendAsync()
                .get();
        return result.getTransactionCount();
    }
}
