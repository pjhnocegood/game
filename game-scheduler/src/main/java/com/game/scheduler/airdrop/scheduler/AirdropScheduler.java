package com.game.scheduler.airdrop.scheduler;

import com.game.common.entity.db.AirdropRequestMember;
import com.game.common.repository.db.AirdropRequestMemberRepository;
import com.game.common.util.ConvertUtil;
import com.game.scheduler.web3.component.Web3Component;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.DynamicBytes;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Bytes32;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.protocol.exceptions.TransactionException;

import java.io.IOException;
import java.math.BigInteger;
import java.time.OffsetDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class AirdropScheduler {


    private boolean airdropTransferIsRunning = false;
    private static final Logger log = LoggerFactory.getLogger(AirdropScheduler.class);

    private final AirdropRequestMemberRepository airdropRequestMemberRepository;

    private final Web3Component web3Component;
    @Value("${web3.ethereum.address")
    private String OWNER_ADDRESS;

    private final String BATCH_TRANSFER_FUNCTION_NAME = "safeTransferFromBatch";

    @Scheduled(fixedDelay = 10 * 1000, initialDelay = 10 * 1000)
    public void airdropTransfer() {

        if(airdropTransferIsRunning) {
            return;
        }

        try {
            airdropTransferIsRunning= true;
            List<AirdropRequestMember> airdropRequestMemberList = airdropRequestMemberRepository.findTop30ByIsCompletedOrderByAirdropRequestMemberId(false);

            if(!airdropRequestMemberList.isEmpty()){

                List<Long> airdropRequestMemberIdList = airdropRequestMemberList.stream().map(AirdropRequestMember::getAirdropRequestMemberId).toList();
                airdropRequestMemberRepository.updateCompletedByAirdropRequestMemberIdList(airdropRequestMemberIdList,true);
                Map<Long,List<AirdropRequestMember>> map =airdropRequestMemberListToMap(airdropRequestMemberList);


                //airdropId 단위로 web3로 전송
                for (Long airdropId : map.keySet()) {
                    airdropTransferToWeb3(map.get(airdropId));
                }

            }

        } catch (Exception e) {
            log.error("airdropTransferSchedule error : {}", e.getMessage());
        } finally {
            airdropTransferIsRunning = false;
        }

    }

    //airdropId 단위로 map에 AirdropRequestMember를 list로 저장함
    public Map<Long,List<AirdropRequestMember>> airdropRequestMemberListToMap(List<AirdropRequestMember> airdropRequestMemberList){
        Map<Long,List<AirdropRequestMember>> map = new HashMap<>();

        for (AirdropRequestMember airdropRequestMember :airdropRequestMemberList) {
            Long airdropId = airdropRequestMember.getAirdrop().getAirdropId();
            if(map.containsKey(airdropId)){
                map.get(airdropId).add(airdropRequestMember);
            }else{
                List<AirdropRequestMember> list = new ArrayList<>();
                list.add(airdropRequestMember);
                map.put(airdropId,list);
            }
        }
        return map;
    }


    private void airdropTransferToWeb3(List<AirdropRequestMember> airdropRequestMemberList) throws TransactionException, IOException, InterruptedException {
        Long nftCount = airdropRequestMemberList.get(0).getAirdrop().getCountPerMember();
        Long tokenId = airdropRequestMemberList.get(0).getAirdrop().getNft().getTokenId();
        String contractAddress = airdropRequestMemberList.get(0).getAirdrop().getNft().getContractAddress();


        List<Address> toAddressList = new ArrayList<>();
        for (AirdropRequestMember airdropRequestMember : airdropRequestMemberList) {
            toAddressList.add(new Address(airdropRequestMember.getMember().getWallet().getWalletAddress()));
        }

        List<Type> params = new ArrayList<>();
        params.add(new Address(OWNER_ADDRESS));
        params.add(new DynamicArray(toAddressList));
        params.add(new Uint256(tokenId));
        params.add(new  Uint256(nftCount));
        params.add(new DynamicBytes("0x".getBytes()));


        // output parameters
        List<TypeReference<?>> returnTypes = Collections.<TypeReference<?>>emptyList();

        web3Component.sendTransaction(BATCH_TRANSFER_FUNCTION_NAME, contractAddress, params, returnTypes);
    }

}
