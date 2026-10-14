
## 项目功能梳理
项目中，我们将开发一个 AI 恋爱大师应用、一个拥有自主规划能力的超级智能体，以及一系列工具和 MCP 服务。
具体需求如下：
- AI 恋爱大师应用：用户在恋爱过程中难免遇到各种难题，让 AI 为用户提供贴心情感指导。支持多轮对话、对话记忆持久化、RAG 知识库检索、工具调用、MCP 服务调用。
- AI 超级智能体：可以根据用户的需求，自主推理和行动，直到完成目标。
- 提供给 AI 的工具：包括联网搜索、文件操作、网页抓取、资源下载、终端操作、PDF 生成。
- AI MCP 服务：可以从特定网站搜索图片。
## 用哪些技术？
项目以 Spring AI 开发框架实战为核心，涉及到多种主流 AI 客户端和工具库的运用。
- Java 21 + Spring Boot 3 框架
- ⭐️ Spring AI + LangChain4j
- ⭐️ RAG 知识库
- ⭐️ PGvector 向量数据库
- ⭐ Tool Calling 工具调用 
- ⭐️ MCP 模型上下文协议
- ⭐️ ReAct Agent 智能体构建
- ⭐️ Serverless 计算服务
- ⭐️ AI 大模型开发平台百炼
- ⭐️ Cursor AI 代码生成
- ⭐️ SSE 异步推送
- 第三方接口：如 SearchAPI / Pexels API
- Ollama 大模型部署
- 工具库如：Kryo 高性能序列化 + Jsoup 网页抓取 + iText PDF 生成 + Knife4j 接口文档


## 学习大纲

第 1 期：项目总览

- 项目介绍
- 项目优势
- 项目功能梳理
- 技术选型
- 架构设计
- AI 学习路线

- - AI 应用平台的使用（Dify）
  - AI 常用工具
  - AI 编程技巧
  - AI 编程技术

- 学习大纲



第 2 期：AI 大模型接入

- AI 大模型概念
- 接入 AI 大模型（3 种方式）
- 后端项目初始化
- 程序调用 AI 大模型（4 种方式）
- 本地部署 AI 大模型
- Spring AI 调用本地大模型



第 3 期：AI 应用开发

- Prompt 工程概念
- Prompt 优化技巧
- AI 恋爱大师应用需求分析
- AI 恋爱大师应用方案设计
- Spring AI ChatClient / Advisor / ChatMemory 特性
- 多轮对话 AI 应用开发
- Spring AI 自定义 Advisor
- Spring AI 结构化输出 - 恋爱报告功能
- Spring AI 对话记忆持久化
- Spring AI Prompt 模板特性
- 多模态概念和开发



第 4 期：RAG 知识库基础

- AI 恋爱知识问答需求分析
- RAG 概念（重点理解核心步骤）
- RAG 实战：Spring AI + 本地知识库
- RAG 实战：Spring AI + 云知识库服务



第 5 期：RAG 知识库进阶

- RAG 核心特性

- - 文档收集和切割（ETL）
  - 向量转换和存储（向量数据库）
  - 文档过滤和检索（文档检索器）
  - 查询增强和关联（上下文查询增强器）

- RAG 最佳实践和调优
- 检索策略
- 大模型幻觉



第 6 期：工具调用

- 工具概念
- Spring AI 工具开发
- 主流工具开发

- - 文件操作
  - 联网搜索
  - 网页抓取
  - 终端操作
  - 资源下载
  - PDF 生成

- 工具进阶知识（原理和高级特性）



第 7 期：MCP

- MCP 概念
- 使用 MCP（3 种方式）
- Spring AI MCP 开发模式
- Spring AI MCP 开发实战 - 图片搜索 MCP
- MCP 开发最佳实践
- 部署 MCP
- MCP 安全问题



第 8 期：AI 智能体构建

- AI 智能体概念
- 智能体实现关键
- 使用 AI 智能体（2 种方式）
- 自主规划智能体介绍
- OpenManus 实现原理
- 自主实现 Manus 智能体
- 智能体工作流



第 9 期：AI 服务化

- AI 应用接口开发（SSE）
- AI 智能体接口开发
- AI 生成前端代码
- AI 服务 Serverless 部

