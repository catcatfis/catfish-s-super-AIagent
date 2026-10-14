package com.yupi.yuaiagent.tools;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.util.HashMap;
import java.util.Map;

/**
 * 旅行信息查询工具（天气 + 汇率），TravelApp 专属
 */
public class TravelInfoTool {

    // Open-Meteo 天气接口（免 API Key）
    private static final String WEATHER_API_URL = "https://geocoding-api.open-meteo.com/v1/search";

    // Open-Meteo 预报接口（免 API Key）
    private static final String FORECAST_API_URL = "https://api.open-meteo.com/v1/forecast";

    // 汇率接口（免 API Key）
    private static final String EXCHANGE_RATE_API_URL = "https://open.er-api.com/v6/latest/";

    @Tool(description = "Get the weather forecast for a travel destination city", returnDirect = false)
    public String getWeatherForecast(
            @ToolParam(description = "Name of the travel destination city, e.g. Beijing") String city) {
        try {
            // 1、先通过地理编码接口拿到城市经纬度
            Map<String, Object> geoParams = new HashMap<>();
            geoParams.put("name", city);
            geoParams.put("count", 1);
            geoParams.put("language", "zh");
            String geoResponse = HttpUtil.get(WEATHER_API_URL, geoParams);
            JSONObject geoObject = JSONUtil.parseObj(geoResponse);
            cn.hutool.json.JSONArray results = geoObject.getJSONArray("results");
            if (results == null || results.isEmpty()) {
                return "未找到城市：" + city + "，请确认城市名称是否正确。";
            }
            JSONObject firstResult = (JSONObject) results.get(0);
            double latitude = firstResult.getDouble("latitude");
            double longitude = firstResult.getDouble("longitude");
            // 2、再通过预报接口获取未来 3 天天气
            Map<String, Object> forecastParams = new HashMap<>();
            forecastParams.put("latitude", latitude);
            forecastParams.put("longitude", longitude);
            forecastParams.put("daily", "weather_code,temperature_2m_max,temperature_2m_min");
            forecastParams.put("forecast_days", 3);
            forecastParams.put("timezone", "auto");
            String forecastResponse = HttpUtil.get(FORECAST_API_URL, forecastParams);
            return "城市：" + firstResult.getStr("name") + "，未来 3 天天气预报（原始数据）：" + forecastResponse;
        } catch (Exception e) {
            return "查询天气失败：" + e.getMessage();
        }
    }

    @Tool(description = "Get the exchange rate from one currency to another for travel budget", returnDirect = false)
    public String getExchangeRate(
            @ToolParam(description = "The base currency code to convert from, e.g. CNY") String baseCurrency,
            @ToolParam(description = "The target currency code to convert to, e.g. USD") String targetCurrency) {
        try {
            String response = HttpUtil.get(EXCHANGE_RATE_API_URL + baseCurrency.toUpperCase());
            JSONObject jsonObject = JSONUtil.parseObj(response);
            String result = jsonObject.getStr("result");
            if (!"success".equals(result)) {
                return "查询汇率失败，请确认货币代码是否正确，如 CNY、USD、JPY、EUR。";
            }
            JSONObject rates = jsonObject.getJSONObject("rates");
            if (rates == null || rates.get(targetCurrency.toUpperCase()) == null) {
                return "未找到目标货币：" + targetCurrency;
            }
            double rate = rates.getDouble(targetCurrency.toUpperCase());
            return "1 " + baseCurrency.toUpperCase() + " = " + rate + " " + targetCurrency.toUpperCase()
                    + "（更新时间：" + jsonObject.getStr("time_last_update_utc") + "）";
        } catch (Exception e) {
            return "查询汇率失败：" + e.getMessage();
        }
    }
}
