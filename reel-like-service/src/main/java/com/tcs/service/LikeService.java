package com.tcs.service;

import java.util.List;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import com.tcs.dto.LikeReponse;
import com.tcs.dto.LikeRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private static final int SHARD_COUNT = 256;
	private static final long INK_TTL_SECONDS = 7L * 24 * 60 * 60;
	
	private final StringRedisTemplate redisTemplate;
	private final RedisScript<Long> likeScript;
	
	
	public LikeReponse like(LikeRequest req) {
		int shard=computeShard(req.userId());
		String tag="{us:"+shard+"}";
		String inkKey=tag+":liked:"+req.reelId()+":"+req.userId();
		String counterKey = tag + ":cnt:" + req.reelId();
		String outboxKey=tag+":outbox";
		
		List<String> keys=List.of(inkKey,counterKey,outboxKey);
		
		Long result=redisTemplate.execute(
				likeScript,keys,
				String.valueOf(req.clientTimestamp()),
				String.valueOf(INK_TTL_SECONDS),
				String.valueOf(req.reelId()),
				String.valueOf(req.userId())
				);
		
		boolean duplicate=(result==null || result== -1L);
		
		long likeCount=getApproxTotal(req.reelId());
		return new LikeReponse(req.userId(),!duplicate,likeCount,duplicate);
		
	}
	
	private int computeShard(Long  userId) {
		return Math.floorMod(userId, SHARD_COUNT);
	}
	
	private long getApproxTotal(Long reelId) {
		String totalKey="reel:"+reelId+":total";
		String value=redisTemplate.opsForValue().get(totalKey);
		return value!=null?Long.parseLong(value):0L;
	}
}
