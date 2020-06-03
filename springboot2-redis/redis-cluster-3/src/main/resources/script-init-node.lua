-- get next sequence
-- User: taosy
-- Date: 17/11/17
redis.replicate_commands();

-- 判断是否是闰年
local function isleap(year)
    return year % 4 == 0 and (year % 100 ~= 0 or year % 400 == 0);
end

-- 获取某一年的天数
local function get_yeardays(year)
    if isleap(year) then
        return 366;
    else
        return 365;
    end
end

-- 格式化时间
local function formattime(time)
    local day_sec = 24 * 60 * 60;
    local year = 1970;
    local month;
    local day;
    local hour;
    local minute;
    local second;
    local mons = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    --local now = tonumber(redis.call('TIME')[1]);
    -- 获取年份
    local days = time / day_sec;
    local curr_day = get_yeardays(year);
    while (days >= curr_day)
    do
        days = days - curr_day;
        year = year + 1;
        curr_day = get_yeardays(year);
    end
    day = days;

    if isleap(year) then
        mons[1] = mons[1] + 1;
    end
    -- 获取月和天数
    for i, v in ipairs(mons) do
        if day < v then
            month = i;
            day = day + 1;
            break;
        end
        day = day - v;
    end
    -- 剩余天数
    local sec_in_day = math.floor(time % day_sec);
    -- 时
    hour = sec_in_day / (60 * 60);
    -- 分
    minute = sec_in_day % (60 * 60) / 60;
    -- 秒
    second = sec_in_day % 60;
    -- 返回拼接时间
    return string.sub(year, 3, -1) ..
            string.format('%02d', month) ..
            string.format('%02d', day) ..
            string.format('%02d', hour) ..
            string.format('%02d', minute) ..
            string.format('%02d', second);
end

-- 序列的key
local key = tostring(KEYS[1]);
-- 初始化序列后缀值
local init_value = tonumber(ARGV[1]);
-- 自增长数
local incr_amount = tonumber(ARGV[2]);
-- 序列长度
local length = tonumber(ARGV[3]);
-- 用来进行比较的序列前四位yyMM
local yyMM = tostring(ARGV[4]);
-- 时差
local lag = tonumber(ARGV[5]);
local seq = redis.call('get', key);
if string.len(seq) < length or string.find(seq, yyMM) ~= 1 then
    seq = formattime(tonumber(redis.call('TIME')[1]) + lag * 3600) .. string.format('%0' .. (length - 12) .. 'd', init_value);
    -- redis.log(redis.LOG_NOTICE, 'reset->key:' .. key .. ',seq:' .. seq .. ',init_value:' .. init_value .. ');
    redis.call('set', key, seq);
    return seq;
else
    -- 此处返回用redis.call('get', key)，redis的incrby可能返回科学计数法
    redis.call('incrby', key, incr_amount);
    -- redis.log(redis.LOG_NOTICE,'incrby->seq:' .. seq)
    return redis.call('get', key);
end