package com.yupi.yuaiagent.agent;

import com.yupi.yuaiagent.advisor.MyLoggerAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;

/**
 * 旅行行程规划智能体：复用 YuManus 的工具链（文件 / PDF 生成 / 网络搜索等），
 * 自主完成"规划行程 → 生成行程 PDF"的完整任务闭环。
 * 注意：BaseAgent 持有可变会话状态，每次请求须由 Controller new 一个新实例，不能注册为单例 Bean。
 */
public class TravelPlanAgent extends ToolCallAgent {

    public TravelPlanAgent(ToolCallback[] allTools, ChatModel dashscopeChatModel) {
        super(allTools);
        this.setName("travelPlanAgent");
        String SYSTEM_PROMPT = """
                You are TravelPlanAgent, a professional travel itinerary planning agent.
                Given the user's travel request (destination, days, budget, preferences), you should:
                1. Optionally search the web for destination tips if useful.
                2. Write a detailed day-by-day itinerary in Chinese (attractions, transport, food, budget).
                3. Always finish by generating a PDF file of the itinerary using the `generatePDF` tool,
                   with a file name like "旅行行程-{destination}.pdf".
                If you want to stop the interaction at any point, use the `terminate` tool/function call.
                """;
        this.setSystemPrompt(SYSTEM_PROMPT);
        String NEXT_STEP_PROMPT = """
                Based on the user's travel request, plan the next action.
                You can search the web for information, and you MUST eventually call the `generatePDF` tool
                to save the final itinerary as a PDF file, then call `terminate`.
                """;
        this.setNextStepPrompt(NEXT_STEP_PROMPT);
        this.setMaxSteps(15);
        // 初始化 AI 对话客户端
        ChatClient chatClient = ChatClient.builder(dashscopeChatModel)
                .defaultAdvisors(new MyLoggerAdvisor())
                .build();
        this.setChatClient(chatClient);
    }
}
