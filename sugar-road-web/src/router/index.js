import { createRouter, createWebHistory } from "vue-router";
import Test from "@/pages/main/Test.vue";
import Search from "@/pages/search/Search.vue";
import Post from "@/pages/post/Post.vue";
import Store from "@/pages/store/Store.vue";
import MyPage from "@/pages/mypage/MyPage.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: Test},
    { path: "/search", component: Search },
    { path: "/post", component: Post },
    { path: "/store", component: Store },
    { path: "/mypage", component: MyPage },

  ],
});
export default router;