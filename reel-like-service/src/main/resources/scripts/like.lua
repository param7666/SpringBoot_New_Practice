-- KEYS[1] = {us:N}:liked:<reelId>:<userId>  (ink mark, stores version)
-- KEYS[2] = {us:N}:cnt:<reelId>              (this shard's partial counter)
-- KEYS[3] = {us:N}:outbox                    (durable event log for this shard)
-- ARGV[1] = clientTimestamp (version)
-- ARGV[2] = ink TTL in seconds
-- ARGV[3] = reelId
-- ARGV[4] = userId

local existing = redis.call('GET', KEYS[1])

if existing and tonumber(existing) >= tonumber(ARGV[1]) then
    return -1
end

redis.call('SET', KEYS[1], ARGV[1], 'EX', ARGV[2])

local partial = redis.call('INCR', KEYS[2])

redis.call('XADD', KEYS[3], '*',
    'op', 'LIKE',
    'reelId', ARGV[3],
    'userId', ARGV[4],
    'version', ARGV[1])

return partial