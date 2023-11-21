import { createRouter, createWebHistory } from "vue-router";
import Test from "@/pages/main/Test.vue";
import Search from "@/pages/search/Search.vue";
import Post from "@/pages/post/Post.vue";
import Store from "@/pages/store/Store.vue";
import MyPage from "@/pages/mypage/MyPage.vue";

import ReviewEdit from "@/pages/review/ReviewEdit.vue";
import ReviewDetail from "@/pages/review/ReviewDetail.vue";
import ReviewTest from "@/pages/review/ReviewTest.vue";
import ReviewWrite from "@/pages/review/ReviewWrite.vue";

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { path: "/", component: Test},
    { path: "/search", component: Search },
    { path: "/post", component: Post },
    { path: "/store", component: Store },
    { path: "/mypage", component: MyPage },
    { path: "/review/:reviewId", component: ReviewDetail},
    { path: "/review/edit/:reviewId", component: ReviewEdit},
    { path: "/review/write/store/:storeId", component: ReviewWrite},
    { path: "/test", component: ReviewTest}
  ],
});
export default router;