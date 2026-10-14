package com.yupi.yuaiagent.demo.invoke;

/**
 * 仅用于测试获取 API Key
 */
public interface TestApiKey {

    // 从环境变量 QW_API_KEY 读取真实的 API Key（已在系统中配置）
    String API_KEY = System.getenv("QW_API_KEY");
}
