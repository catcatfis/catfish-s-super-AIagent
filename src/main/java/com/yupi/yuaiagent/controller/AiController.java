package com.yupi.yuaiagent.controller;

import com.yupi.yuaiagent.agent.TravelPlanAgent;
import com.yupi.yuaiagent.agent.YuManus;
import com.yupi.yuaiagent.app.LoveApp;
import com.yupi.yuaiagent.app.TravelApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;

@RestController
@RequestMapping("/ai")
public class AiController {

    @Resource
    private LoveApp loveApp;

    @Resource
    private TravelApp travelApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;

    /**
     * 同步调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/love_app/chat/sync")
    public String doChatWithLoveAppSync(String message, String chatId) {
        return loveApp.doChat(message, chatId);
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLoveAppSSE(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId);
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithLoveAppServerSentEvent(String message, String chatId) {
        return loveApp.doChatByStream(message, chatId)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    /**
     * SSE 流式调用 AI 恋爱大师应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/love_app/chat/sse_emitter")
    public SseEmitter doChatWithLoveAppServerSseEmitter(String message, String chatId) {
        // 创建一个超时时间较长的 SseEmitter
        SseEmitter sseEmitter = new SseEmitter(180000L); // 3 分钟超时
        // 获取 Flux 响应式数据流并且直接通过订阅推送给 SseEmitter
        loveApp.doChatByStream(message, chatId)
                .subscribe(chunk -> {
                    try {
                        sseEmitter.send(chunk);
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }, sseEmitter::completeWithError, sseEmitter::complete);
        // 返回
        return sseEmitter;
    }

    /**
     * 同步调用 AI 恋爱大师应用（恋爱知识库 RAG 问答）
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/love_app/chat/rag")
    public String doChatWithLoveAppRag(String message, String chatId) {
        return loveApp.doChatWithRag(message, chatId);
    }

    /**
     * 同步调用 AI 旅行助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/travel_app/chat/sync")
    public String doChatWithTravelAppSync(String message, String chatId) {
        return travelApp.doChat(message, chatId);
    }

    /**
     * SSE 流式调用 AI 旅行助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/travel_app/chat/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithTravelAppSSE(String message, String chatId) {
        return travelApp.doChatByStream(message, chatId);
    }

    /**
     * SSE 流式调用 AI 旅行助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/travel_app/chat/server_sent_event")
    public Flux<ServerSentEvent<String>> doChatWithTravelAppServerSentEvent(String message, String chatId) {
        return travelApp.doChatByStream(message, chatId)
                .map(chunk -> ServerSentEvent.<String>builder()
                        .data(chunk)
                        .build());
    }

    /**
     * SSE 流式调用 AI 旅行助手应用
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping(value = "/travel_app/chat/sse_emitter")
    public SseEmitter doChatWithTravelAppServerSseEmitter(String message, String chatId) {
        // 创建一个超时时间较长的 SseEmitter
        SseEmitter sseEmitter = new SseEmitter(180000L); // 3 分钟超时
        // 获取 Flux 响应式数据流并且直接通过订阅推送给 SseEmitter
        travelApp.doChatByStream(message, chatId)
                .subscribe(chunk -> {
                    try {
                        sseEmitter.send(chunk);
                    } catch (IOException e) {
                        sseEmitter.completeWithError(e);
                    }
                }, sseEmitter::completeWithError, sseEmitter::complete);
        // 返回
        return sseEmitter;
    }

    /**
     * 同步调用 AI 旅行助手应用（旅行知识库 RAG 问答）
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/travel_app/chat/rag")
    public String doChatWithTravelAppRag(String message, String chatId) {
        return travelApp.doChatWithRag(message, chatId);
    }

    /**
     * 同步调用 AI 旅行助手应用（结构化旅行报告输出）
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/travel_app/chat/report")
    public TravelApp.TravelReport doChatWithTravelAppReport(String message, String chatId, String userName) {
        return travelApp.doChatWithReport(message, chatId, userName);
    }

    /**
     * 同步调用 AI 旅行助手应用（调用旅行专属工具：天气/汇率）
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/travel_app/chat/tools")
    public String doChatWithTravelAppTools(String message, String chatId) {
        return travelApp.doChatWithTools(message, chatId);
    }

    /**
     * 同步调用 AI 旅行助手应用（调用 MCP 服务）
     *
     * @param message
     * @param chatId
     * @return
     */
    @GetMapping("/travel_app/chat/mcp")
    public String doChatWithTravelAppMcp(String message, String chatId) {
        return travelApp.doChatWithMcp(message, chatId);
    }

    /**
     * 流式调用旅行行程规划智能体（规划行程并生成行程 PDF）
     *
     * @param message
     * @return
     */
    @GetMapping("/travel_app/plan")
    public SseEmitter doChatWithTravelPlanAgent(String message) {
        TravelPlanAgent travelPlanAgent = new TravelPlanAgent(allTools, dashscopeChatModel);
        return travelPlanAgent.runStream(message);
    }

    /**
     * 流式调用 Manus 超级智能体
     *
     * @param message
     * @return
     */
    @GetMapping("/manus/chat")
    public SseEmitter doChatWithManus(String message) {
        YuManus yuManus = new YuManus(allTools, dashscopeChatModel);
        return yuManus.runStream(message);
    }
}
