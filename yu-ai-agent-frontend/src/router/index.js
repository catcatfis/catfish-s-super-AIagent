import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: () => import('../views/Home.vue'),
    meta: {
      title: '首页 - catfishAI超级智能体应用平台',
      description: 'catfishAI超级智能体应用平台提供AI恋爱大师、AI旅行助手和AI超级智能体服务，满足您的各种AI对话需求'
    }
  },
  {
    path: '/love-master',
    name: 'LoveMaster',
    component: () => import('../views/LoveMaster.vue'),
    meta: {
      title: 'AI恋爱大师 - catfishAI超级智能体应用平台',
      description: 'AI恋爱大师是catfishAI超级智能体应用平台的专业情感顾问，帮你解答各种恋爱问题，提供情感建议'
    }
  },
  {
    path: '/travel-assistant',
    name: 'TravelAssistant',
    component: () => import('../views/TravelAssistant.vue'),
    meta: {
      title: 'AI旅行助手 - catfishAI超级智能体应用平台',
      description: 'AI旅行助手是catfishAI超级智能体应用平台的专业出行顾问，帮你规划行程、推荐目的地、制定旅行方案'
    }
  },
  {
    path: '/super-agent',
    name: 'SuperAgent',
    component: () => import('../views/SuperAgent.vue'),
    meta: {
      title: 'AI超级智能体 - catfishAI超级智能体应用平台',
      description: 'AI超级智能体是catfishAI超级智能体应用平台的全能助手，能解答各类专业问题，提供精准建议和解决方案'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// 全局导航守卫，设置文档标题
router.beforeEach((to, from, next) => {
  // 设置页面标题
  if (to.meta.title) {
    document.title = to.meta.title
  }
  next()
})

export default router 